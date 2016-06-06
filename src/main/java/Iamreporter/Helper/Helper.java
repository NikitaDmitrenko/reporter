package Iamreporter.Helper;

import Iamreporter.Service.UserNewsService;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import static java.lang.Math.*;

public class Helper {


    public static long UnixTime(){
        return System.currentTimeMillis()/1000;
    }

    public static String UUID(){
        return UUID.randomUUID().toString();
    }

    public static final String ARCHIVE_LOCATION = "/var/lib/tomcat7/webapps/ROOT/image/archive/";
    public static final String ARCHIVE_URL = "http://test.mediaretail.com.ua:8095/image/archive/";
    //D:

    public static final String VIDEO_FILE_URL = "http://test.mediaretail.com.ua:8095/image/videofile/";
    public static final String VIDEO_FILE_LOCATION = "/var/lib/tomcat7/webapps/ROOT/image/videofile/";
    //D:\

    public static final String BIG_PHOTO_LOCATION ="/var/lib/tomcat7/webapps/ROOT/image/bigPhoto/";
    public static final String BIG_PHOTO_URL = "http://test.mediaretail.com.ua:8095/image/bigPhoto/";
    //D:\

    public static final String SMALL_PHOTO_LOCATION = "/var/lib/tomcat7/webapps/ROOT/image/smallPhoto/";
    public static final String SMALL_PHOTO_URL = "http://test.mediaretail.com.ua:8095/image/smallPhoto/";
    //C:\

    public static final String AVATARS_LOCATION = "/var/lib/tomcat7/webapps/ROOT/image/avatars/";
    //D:\
    public static final String AVATARS_URL = "http://test.mediaretail.com.ua:8095/image/avatars/";

    public static final String GOOGLE_MAPS = "https://maps.googleapis.com/maps/api/geocode/json?latlng=latitude,longitude&key=AIzaSyCnzsWY6u5anRnUgwGJOZ2Bqa72buIaNv8";

    public static double getDimension(String fileURL)  {
        double dimension = 0.0;
        BufferedImage bimg;
        try {
            bimg = ImageIO.read(new URL(fileURL));
            int width          = bimg.getWidth();
            int height         = bimg.getHeight();
            dimension = (double) width/ (double) height;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  round(dimension * 100.0)/100.0;
    }

    public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
        String ext = FilenameUtils.getExtension(filePath);
        if(ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("gif")){
            resizeImage(filePath);
        }
    }

    public static void writeFile(InputStream uploadedInputStream, String uploadedFileLocation)throws IOException{
        try(FileOutputStream out  = new FileOutputStream(uploadedFileLocation)) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resizeImage(String uploadedFileLocation) throws IOException{
        BufferedImage image = ImageIO.read(new File(uploadedFileLocation));

        String smallFileLocation = uploadedFileLocation.replace(BIG_PHOTO_LOCATION,SMALL_PHOTO_LOCATION);

        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();

        BufferedImage smallImage;
        BufferedImage resizedImage;

        int width = image.getWidth();
        int height = image.getHeight();

        int smallWidth;
        int smallHeight;

        double percent;
        if(width>=2100 || height>=2100) {
            if (width > height) {

                percent = width / 2100;
                width /= percent;
                height /= percent;
                smallWidth = width/2;
                smallHeight = height/2;

                resizedImage = resize(image, width, height, type);
                ImageIO.write(resizedImage, UserNewsService.getFileExtension(uploadedFileLocation), new File(uploadedFileLocation));
                smallImage = resize(image,smallWidth,smallHeight,type);
                ImageIO.write(smallImage, UserNewsService.getFileExtension(uploadedFileLocation),new File(smallFileLocation));
            } else {

                percent = height / 2100;
                width /= percent;
                height /= percent;
                smallWidth = width/2;
                smallHeight = height/2;
                resizedImage = resize(image, width, height, type);
                ImageIO.write(resizedImage,UserNewsService.getFileExtension(uploadedFileLocation), new File(uploadedFileLocation));
                smallImage = resize(image,smallWidth,smallHeight,type);
                ImageIO.write(smallImage,UserNewsService.getFileExtension(uploadedFileLocation),new File(smallFileLocation));
            }
        }
        smallWidth = width/2;
        smallHeight = height/2;
        smallImage = resize(image,smallWidth,smallHeight,type);
        ImageIO.write(smallImage,UserNewsService.getFileExtension(uploadedFileLocation),new File(smallFileLocation));
    }

    private static BufferedImage resize(BufferedImage originalImage,int width,int height, int type){
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

}
