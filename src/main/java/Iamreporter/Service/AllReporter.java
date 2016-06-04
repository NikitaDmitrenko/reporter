package Iamreporter.Service;

import Iamreporter.DB.UserDB;
import Iamreporter.DB.UserNewsDB;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/{privateUserUUID}/getReporters")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AllReporter {

    UserDB userDB = new UserDB();
    UserNewsDB userNewsDB = new UserNewsDB();

    @GET
    @Path("/{step: \\d}")
    public String getAllReporters(@PathParam("privateUserUUID")String privateUserUUID,@PathParam("step")int id){
        return getReporters(privateUserUUID,id).toString();
    }

    public JSONObject getReporters(String privateUUID,int step){
        List<User> famousUsers = userDB.getUsers(privateUUID);
        famousUsers = getUniqueNews(famousUsers, step * 30, (step + 1) * 30);
        return shiftNewses(famousUsers);
    }


    public List<User> getUniqueNews(List<User> userList, int start, int end){
        if(userList.size()<end){
            return new ArrayList<>(userList.subList(start,userList.size()));
        }else
            return new ArrayList<>(userList.subList(start,end));
    }

    public JSONObject shiftNewses(List<User> reporters){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1;
        if(!reporters.isEmpty()){
            for(User user : reporters) {
                jsonObject1 = new JSONObject();
                jsonObject1.put("name",user.getName());
                jsonObject1.put("callName",user.getCallName());
                jsonObject1.put("avatarURL",user.getAvatarURL());
                jsonObject1.put("description",user.getDescription());
                jsonObject1.put("city",user.getCity());
                jsonObject1.put("vievs","");
                jsonObject1.put("newsCount",userNewsDB.getUserNewsCount(user.getPrivateUUID()));
                jsonObject1.put("publicUUID",user.getPublicUUID());
                jsonArray.put(jsonObject1);
            }
        }
        jsonObject.put("reporters",jsonArray);
        return jsonObject;
    }



}
