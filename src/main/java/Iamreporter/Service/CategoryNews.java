package Iamreporter.Service;

import Iamreporter.DB.MediaFileDB;
import Iamreporter.DB.UserNewsDB;
import Iamreporter.Model.MediaFile;
import Iamreporter.Model.UserNews;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;

@Path("/category")
@Component
public class CategoryNews {

    UserNewsDB userNewsDB = new UserNewsDB();
    MediaFileDB mediaFileDB = new MediaFileDB();

    @GET
    @Path("/politics/{step: \\d}")
    public String getPoliticsNews(@PathParam("step")int step){
        List<UserNews> politicNews = userNewsDB.getPoliticNews();
        politicNews = getUniqueNews(politicNews, step * 30, (step + 1) * 30);
        return shiftNewses(politicNews).toString();
    }

    @GET
    @Path("/life/{step: \\d}")
    public String getLifeNew(@PathParam("step")int step){
        List<UserNews> lifeNews = userNewsDB.getLifeNews();
        lifeNews = getUniqueNews(lifeNews, step * 30, (step + 1) * 30);
        return shiftNewses(lifeNews).toString();
    }

    @GET
    @Path("/sport/{step: \\d}")
    public String getSportNews(@PathParam("step")int step){
        List<UserNews> sportNews = userNewsDB.getSportNews();
        sportNews = getUniqueNews(sportNews, step * 30, (step + 1) * 30);
        return shiftNewses(sportNews).toString();
    }

    @GET
    @Path("/humor/{step:\\d}")
    public String getHumorNews(@PathParam("step")int step){
        List<UserNews> humourNews = userNewsDB.getHumourNews();
        humourNews = getUniqueNews(humourNews, step * 30, (step + 1) * 30);
        return shiftNewses(humourNews).toString();
    }

    @GET
    @Path("/other/{step: \\d}")
    public String getOtherNews(@PathParam("step")int step){
        List<UserNews> otherNews = userNewsDB.getAnotherNews();
        otherNews = getUniqueNews(otherNews, step * 30, (step + 1) * 30);
        return shiftNewses(otherNews).toString();
    }

    @GET
    @Path("/all/{step:\\d}")
    public String getAllNews(@PathParam("step")int step){
        List<UserNews> allNews = userNewsDB.getAllNews();
        allNews = getUniqueNews(allNews, step * 30, (step + 1) * 30);
        return shiftNewses(allNews).toString();
    }

    public List<UserNews> getUniqueNews(List<UserNews> userList, int start, int end){
        if(userList.size()<end){
            return new ArrayList<>(userList.subList(start,userList.size()));
        }else
            return new ArrayList<>(userList.subList(start,end));
    }

    public JSONObject shiftNewses(List<UserNews> userList) {
        JSONObject returnJs = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject js ;
        for (UserNews userNews : userList) {
            js = new JSONObject();
            List<MediaFile> newsPhotos = mediaFileDB.getNewsPhotos(userNews.getUuid());
            js.put("text",userNews.getText());
            js.put("theme",userNews.getUserNewsTheme());
            js.put("date",userNews.getDate());
            js.put("newsUUID",userNews.getUuid());
            js.put("countViews",userNews.getCountViews());
            if(!newsPhotos.isEmpty()) {
                MediaFile mediaFile1 = getPhotoFile(newsPhotos);
                if(mediaFile1!=null) {
                    js.put("bigPhotoURL", mediaFile1.getPhotoURL());
                    js.put("smallPhotoURL", mediaFile1.getSmallPhotoURL());
                }else{
                    js.put("bigPhotoURL", "");
                    js.put("smallPhotoURL", "");
                }
            }else{
                js.put("bigPhotoURL", "");
                js.put("smallPhotoURL", "");
            }
            jsonArray.put(js);
        }
        returnJs.put("topNews",jsonArray);
        if(userList.size()<30){
            returnJs.put("available", false);
        }else {
            returnJs.put("available",true);
        }
        return returnJs;
    }



    public MediaFile getPhotoFile(List<MediaFile> mediaFiles){
        MediaFile mediaFile = null;
        boolean status = true;
        while(status){
            for(int i =0;i<mediaFiles.size();i++){
                if(!mediaFiles.get(i).getPhotoURL().equals("")&& !mediaFiles.get(i).getSmallPhotoURL().equals("")){
                    mediaFile = mediaFiles.get(i);
                    status = false;
                }
            }
        }
        return mediaFile;
    }

}
