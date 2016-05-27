package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static Iamreporter.Helper.Helper.*;

@Path("/userprofile/{userUUID}")
@Component
public class UserProfile {

    UserDB db = new UserDB();
    UserNewsDB userNewsDB = new UserNewsDB();
    ViewsDb viewsDb = new ViewsDb();
    LikesDB likesDB = new LikesDB();
    CommentDB commentDB = new CommentDB();


    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String updateProfile(@PathParam("userUUID") String userUUID, String json) {
        JSONObject jsonObject = new JSONObject(json);

        User user = db.getUserByUUID(userUUID);
        if (user != null) {
            user.setCity(jsonObject.getString("city"));
            user.setMobilePhone(jsonObject.getString("phone"));
            user.setEmail(jsonObject.getString("email"));
            user.setName(jsonObject.getString("name"));
            user.setDescription(jsonObject.getString("description"));
            db.updateUser(user);
        }
        return null;
    }


    @PUT
    @Path("/uploadavatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String uploadUserAvatar(@PathParam("userUUID") String uuid,
                                   @FormDataParam("file") InputStream uploadedInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetail)throws IOException{
        User user = db.getUserByUUID(uuid);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            String avatarLocation = AVATARS_LOCATION + user.getUserUUID() + ".jpg";
            String avatarURL = AVATARS_URL + user.getUserUUID() + ".jpg";
            user.setAvatarURL(avatarURL);
            db.updateUser(user);

            writeFile(uploadedInputStream, avatarLocation);

            jsonObject.put("user", true);
            jsonObject.put("uploadstatus", true);
            jsonObject.put("avatarURL", avatarURL);
        } else {
            jsonObject.put("user", false);
            jsonObject.put("uploadstatus", false);
        }
        return jsonObject.toString();
    }

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createNewAccount(@PathParam("uuid")String uuid,String json) {
        JSONObject js = new JSONObject(json);
        JSONObject response = new JSONObject();
        String userName = js.getString("name");

        String password = js.getString("password");
        String repassword = js.getString("repassword");
        String email = js.getString("email");
        boolean status;
        if (password.equals(repassword)) {
            response.put("password", true);
            status = EmailValidator.isValidEmailAddress(email);
            if (status && db.getUserByEmail(email)==null) {
                User user = new User();
                user.setName(userName);
                user.setEmail(email);
                user.setPassword(password);
                user.setUserUUID(uuid);
                user.setDate(UnixTime());
                db.saveUser(user);
                response.put("uuid",uuid);
                response.put("email", true);
            } else {
                response.put("email", false);
            }
        } else {
            response.put("email", false);
            response.put("password", false);
        }
        return response.toString();
    }


    @GET
    @Path("/anotherUser/{name}/{surName}")
    public String getUserProfile(@PathParam("userUUID")String userUUID, @PathParam("name")String name,@PathParam("surName")String surName){
        User user = db.getUserByUUID(userUUID);
        JSONObject jsonObject = new JSONObject();
        User anotherUser = db.getUserByNameSurName(name, surName);
        if(user!=null) {
            jsonObject.put("name",anotherUser.getName());
            jsonObject.put("surName",anotherUser.getSurName());
            jsonObject.put("nickName",anotherUser.getNickName());
            jsonObject.put("city",anotherUser.getCity());
            jsonObject.put("description",anotherUser.getDescription());
            jsonObject.put("views",getUserViewsCount(anotherUser.getUserUUID()));
            jsonObject.put("news",getUserNews(anotherUser.getUserUUID()));
        }
        return jsonObject.toString();
    }

    public int getUserViewsCount(String userUUID){
        List<String> newsUUIDs = userNewsDB.getUserNewsUUIDS(userUUID);
        int count = 0;
        if(!newsUUIDs.isEmpty()){
            count = userNewsDB.getAuthorViewsCount(newsUUIDs);
        }
        return count;
    }

    public String getUserNews(String userUUID){
        List<UserNews> userNewsLIst = userNewsDB.getUserNews(userUUID);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject js;
        for(UserNews news : userNewsLIst){
            js = new JSONObject();
            js.put("text",news.getText());
            js.put("theme",news.getUserNewsTheme());
            js.put("vievsCount",viewsDb.getUserNewsCountViews(news.getUuid()));
            js.put("likes",likesDB.getNewsLikesCount(news.getUuid()));
            js.put("comments",commentDB.getNewsCommentsCount(news.getUuid()));
            js.put("date",news.getDate());
            jsonArray.put(js);
        }
        jsonObject.put("news",jsonArray);
        return jsonObject.toString();
    }
}
