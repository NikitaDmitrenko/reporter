package Iamreporter.ServicePack;

import Iamreporter.DB.*;
import Iamreporter.Model.*;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static Iamreporter.Helper.Helper.*;
import static Iamreporter.Helper.Helper.VIDEO_FILE_URL;

public class Service {

    UserNewsDB userNewsDB = new UserNewsDB();
    CommentDB commentDB = new CommentDB();
    UserDB db = new UserDB();
    LikesDB likeDB = new LikesDB();
    MediaFileDB mediaFileDB = new MediaFileDB();
    final String username = "dmitrenkonikita1213@gmail.com";
    final String password = "telez1213";

    public UserNews registerUserNews(String json,User user,String newsUUID){
        JSONObject jsonObject = new JSONObject(json);
        UserNews news = new UserNews();

        double latitude = jsonObject.getDouble("latitude");
        double longitude = jsonObject.getDouble("longitude");

        news.setUserNewsTheme(jsonObject.getString("theme"));
        news.setCategory(jsonObject.getInt("tag"));
        news.setText(jsonObject.getString("text"));
        news.setAuthorUUID(user.getPrivateUUID());
        news.setDate(UnixTime());
        news.setUuid(newsUUID);
        if(user.getName()!=null) {
            news.setNameSurName(user.getName());
        }else{
            news.setNameSurName("");
        }
        news.setCity(getCity(latitude,longitude));
        news.setAnonyomous(jsonObject.getBoolean("anonymous"));

        userNewsDB.saveUserNews(news);
        return news;
    }

    public String getNewsDate(String uuid){
        UserNews userNews = userNewsDB.getNewsByUUID(uuid);
        JSONObject jsonObject = new JSONObject();

        if(userNews!=null){
            User user = db.getUserByPrivateUUID(userNews.getAuthorUUID());
            jsonObject.put("category",userNews.getCategory());
            jsonObject.put("theme",userNews.getUserNewsTheme());
            jsonObject.put("text",userNews.getText());
            jsonObject.put("date",userNews.getDate());
            jsonObject.put("name",user.getName());
            jsonObject.put("countViews",userNewsDB.getUserNewsViewsCount(uuid));
            jsonObject.put("likeCounts",likeDB.getNewsLikesCount(userNews.getUuid()));
            jsonObject.put("comments",getNewsComments(uuid));
            jsonObject.put("newsUUID",userNews.getUuid());
            jsonObject.put("commentsCount",commentDB.getNewsCommentsCount(userNews.getUuid()));
            jsonObject.put("photoFiles",getMediaFileInfo(userNews));
            userNews.incrementAndGet();
            userNewsDB.update(userNews);
        }
        return jsonObject.toString();
    }


