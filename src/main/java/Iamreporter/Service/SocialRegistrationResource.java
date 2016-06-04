package Iamreporter.Service;

import Iamreporter.ServicePack.InitUserService;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/social")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SocialRegistrationResource {

    InitUserService service = new InitUserService();

    @POST
    @Path("/twitterRegist")
    public String registTwitter(String twitterId) {
        return service.getUUIDBySocials(twitterId);
    }

    @POST
    @Path("/vkontakteRegist")
    public String registvkontake(String vkontakteId) {
        return service.getUUIDBySocials(vkontakteId);
    }

    @POST
    @Path("/facebookRegist")
    public String registFacebook(String facebookId) {
        return service.getUUIDBySocials(facebookId);
    }


}
