package Iamreporter.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity

@Table(name = "FRIENDS")

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class FriendRelation {

    @Id
    @GeneratedValue
    private int id;

    private String userWhoAddUUID;

    private String userWhomAddUUID;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserWhoAddUUID() {
        return userWhoAddUUID;
    }

    public void setUserWhoAddUUID(String userWhoAddUUID) {
        this.userWhoAddUUID = userWhoAddUUID;
    }

    public String getUserWhomAddUUID() {
        return userWhomAddUUID;
    }

    public void setUserWhomAddUUID(String userWhomAddUUID) {
        this.userWhomAddUUID = userWhomAddUUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FriendRelation changeStatus(){
        if(this.status.equals("subscribe")){
            this.status = "unsubscribe";
        }else{
            this.status = "subscribe";
        }
        return this;
    }

    public int getDigitalStatus(){
        int status = 0;
        if(this.getStatus().equals("subscribe")){
            status = 1;
        }
        return status;
    }


}
