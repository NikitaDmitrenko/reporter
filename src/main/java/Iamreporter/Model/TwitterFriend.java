package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Embeddable
@Table(name = "TWITTER_FRIEND")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TwitterFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "REAL_NAME")
    private String realName;

    @Column(name = "TWITTER_ID")
    private String twitterID;

    @Column(name = "AVATAR_URL")
    private String tImageUrl;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public void setTwitterID(String twitterID) {
        this.twitterID = twitterID;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setTImageUrl(String tImageUrl) {
        this.tImageUrl = tImageUrl;
    }

    public String getTwiterID() {
        return twitterID;
    }

    public String getRealName() {
        return realName;
    }

    public String getTImageUrl() {
        return tImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTwitterID() {
        return twitterID;
    }

    public String gettImageUrl() {
        return tImageUrl;
    }

    public void settImageUrl(String tImageUrl) {
        this.tImageUrl = tImageUrl;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
