package Iamreporter.Service;


import Iamreporter.DB.UserDB;
import Iamreporter.Model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Component
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class Login {


    UserDB userDB = new UserDB();

    @POST
    public String login(String json){
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        User user = userDB.getUserByEmailPassword(email, password);
        JSONObject jsonObject1 = new JSONObject();
        if(user!=null){
            jsonObject1.put("userUUID",user.getUserUUID());
            jsonObject1.put("login",true);
        }else{
            jsonObject1.put("userUUID","");
            jsonObject1.put("login",false);
        }
        return jsonObject1.toString();
    }

}
