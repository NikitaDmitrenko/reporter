package Iamreporter.Model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Embeddable
@Table(name = "FACEBOOK_FRIEND")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FacebookFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "REAL_NAME")
    private String realName;

    @Column(name = "FACEBOOK_ID")
    private String facebookID;

    @Column(name = "AVATAR_URL")
    private String fImageUrl;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public FacebookFriend() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getfImageUrl() {
        return fImageUrl;
    }

    public void setfImageUrl(String tImageUrl) {
        this.fImageUrl = tImageUrl;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
