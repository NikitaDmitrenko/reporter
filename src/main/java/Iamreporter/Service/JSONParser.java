package Iamreporter.Service;

import org.json.JSONObject;

/**
 * Created by Nikita on 21.05.2016.
 */
public class JSONParser {


    public String getEmail(String json){
        String email;
        JSONObject jsonObject = new JSONObject(json);
        if(jsonObject.has("email")){
            email = jsonObject.getString("email");
        }else{
            email = "";
        }
        return email;
    }

    public String getNewsTheme(String json){
        JSONObject jsonObject = new JSONObject();
        String theme;
        if(jsonObject.has("theme") && jsonObject.getString("theme").length()>6){
            theme = jsonObject.getString("theme");
        }else{
            theme = "";
        }
        return theme;
    }
}
