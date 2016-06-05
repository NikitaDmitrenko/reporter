package Iamreporter.Service;

import Iamreporter.DB.UserDB;
import Iamreporter.DB.UserNewsDB;
import Iamreporter.DB.ViewsDb;
import Iamreporter.Model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/{privateUserUUID}")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AllReporter {

    UserDB userDB = new UserDB();
    UserNewsDB userNewsDB = new UserNewsDB();
    ViewsDb viewsDb = new ViewsDb();

    @GET
    @Path("/readers/{step: \\d}")
    public String getMyReaders(@PathParam("privateUserUUID")String privateUUID,@PathParam("step")int step){
        List<User> famousUsers = userDB.getMyReaders(privateUUID);
        famousUsers = getUniqueNews(famousUsers, step * 30, (step + 1) * 30);
        return getNewsData(famousUsers).toString();
    }

    @GET
    @Path("/subscribers/{step: \\d}")
    public String getReporters(@PathParam("privaateUserUUID")String privateUUID,@PathParam("step")int step){
        List<User> famousUsers = userDB.getMySubscribers(privateUUID);
        famousUsers = getUniqueNews(famousUsers, step * 30, (step + 1) * 30);
        return getNewsData(famousUsers).toString();
    }

    @GET
    @Path("/allreporters/{step:\\d}")
    public String getAllReporters(@PathParam("privateUserUUID")String privateUUID,@PathParam("step")int step){
        List<User> allReporters = userDB.getAllReporters(privateUUID);
        allReporters = getUniqueNews(allReporters, step * 30, (step + 1) * 30);
        return getNewsData(allReporters).toString();
    }


    public List<User> getUniqueNews(List<User> userList, int start, int end){
        if(userList.size()<end){
            return new ArrayList<>(userList.subList(start,userList.size()));
        }else
            return new ArrayList<>(userList.subList(start,end));
    }

    public JSONObject getNewsData(List<User> reporters){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1;
        if(!reporters.isEmpty()){
            for(User user : reporters) {
                jsonObject1 = new JSONObject();
                jsonObject1.put("name",user.getName());
                jsonObject1.put("avatarURL",user.getAvatarURL());
                jsonObject1.put("description",user.getDescription());
                jsonObject1.put("city",user.getCity());
                jsonObject1.put("vievs",userNewsDB.getUserNewsViewsCount(user.getPrivateUUID()));
                jsonObject1.put("newsCount",userNewsDB.getUserNewsCount(user.getPrivateUUID()));
                jsonObject1.put("publicUUID",user.getPublicUUID());
                jsonArray.put(jsonObject1);
            }
        }
        if(reporters.size()<30){
            jsonObject.put("available", false);
        }else {
            jsonObject.put("available",true);
        }
        jsonObject.put("reporters",jsonArray);
        return jsonObject;
    }
}
