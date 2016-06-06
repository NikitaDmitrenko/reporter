package Iamreporter.Service;

import Iamreporter.DB.UserDB;
import Iamreporter.Helper.Helper;
import Iamreporter.Model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static Iamreporter.Helper.Helper.UnixTime;

@Path("/registration")
@Component
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class Registration {

    UserDB userDB = new UserDB();

    @POST
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
            status = isValidEmailAddress(email);
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
                user.setCity("");
                user.setAvatarURL("");
                user.setDescription("");
                user.setFacebookId("");
                user.setVkId("");
                user.setTwitterId("");

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

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
