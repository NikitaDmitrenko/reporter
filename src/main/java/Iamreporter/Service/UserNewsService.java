package Iamreporter.Service;

import Iamreporter.DB.*;
import Iamreporter.Model.*;
import Iamreporter.ServicePack.Service;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static Iamreporter.Helper.Helper.*;

@Path("/{userUUID}/news")
@Component
public class UserNewsService {

    UserDB db = new UserDB();
    Service service = new Service();
    MediaFileDB mediaFileDB = new MediaFileDB();
    ViewsDb viewsDb = new ViewsDb();

    @Path("/create")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendNews( @PathParam("userUUID") String userUUID,
                            @FormDataParam("file")InputStream uploadedInputStream,
                            @FormDataParam("file")FormDataContentDisposition fileDetail,
                            @FormDataParam("json")String json) throws IOException{
        User user = db.getUserByPrivateUUID(userUUID);
        String newsUUID = UUID();
        if(user!=null){
            if(fileDetail!=null && FilenameUtils.getExtension(fileDetail.getFileName()).equalsIgnoreCase("zip") && uploadedInputStream != null) {
                String location = ARCHIVE_LOCATION + fileDetail.getFileName();
                writeFile(uploadedInputStream, location);
                unzipMediaFiles(user.getPrivateUUID(), location, newsUUID);
            }
        }
        return service.saveNewNews(json,userUUID,newsUUID);
    }


    @GET
    @Path("/{newsUUID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getNewsByUUID(@PathParam("newsUUID")String uuid,@Context HttpServletRequest request){
        saveNewsView(uuid,request);

        return service.getNewsDate(uuid);
    }

    public void unzipMediaFiles(String userUUID, String zipFilePath, String newsUUID) throws IOException {

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("Cp1251"));

        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {

            String ext = getFileExtension(entry.getName());
            String filePath = "";
            if(!ext.equals("")&&(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("gif"))) {
                filePath = BIG_PHOTO_LOCATION + newsUUID + entry.getName();
            }else if (ext.equals("avi")||ext.equals("mp4")||ext.equals("mkv")) {
                filePath = VIDEO_FILE_LOCATION + newsUUID + entry.getName();
            }
            saveMediaFile(userUUID, filePath, newsUUID);
            extractFile(zipIn, filePath);
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    public void saveMediaFile(String userUUID,String filePath,String newsUUID){
        MediaFile mediaFile = new MediaFile();
        mediaFile.setDate(UnixTime());
        mediaFile.setNewsUUID(newsUUID);
        mediaFile.setUuid(UUID());
        mediaFile.setUserUUID(userUUID);
        String ext = FilenameUtils.getExtension(filePath);
        if(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("gif")){
            mediaFile.setPhotoURL(filePath.replace(BIG_PHOTO_LOCATION,BIG_PHOTO_URL));
            mediaFile.setSmallPhotoURL(filePath.replace(BIG_PHOTO_URL, SMALL_PHOTO_URL));
            mediaFile.setVideoURL("");
        }else if(ext.equalsIgnoreCase("avi")||ext.equalsIgnoreCase("mp4")||ext.equalsIgnoreCase("mkv")){
            mediaFile.setVideoURL(filePath.replace(VIDEO_FILE_LOCATION,VIDEO_FILE_URL));
            mediaFile.setPhotoURL("");
            mediaFile.setSmallPhotoURL("");
        }
        mediaFileDB.saveMediaFile(mediaFile);
    }

    public static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else
            return "";
    }

    public void saveNewsView (String uuid,HttpServletRequest request){
        String ipAdress = request.getRemoteAddr();
        Views views = new Views();
        views.setNewsUUID(uuid);
        views.setIpAdress(ipAdress);
        viewsDb.save(views);
    }



}
