package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static Iamreporter.Helper.Helper.*;

@Path("/news")
@Component
public class UserNewsService {

    UserNewsDB userNewsDB = new UserNewsDB();
    UserDB db = new UserDB();
    ViewsDb viewsDB = new ViewsDb();
    CommentDB commentDB = new CommentDB();
    MediaFileDB mediaFileDB = new MediaFileDB();

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendNews(String json){
        return saveNewNews(json);
    }

    @POST
    @Path("/{newsUUID}/uploadnewsphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String uploadNewsPhoto(@PathParam("newsUUID") String uuid,
                                   @FormDataParam("file") InputStream uploadedInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetail)throws IOException {
        User user = db.getUserByUUID(uuid);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            String mediaFileUUID = UUID();
            String location = PHOTO_LOCATION_URL + uuid +mediaFileUUID + fileDetail.getFileName();
            String url = PHOTO_WEB_URL + uuid +mediaFileUUID + fileDetail.getFileName();

            MediaFile file = new MediaFile();
            file.setDate(UnixTime());
            file.setPhotoURL(url);
            file.setSmallPhotoURL("");
            file.setVideoURL("");
            file.setUuid(mediaFileUUID);

            mediaFileDB.saveUser(file);

            writeFile(uploadedInputStream, location);

            jsonObject.put("uploadstatus", true);
            jsonObject.put("avatarURL", url);
        } else {
            jsonObject.put("user", false);
            jsonObject.put("uploadstatus", false);
        }
        return jsonObject.toString();
    }

    @POST
    @Path("/{newsUUID}/{userUUID}/uploadnewsphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String uploadNewsVideo(@PathParam("userUUID")String userUUID,
                                  @PathParam("newsUUID") String uuid,
                                  @FormDataParam("file") InputStream uploadedInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileDetail)throws IOException {
        User user = db.getUserByUUID(userUUID);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {

            String mediaFileUUID = UUID();

            String location = PHOTO_LOCATION_URL + uuid + mediaFileUUID+ fileDetail.getFileName();
            String url = PHOTO_WEB_URL + uuid + mediaFileUUID + fileDetail.getFileName();

            MediaFile file = new MediaFile();
            file.setDate(UnixTime());
            file.setPhotoURL(url);
            file.setSmallPhotoURL("");
            file.setVideoURL("");
            file.setUuid(mediaFileUUID);
            file.setNewsUUID(uuid);
            mediaFileDB.saveUser(file);

            writeFile(uploadedInputStream, location);

            jsonObject.put("uploadstatus", true);
            jsonObject.put("photoUrl", url);

        } else {
            jsonObject.put("uploadstatus", false);
            jsonObject.put("photoUrl", false);
        }
        return jsonObject.toString();
    }

    @GET
    @Path("/{newsUUID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getNewsByUUID(@PathParam("newsUUID")String uuid){
        return getNewsDate(uuid);
    }

    public String saveNewNews(String json){
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = new JSONObject();
        boolean anon = jsonObject.getBoolean("anon");
        String email = jsonObject.getString("email");
        User user;
        UserNews news ;
        if(anon){
            if(EmailValidator.isValidEmailAddress(email) && validateForDuplicateEmail(email) ){
                user = createNewUser(json);
                news = createNewNews(json, user);
                response.put("create",true);
                response.put("news",news.getUuid());
             }else{
                response.put("create",false);
            }
        }else {
            user = db.getUserByUUID(jsonObject.getString("userUUID"));
            if(user!=null){
                news = registerUserNews(json,user);
                response.put("create",true);
                response.put("news",news.getUuid());
            }else{
                response.put("create",false);
            }
        }
        return response.toString();

    }

    public User createNewUser(String json){
        JSONObject jsonObject = new JSONObject(json);
        User user = new User();
        String name = jsonObject.getString("nameSurName");
        String [] nameSurname = name.split(" ");
        user.setName(nameSurname[0]);
        user.setSurName(nameSurname[1]);
        user.setUserUUID(jsonObject.getString("userUUID"));
        user.setDate(UnixTime());
        user.setEmail(jsonObject.getString("email"));
        user.setPassword(UUID());
        user.setCity("");
        user.setNickName("");
        user.setAvatarURL("");
        user.setDescription("");
        user.setCallName("");
        user.setMobilePhone("");
        db.saveUser(user);
        sendRegistrationMessage(jsonObject.getString("email"),json,user.getPassword());
        return user;
    }

    public UserNews createNewNews(String json, User user){
        JSONObject jsonObject = new JSONObject(json);

        UserNews news = new UserNews();
        news.setUserNewsTheme(jsonObject.getString("theme"));
        news.setCity(jsonObject.getString("city"));
        news.setCategories(jsonObject.getInt("category"));
        news.setText(jsonObject.getString("text"));
        news.setAuthorUUID(user.getUserUUID());
        news.setUuid(UUID());

        userNewsDB.saveUserNews(news);

        return news;
    }

    public UserNews registerUserNews(String json,User user){
        JSONObject jsonObject = new JSONObject(json);
        UserNews news = new UserNews();
        news.setUserNewsTheme(jsonObject.getString("theme"));
        news.setCity(jsonObject.getString("city"));
        news.setCategories(jsonObject.getInt("category"));
        news.setText(jsonObject.getString("text"));
        news.setAuthorUUID(user.getUserUUID());
        news.setUuid(UUID());
        userNewsDB.saveUserNews(news);
        return news;
    }


    public boolean validateForDuplicateEmail(String email){
        boolean status = false;
        if(db.getUserByEmail(email)==null){
            status = true;
        }
        return status;
    }

    public String getNewsDate(String uuid){
        UserNews userNews = userNewsDB.getNewsByUUID(uuid);
        JSONObject jsonObject = new JSONObject();

        if(userNews!=null){
            User user = db.getUserByUUID(userNews.getAuthorUUID());
            jsonObject.put("category",userNews.getCategories());
            jsonObject.put("theme",userNews.getUserNewsTheme());
            jsonObject.put("text",userNews.getText());
            jsonObject.put("date",userNews.getDate());
            jsonObject.put("author",user.getName());
            jsonObject.put("authorUUID",user.getUserUUID());
            jsonObject.put("countViews",viewsDB.getUserNewsCountViews(uuid));
            jsonObject.put("comments",getComments(uuid));
        }
        return jsonObject.toString();
    }

    public List<CommentCreator> getComments(String uuid){
        List<Comment> comments = commentDB.getCOmmentByNewsUUID(uuid);
        List<CommentCreator> creators = new ArrayList<>();
        for(Comment comment : comments){
            CommentCreator commentCreator = new CommentCreator();
            User user = db.getUserByUUID(comment.getAuthorUUID());
            commentCreator.setText(comment.getText());
            commentCreator.setDate(comment.getDate());
            commentCreator.setName(user.getNickName());
            if(comment.getToUserUUID()!=null){
                commentCreator.setTo(comment.getToUserUUID());
            }else{
                commentCreator.setTo("");
            }
            commentCreator.setLikeCount(viewsDB.commentsCount(comment.getCommentUUID()));
            commentCreator.setVievCount(0);
            creators.add(commentCreator);
        }
        return creators;
    }


    public void sendRegistrationMessage(String email, String json,String generatedPassword) {
        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getString("nameSurName");
        String [] nameSurname = name.split(" ");
        final String username = "dmitrenkonikita1213@gmail.com";
        final String password = "telez1213";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplyreporter@reporter.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Testing Subject");
            message.setText("Привет , " + nameSurname[0] + " ," + nameSurname[1] + " ты зарегестрирован в приложении ,''Я репортер''" +
                    " Ваш пароль = "+generatedPassword+" :)");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
