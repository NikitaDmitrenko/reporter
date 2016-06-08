package Iamreporter.Model;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static Iamreporter.Helper.Helper.*;

public class GCM {

    private String to;

    private Data data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public GCM(String to,Data data) {
        this.to = to;
        this.data = data;
    }

    public void pushMessageToTopic(String chatURL, Data data) {
        GCM gcm = new GCM("/topics/" + chatURL, data);
        gcm.sendPushNotifications();
    }

    public void pushMessage(String registrationId, Data data) {
        GCM gcm = new GCM(registrationId, data);
        gcm.sendPushNotifications();
    }

    public String sendPushNotifications(){
        Client client = ClientBuilder.newClient();
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        WebTarget webTarget = client.target(GCM_SERVER);
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).
                header("Content-Type","application/json").
                header("Authorization", "key=AIzaSyAuJY3f2kUfAj1tQlrHbJLfaWRBPCjzA0c").post(Entity.json(this));
        return response.toString();
    }
}
