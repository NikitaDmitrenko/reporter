package Iamreporter.ServicePack;

import Iamreporter.DB.*;
import Iamreporter.Model.Comment;
import Iamreporter.Model.CommentCreator;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import Iamreporter.Service.EmailValidator;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static Iamreporter.Helper.Helper.UUID;
import static Iamreporter.Helper.Helper.UnixTime;

public class Service {

    UserNewsDB userNewsDB = new UserNewsDB();
    CommentDB commentDB = new CommentDB();
    UserDB db = new UserDB();
    ViewsDb viewsDB = new ViewsDb();
    LikesDB likeDB = new LikesDB();

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
            jsonObject.put("authorName",user.getName());
            jsonObject.put("authorSurName",user.getSurName());
            jsonObject.put("authorUUID",user.getUserUUID());
            jsonObject.put("countViews",viewsDB.getUserNewsCountViews(uuid));
            jsonObject.put("likeCounts",likeDB.getNewsLikesCount(userNews.getUuid()));
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
                news = registerUserNews(json, user);
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
        sendRegistrationMessage(jsonObject.getString("email"), json, user.getPassword());
        return user;
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
