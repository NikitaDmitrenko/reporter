package Iamreporter.Service;

import Iamreporter.ServicePack.Service;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Component
@Path("/topnews")
public class TopNews {

    Service service = new Service();

    @GET
    @Path("/{step}")
    public String getTopNews(@PathParam("step")int step){
        return service.getTopNews(step).toString();
    }

}
