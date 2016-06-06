package Iamreporter.SpringBoot;

import Iamreporter.Service.*;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@Configuration
@ApplicationPath("/webapi")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        register(MultiPartFeature.class);
        register(AllReporter.class);
        register(CategoryNews.class);
        register(Feed.class);
        register(Login.class);
        register(SocialRegistrationResource.class);
        register(TopNews.class);
        register(UserNewsService.class);
        register(UserProfile.class);
        register(Registration.class);
    }
}
