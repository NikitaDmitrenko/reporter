package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.*;
import Iamreporter.ServicePack.Service;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

import static Iamreporter.Helper.Helper.*;

@Path("/news")
@Component
public class UserNewsService {

    UserDB db = new UserDB();
    MediaFileDB mediaFileDB = new MediaFileDB();
    Service service = new Service();

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendNews(String json){
        return service.saveNewNews(json);
    }

    @POST
    @Path("/{newsUUID}/uploadnewsphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String uploadNewsPhoto(@PathParam("userUUID")String userUUID,
                                    @PathParam("newsUUID") String uuid,
                                   @FormDataParam("file") InputStream uploadedInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetail)throws IOException {
        User user = db.getUserByUUID(userUUID);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            String mediaFileUUID = UUID();
            String location = PHOTO_LOCATION_URL + uuid +mediaFileUUID + fileDetail.getFileName();
            String bigPhotourl = PHOTO_WEB_URL + uuid +mediaFileUUID + fileDetail.getFileName();
            String smallPhotoUrl = uuid + mediaFileUUID + fileDetail.getFileName();

            MediaFile file = new MediaFile();
            file.setDate(UnixTime());
            file.setPhotoURL(bigPhotourl);
            file.setSmallPhotoURL(smallPhotoUrl);
            file.setVideoURL("");
            file.setUuid(mediaFileUUID);
            file.setUserUUID(userUUID);

            mediaFileDB.saveUser(file);

            writeFile(uploadedInputStream, location);

            jsonObject.put("uploadstatus", true);
            jsonObject.put("photoURL", bigPhotourl);
        } else {
            jsonObject.put("uploadstatus", false);
        }
        return jsonObject.toString();
    }

    @POST
    @Path("/{newsUUID}/{userUUID}/uploadnewsvideo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public String uploadNewsVideo(@PathParam("userUUID")String userUUID,
                                  @PathParam("newsUUID")String uuid,
                                  @FormDataParam("file")InputStream uploadedInputStream,
                                  @FormDataParam("file")FormDataContentDisposition fileDetail)throws IOException {
        User user = db.getUserByUUID(userUUID);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {

            String mediaFileUUID = UUID();

            String location = PHOTO_LOCATION_URL + uuid + mediaFileUUID+ fileDetail.getFileName();
            String url = PHOTO_WEB_URL + uuid + mediaFileUUID + fileDetail.getFileName();

            MediaFile file = new MediaFile();

            file.setDate(UnixTime());
            file.setPhotoURL("");
            file.setSmallPhotoURL("");
            file.setVideoURL(url);
            file.setUuid(mediaFileUUID);
            file.setNewsUUID(uuid);
            file.setUserUUID(userUUID);

            mediaFileDB.saveUser(file);

            writeFile(uploadedInputStream, location);

            jsonObject.put("uploadstatus", true);
            jsonObject.put("videoURL", url);

        } else {
            jsonObject.put("uploadstatus", false);
        }
        return jsonObject.toString();
    }

    @GET
    @Path("/{newsUUID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getNewsByUUID(@PathParam("newsUUID")String uuid){
        return service.getNewsDate(uuid);
    }





}
