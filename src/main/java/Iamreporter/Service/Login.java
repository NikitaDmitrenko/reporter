package Iamreporter.Service;


import Iamreporter.DB.UserDB;
import Iamreporter.Helper.Helper;
import Iamreporter.Model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static Iamreporter.Helper.Helper.UnixTime;

@Path("/")
@Component
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class Login {

    UserDB userDB = new UserDB();

    @POST
    @Path("/login")
    public String login(String json){
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        User user = userDB.getUserByEmailPassword(email, password);
        JSONObject jsonObject1 = new JSONObject();
        if(user!=null){
            jsonObject1.put("userUUID",user.getPrivateUUID());
            jsonObject1.put("login",true);
        }else{
            jsonObject1.put("userUUID","");
            jsonObject1.put("login",false);
        }
        return jsonObject1.toString();
    }

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createNewAccount(String json) {
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
            if (status && userDB.getUserByEmail(email)==null) {
                final String privateUUID  = Helper.UUID();
                final String publicUUID = Helper.UUID();

                User user = new User();

                user.setName(userName);
                user.setEmail(email);
                user.setPassword(password);
                user.setPrivateUUID(privateUUID);
                user.setPublicUUID(publicUUID);
                user.setDate(UnixTime());

                userDB.saveUser(user);

                response.put("uuid",publicUUID);
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

}
