package Iamreporter.ServicePack;

import Iamreporter.DB.UserDB;
import Iamreporter.Model.FacebookFriend;
import Iamreporter.Model.TwitterFriend;
import Iamreporter.Model.User;

import Iamreporter.Model.VkontakteFriend;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.UUID;

public class InitUserService {

    static UserDB userDAO = new UserDB();

    public static ArrayList<FacebookFriend> getFacebookFriends (JSONObject js){
        JSONArray jsonFacebookFriends = js.getJSONArray("facebookFriends");
        ArrayList<FacebookFriend> facebookFriends = new ArrayList<>();
        Gson gson = new Gson();
        for(int i = 0;i<jsonFacebookFriends.length();i++) {
            FacebookFriend facebookFriend = gson.fromJson(jsonFacebookFriends.getJSONObject(i).toString(), FacebookFriend.class);
            userDAO.saveFacebookFriend(facebookFriend);
            facebookFriends.add(facebookFriend);
        }
        return facebookFriends;
    }

    public static ArrayList<TwitterFriend> getTwitterFriends (JSONObject js){
        JSONArray jsonTwitterFriends = js.getJSONArray("twitterFriends");
        ArrayList<TwitterFriend> twitterFriends= new ArrayList<>();
        Gson gson = new Gson();
        for(int i = 0;i<jsonTwitterFriends.length();i++){
            TwitterFriend twitterFriend = gson.fromJson(
                    jsonTwitterFriends.getJSONObject(i).toString(),
                    TwitterFriend.class);
            userDAO.saveTwitterFriend(twitterFriend);
            twitterFriends.add(twitterFriend);
        }
        return twitterFriends;
    }

    public static ArrayList<VkontakteFriend> getVkontkteFriends(JSONObject js){
        JSONArray jsonGoogleFriends = js.getJSONArray("vkontakteFriends");
        ArrayList<VkontakteFriend> VkontakteFriends = new ArrayList<>();
        Gson gson = new Gson();
        for(int i = 0;i<jsonGoogleFriends.length();i++){
            VkontakteFriend googleFriend = gson.fromJson(jsonGoogleFriends.getJSONObject(i).toString(),VkontakteFriend.class);
            userDAO.saveVkontakteFriend(googleFriend);
            VkontakteFriends.add(googleFriend);
        }
        return VkontakteFriends;
    }

    public static User userInit(JSONObject js){
        User user = new User();
        String name = js.getString("name");
        UUID uuid = UUID.randomUUID();
        user.setNickName(name);
        user.setUserUUID(uuid.toString());
        if(js.has("facebook_ID")){
            user.setFacebookId(js.getString("facebook_ID"));
            user.setFacebookFriends(getFacebookFriends(js));
        }else if(js.has("twitter_ID")){
            user.setTwitterId(js.getString("twitter_ID"));
            user.setTwitterFriends(getTwitterFriends(js));
        }else {
            user.setVkId(js.getString("vkontakte_ID"));
            user.setVkontakteFriendList(getVkontkteFriends(js));
        }
        return user;
    }

}
