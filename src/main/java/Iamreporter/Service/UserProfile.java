package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import Iamreporter.ServicePack.Service;
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
    Service service = new Service();

    @GET
    public String getSelfProfile(@PathParam("userUUID")String privateUUID){
        User user = db.getUserByPrivateUUID(privateUUID);
        JSONObject jsonObject = new JSONObject();
        if(user!=null) {
            jsonObject.put("name", user.getName());
            jsonObject.put("nickName", user.getNickName());
            jsonObject.put("callName",user.getCallName());
            jsonObject.put("mobilePhone",user.getMobilePhone());
            jsonObject.put("city", user.getCity());
            jsonObject.put("description", user.getDescription());
            jsonObject.put("views", getUserViewsCount(user.getPrivateUUID()));
            jsonObject.put("news", getSelfNews(user.getPrivateUUID()));
        }
        return jsonObject.toString();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String updateProfile(@PathParam("userUUID") String userUUID, String json) {
        JSONObject jsonObject = new JSONObject(json);

        User user = db.getUserByPrivateUUID(userUUID);
        if (user != null) {
            user.setCity(jsonObject.getString("city"));
            user.setMobilePhone(jsonObject.getString("mobilePhone"));
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
        User user = db.getUserByPrivateUUID(uuid);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            String avatarLocation = AVATARS_LOCATION + user.getPrivateUUID() + ".jpg";
            String avatarURL = AVATARS_URL + user.getPrivateUUID() + ".jpg";
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




    @GET
    @Path("/anotherUser/{publicUserUUID}")
    public String getUserProfile(@PathParam("userUUID")String privateUUID, @PathParam("publicUserUUID")String publicUserUUID){
        User user = db.getUserByPrivateUUID(privateUUID);
        JSONObject jsonObject = new JSONObject();
        User anotherUser = db.getUserByPublicUUID(publicUserUUID);
        if(user!=null) {
            jsonObject.put("name",anotherUser.getName());
            jsonObject.put("nickName",anotherUser.getNickName());
            jsonObject.put("callName",anotherUser.getCallName());
            jsonObject.put("mobilePhone",anotherUser.getMobilePhone());
            jsonObject.put("city",anotherUser.getCity());
            jsonObject.put("description",anotherUser.getDescription());
            jsonObject.put("views",getUserViewsCount(anotherUser.getPrivateUUID()));
            jsonObject.put("publicUUID",publicUserUUID);
            jsonObject.put("news",getUserNews(anotherUser.getPrivateUUID()));
        }
        return jsonObject.toString();
    }

    @POST
    @Path("/anotherUser/{publicUserUUID}/sendMessage")
    public String sendMessageToAnotherUser(@PathParam("userUUID")String userUUID,
                                           @PathParam("publicUserUUID")String publicUserUUID,
                                           String json){
        User user = db.getUserByPrivateUUID(userUUID);
        User anotherUser = db.getUserByPublicUUID(publicUserUUID);
        JSONObject jsonObject = new JSONObject();
        if(user!=null && anotherUser!=null){
            service.SendMailToUser(user,anotherUser,json);
            jsonObject.put("send",true);
        }else{
            jsonObject.put("send",false);
        }
        return  jsonObject.toString();
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
            js.put("viewsCount",viewsDb.getUserNewsCountViews(news.getUuid()));
            js.put("likes",likesDB.getNewsLikesCount(news.getUuid()));
            js.put("date",news.getDate());
            jsonArray.put(js);
        }
        jsonObject.put("news",jsonArray);
        return jsonObject.toString();
    }

    public String getSelfNews(String userUUID){
        List<UserNews> userNewsLIst = userNewsDB.getUserNews(userUUID);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject js;
        for(UserNews news : userNewsLIst){
            js = new JSONObject();
            js.put("text",news.getText());
            js.put("theme",news.getUserNewsTheme());
            js.put("date",news.getDate());
            jsonArray.put(js);
        }
        jsonObject.put("news",jsonArray);
        return jsonObject.toString();
    }
}
