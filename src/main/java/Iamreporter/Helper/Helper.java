package Iamreporter.Helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import static java.lang.Math.*;

public class Helper {


    public static long UnixTime(){
        return System.currentTimeMillis()/1000;
    }

    public static String UUID(){
        return UUID.randomUUID().toString();
    }

    public static final String BIG_PHOLDER = "D:\\";

    public static final String SMALL_PHOLDER = "C:\\";

    public static final String WEB_URL = "";

    public static final String USER_BIG_PHOTO_URL = "http://server.tripodapp.com:8080/images/userbigPhoto/";
    public static final String USER_SMALL_PHOTO_URL = "http://server.tripodapp.com:8080/images/usersmallPhoto/";

    public static final String USER_BIG_PHOTO_LOCATION = "/opt/tomcat/webapps/ROOT/images/userbigPhoto/";
    public static final String USER_SMALL_PHOTO_LOCATION = "/opt/tomcat/webapps/ROOT/images/usersmallPhoto/";

    public static final String AVATARS_LOCATION = "/opt/tomcat/webapps/ROOT/images/avatars/";
    public static final String AVATARS_URL = "http://server.tripodapp.com:8080/images/avatars/";

    public static final String PHOTO_LOCATION_URL = "";
    public static final String PHOTO_WEB_URL = "";

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



}
