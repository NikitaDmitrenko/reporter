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

@Component
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SocialRegistrationResource {

    UserDB userDB = new UserDB();

    @POST
    @Path("/twitterRegist")
    public String registTwitter(String twitterId) {
        return getUUIDBySocials(twitterId);
    }

    @POST
    @Path("/vkontakteRegist")
    public String registvkontake(String vkontakteId) {
        return getUUIDBySocials(vkontakteId);
    }

    @POST
    @Path("/facebookRegist")
    public String registFacebook(String facebookId) {
        return getUUIDBySocials(facebookId);
    }

    public String getUUIDBySocials(String socialId) {
        JSONObject js = new JSONObject(socialId);
        User user = InitUserService.userInit(js);
        userDB.saveUser(user);
        return new JSONObject().put("uuid", user.getUserUUID()).toString();
    }
}
