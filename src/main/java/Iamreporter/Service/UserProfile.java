package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.*;
import Iamreporter.ServicePack.FriendAction;
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

    Service service = new Service();
    UserNewsDB userNewsDB = new UserNewsDB();
    UserDB db = new UserDB();
    MediaFileDB mediaFileDB = new MediaFileDB();
    FriendAction action = new FriendAction();
    FriendRelationDB friendRelationDB = new FriendRelationDB();

    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String getSelfProfile(@PathParam("userUUID")String privateUUID){
        return getUserData(privateUUID).toString();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String updateProfile(@PathParam("userUUID") String userUUID, String json) {
        JSONObject jsonObject = new JSONObject(json);
        User user = db.getUserByPrivateUUID(userUUID);
        JSONObject jsonObject1 ;
        if (user != null) {
            user.setCity(jsonObject.getString("city"));
            user.setDescription(jsonObject.getString("description"));
            db.updateUser(user);
            jsonObject1 = getUserData(userUUID);
        }else{
            jsonObject1 = new JSONObject();
        }

        return jsonObject1.toString();
    }


    @GET
    @Path("/myNews/{newsUUID}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String myNews(@PathParam("userUUID")String privateUUID,@PathParam("newsUUID")String newsuUID){
        User user = db.getUserByPrivateUUID(privateUUID);
        JSONObject jsonObject = new JSONObject();
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
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String getUserProfile(@PathParam("userUUID")String privateUUID, @PathParam("publicUserUUID")String publicUserUUID){
        User user = db.getUserByPrivateUUID(privateUUID);
        JSONObject jsonObject = new JSONObject();
        User anotherUser = db.getUserByPublicUUID(publicUserUUID);
        if(user!=null) {
            jsonObject = getUserData(anotherUser.getPrivateUUID());
            jsonObject.put("publicUUID",publicUserUUID);
        }
        return jsonObject.toString();
    }

    @POST
    @Path("/anotherUser/{publicUserUUID}/sendMessage")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
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
            jsonObject.put("send", false);
        }
        return  jsonObject.toString();
    }

    @POST
    @Path("/anotherUser/{publicUserUUID}/subscribe")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String subscribeOnUser(@PathParam("userUUID")String userUUID,@PathParam("publicUserUUID")String publicUserUUID) {
        User user = db.getUserByPrivateUUID(userUUID);
        User anotherUser = db.getUserByPublicUUID(publicUserUUID);
        JSONObject jsonObject = new JSONObject();
        int status = 0;
        if(user!=null && anotherUser!=null) {
            status = action.startFriendRelation(user, anotherUser);
        }
        jsonObject.put("status",status);
        return jsonObject.toString();
    }


    public JSONArray getUserNews(String userUUID){
        List<UserNews> userNewsLIst = userNewsDB.getUserNews(userUUID);
        JSONArray jsonArray = new JSONArray();
        JSONObject js;
        for(UserNews news : userNewsLIst){
            js = new JSONObject();
            List<MediaFile> mediaFile = mediaFileDB.getNewsPhotos(news.getUuid());
            js.put("text",news.getText());
            js.put("theme",news.getUserNewsTheme());
            js.put("viewsCount",news.getCountViews());
            js.put("date",news.getDate());
            if(!mediaFile.isEmpty()) {
                MediaFile mediaFile1 = getPhotoFile(mediaFile);
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
        return jsonArray;
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

    public JSONObject getUserData(String privateUUID){
        User user = db.getUserByPrivateUUID(privateUUID);
        JSONObject jsonObject = new JSONObject();
        if(user!=null) {
            jsonObject.put("name", user.getName());
            jsonObject.put("email",user.getEmail());
            jsonObject.put("city", user.getCity());
            jsonObject.put("avatarURL",user.getAvatarURL());
            jsonObject.put("description", user.getDescription());
            jsonObject.put("views", userNewsDB.getUserNewsViewsCount(user.getPrivateUUID()));
            jsonObject.put("news", getUserNews(user.getPrivateUUID()));
            jsonObject.put("subscribers",friendRelationDB.getUserSubbscribersCount(user.getPrivateUUID()));
        }
        return jsonObject;
    }
}