    public JSONArray getMediaFileInfo(UserNews userNews){
        List<MediaFile> mediaFiles = mediaFileDB.getNewsMediaFiles(userNews.getUuid());
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        if(!mediaFiles.isEmpty()){
            for(MediaFile mediaFile : mediaFiles) {
                jsonObject = new JSONObject();
                if(!mediaFile.getPhotoURL().equals("")) {
                    jsonObject.put("bigPhotoURL", mediaFile.getPhotoURL());
                    jsonObject.put("smallPhotoURL", mediaFile.getSmallPhotoURL());
                    jsonObject.put("status",1);
                }else{
                    jsonObject.put("videoURL",mediaFile.getVideoURL());
                    jsonObject.put("status",2);
                }
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    public void saveMediaFile(String userUUID,String filePath,String newsUUID){
        MediaFile mediaFile = new MediaFile();
        mediaFile.setDate(UnixTime());
        mediaFile.setNewsUUID(newsUUID);
        mediaFile.setUuid(UUID());
        mediaFile.setUserUUID(userUUID);
        String ext = FilenameUtils.getExtension(filePath);
        if(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("gif")){
            mediaFile.setPhotoURL(filePath.replace(BIG_PHOTO_LOCATION,BIG_PHOTO_URL));
            mediaFile.setSmallPhotoURL(filePath.replace(BIG_PHOTO_URL, SMALL_PHOTO_URL));
            mediaFile.setVideoURL("");
        }else if(ext.equalsIgnoreCase("avi")||ext.equalsIgnoreCase("mp4")||ext.equalsIgnoreCase("mkv")){
            mediaFile.setVideoURL(filePath.replace(VIDEO_FILE_LOCATION,VIDEO_FILE_URL));
            mediaFile.setPhotoURL("");
            mediaFile.setSmallPhotoURL("");
        }
        mediaFileDB.saveMediaFile(mediaFile);
    }

    public String saveNewNews(String json,String userUUID, String newsUUID){
        JSONObject response = new JSONObject();
        User user;
            user = db.getUserByPrivateUUID(userUUID);
            if(user!=null){
                registerUserNews(json, user,newsUUID);
                response.put("create",true);
                response.put("newsUUID",newsUUID);
            }else{
                response.put("create",false);
            }
        return response.toString();

    }

    public void SendMailToUser(User user, User anotherUser, String json){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        JSONObject jsonObject = new JSONObject(json);
        String userEmail = user.getEmail();
        String anotherUserEmail = anotherUser.getEmail();
        String subject = jsonObject.getString("subject");
        String text = jsonObject.getString("text");


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
                    InternetAddress.parse(anotherUserEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCity(final double latitude, final double longitude){

        String target = GOOGLE_MAPS.replace("latitude", String.valueOf(latitude).replace("longitude", String.valueOf(longitude)));
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(target);
        Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
        String response = builder.get(String.class);
        System.out.println("Rersponse is"+response);
        return "";

    }

    public JSONObject getTopNews(int step){
        List<UserNews> topNews = userNewsDB.getTopNews();
        topNews = getUniqueNews(topNews, step * 30, (step + 1) * 30);
        return shiftNewses(topNews);
    }


    public List<UserNews> getUniqueNews(List<UserNews> userList, int start, int end){
        if(userList.size()<end){
            return new ArrayList<>(userList.subList(start,userList.size()));
        }else
            return new ArrayList<>(userList.subList(start,end));
    }

    public JSONObject shiftNewses(List<UserNews> userList) {
        JSONObject returnJs = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject js ;
        for (UserNews userNews : userList) {
            js = new JSONObject();
            List<MediaFile> newsPhotos = mediaFileDB.getNewsPhotos(userNews.getUuid());
            js.put("text",userNews.getText());
            js.put("theme",userNews.getUserNewsTheme());
            js.put("date",userNews.getDate());
            js.put("newsUUID",userNews.getUuid());
            if(!newsPhotos.isEmpty()) {
                MediaFile mediaFile1 = getPhotoFile(newsPhotos);
                if(mediaFile1!=null) {
                    js.put("bigPhotoURL", mediaFile1.getPhotoURL());
                    js.put("smallPhotoURL", mediaFile1.getSmallPhotoURL());
                }else{
                    js.put("bigPhotoURL", "");
                    js.put("smallPhotoURL", "");
                }
            }else{
                js.put("bigPhotoURL", "");
                js.put("smallPhotoURL", "");
            }
            jsonArray.put(js);
        }
        returnJs.put("topNews",jsonArray);
        if(userList.size()<30){
            returnJs.put("available", false);
        }else {
            returnJs.put("available",true);
        }
        return returnJs;
    }



    public MediaFile getPhotoFile(List<MediaFile> mediaFiles){
        MediaFile mediaFile = null;
        boolean status = true;
        while(status){
            for(int i =0;i<mediaFiles.size();i++){
                if(!mediaFiles.get(i).getPhotoURL().equals("")&& !mediaFiles.get(i).getSmallPhotoURL().equals("")){
                    mediaFile = mediaFiles.get(i);
                    status = false;
                }
            }
        }
        return mediaFile;
    }

    public JSONObject likeNews(String userUUID, String newsUUID){

        User user = db.getUserByPrivateUUID(userUUID);
        UserNews userNews =  userNewsDB.getNewsByUUID(newsUUID);
        JSONObject jsonObject = new JSONObject();
        LikeNews likeNews;

        int status = 0 ;
        if(user!=null && userNews!=null) {
             likeNews = likeDB.getUserLikeFromUserNews(user, userNews);
            if (likeNews == null) {
                likeNews = new LikeNews();
                likeNews.setNewsUUID(userNews.getUuid());
                likeNews.setUserUUID(user.getPrivateUUID());
                likeNews.setStatus(true);
                likeDB.saveLike(likeNews);
                status = likeNews.getDigitalStatus();
            } else {
                likeNews.changeStatus();
                likeDB.update(likeNews);
                status = likeNews.getDigitalStatus();
            }
        }
        jsonObject.put("status",status);
        jsonObject.put("likesCount",likeDB.getNewsLikesCount(newsUUID));
        return jsonObject;
    }


    public JSONObject commentUserNews(String newsUUID,String userUUID,String json){
        JSONObject jsonObject = new JSONObject(json);
        UserNews userNews = userNewsDB.getNewsByUUID(newsUUID);
        User user = db.getUserByPrivateUUID(userUUID);
        JSONObject response;
        if(user!=null && userNews!=null) {
            String text = jsonObject.getString("text");
            String to ;
            String commentUUID;
            if (jsonObject.has("to")) {
                to = jsonObject.getString("to");
                 commentUUID = jsonObject.getString("commentUUID");
            }else{
                to = "";
                commentUUID = "";
            }
            Comment comment = new Comment();
            comment.setNewsUUID(newsUUID);
            comment.setAuthorUUID(userUUID);
            comment.setText(text);
            comment.setDate(UnixTime());
            comment.setCommentUUID(UUID());
            comment.setToUserUUID(to);
            comment.setToCommentUUID(commentUUID);
            commentDB.saveComment(comment);
        }
        response = getNewsComments(newsUUID);
        return response;
    }

    public JSONObject getNewsComments(String newsUUID){
        List<Comment> newsComments = commentDB.getNewsComments(newsUUID);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1;
        if(!newsComments.isEmpty()){
            for(Comment comment : newsComments){
                User user = db.getUserByPrivateUUID(comment.getAuthorUUID());
                jsonObject1 = new JSONObject();
                jsonObject1.put("date",comment.getDate());
                jsonObject1.put("text",comment.getText());
                jsonObject1.put("avatarURL",user.getAvatarURL());
                jsonObject1.put("name",user.getName());
                if(!comment.getToCommentUUID().equals("")){
                    User user1 = db.getUserByPrivateUUID(comment.getToUserUUID());
                    jsonObject1.put("toUser",user1.getName());
                }
                jsonArray.put(jsonObject1);
            }
        }
        jsonObject.put("comments",jsonArray);
        return jsonObject;
    }


}
