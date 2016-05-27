package Iamreporter.ServicePack;

import Iamreporter.DB.UserDB;
import Iamreporter.Model.User;

import org.json.JSONObject;

import static Iamreporter.Helper.Helper.*;

public class InitUserService {

    private  UserDB userDB = new UserDB();

    public  User userInit(JSONObject js){
        User user ;
        String name = js.getString("name");
        if(js.has("facebook_ID")){
            String facebookId = js.getString("facebook_ID");
            user = userDB.getUserByFacebookId(facebookId);
            if(user==null){
                user = new User();
                user.setFacebookId(facebookId);
                user = setUserData(user,name);
            }
        }else if(js.has("twitter_ID")){
            String twitterId = js.getString("twitter_ID");
            user = userDB.getUserByTwitterId(twitterId);
            if(user==null){
                user = new User();
                user.setTwitterId(twitterId);
                user = setUserData(user,name);
            }
        }else {
            String vkontakteId = js.getString("vkontakte_ID");
            user = userDB.getUserByVkId(vkontakteId);
            if(user==null){
                user = new User();
                user.setVkId(vkontakteId);
                user = setUserData(user,name);
            }
        }
        return user;
    }

    public String getUUIDBySocials(String socialId) {
        JSONObject js = new JSONObject(socialId);
        User user = userInit(js);
        return new JSONObject().put("uuid", user.getPrivateUUID()).toString();
    }

    public User setUserData(User user,String name){
        user.setNickName(name);
        user.setPrivateUUID(UUID());
        user.setPublicUUID(UUID());
        userDB.saveUser(user);
        return user;
    }

}
