package Iamreporter.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/category")
public class CategoryNews {

    @GET
    @Path("/politics/{step: \\d}")
    public String getPoliticsNews(@PathParam("step")int id){
        return null;
    }

    @GET
    @Path("/life/{step: \\d}")
    public String getLifeNew(@PathParam("step")int id){
        return null;
    }

    @GET
    @Path("/sport/{step: \\d}")
    public String getSportNews(@PathParam("step")int id){
        return null;
    }

    @GET
    @Path("/humor/{step:\\d}")
    public String getHumorNews(){
        return null;
    }

    @GET
    @Path("/other/{step: \\d}")
    public String getOtherNews(){
        return null;
    }

}
