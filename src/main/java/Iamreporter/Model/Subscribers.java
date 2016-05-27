package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "SUBSCRIBERS")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Subscribers {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "USER_WHO_SUBSCRIBE")
    private String userWhoSubscribeUUID;

    @Column(name = "USER_ON_WHO_SUBSCRIBE")
    private String userOnWhoSubscribeUUID;

    @Column(name = "STATUS")
    private String status;

    public Subscribers() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserWhoSubscribeUUID() {
        return userWhoSubscribeUUID;
    }

    public void setUserWhoSubscribeUUID(String userWhoSubscribeUUID) {
        this.userWhoSubscribeUUID = userWhoSubscribeUUID;
    }

    public String getUserOnWhoSubscribeUUID() {
        return userOnWhoSubscribeUUID;
    }

    public void setUserOnWhoSubscribeUUID(String userOnWhoSubscribeUUID) {
        this.userOnWhoSubscribeUUID = userOnWhoSubscribeUUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
