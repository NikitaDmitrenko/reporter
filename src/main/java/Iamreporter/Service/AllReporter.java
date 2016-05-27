package Iamreporter.Service;

import Iamreporter.DB.UserDB;
import Iamreporter.Model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/getReporters")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AllReporter {

    UserDB userDB = new UserDB();

    @GET
    public String getAllReporters(){
        List<User> reporters = userDB.getUsers();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1;
        if(!reporters.isEmpty()){
            for(User user : reporters) {
                jsonObject1 = new JSONObject();
                jsonObject1.put("nickName",user.getNickName());
                jsonObject1.put("name",user.getName());
                jsonObject1.put("callName",user.getCallName());
                jsonObject1.put("surName",user.getSurName());
                jsonObject1.put("avatarURL",user.getAvatarURL());
                jsonObject1.put("description",user.getDescription());
                jsonObject1.put("city",user.getCity());
                jsonObject1.put("vievs","");
                jsonObject1.put("likes","");
                jsonArray.put(jsonObject1);
            }
        }
        jsonObject.put("reporters",jsonArray);
        return jsonObject.toString();
    }

}
