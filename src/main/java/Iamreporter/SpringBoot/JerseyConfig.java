package Iamreporter.SpringBoot;

import Iamreporter.Service.AllReporter;
import Iamreporter.Service.Login;
import Iamreporter.Service.UserNewsService;
import Iamreporter.Service.UserProfile;
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
        register(UserNewsService.class);
        register(UserProfile.class);
        register(AllReporter.class);
        register(Login.class);

    }
}
