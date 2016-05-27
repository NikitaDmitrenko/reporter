package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GOOGLE_FRIEND")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VkontakteFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "REAL_NAME")
    private String realName;

    @Column(name = "GOOGLE_USER")
    private String vkontakteID;

    @Column(name = "AVATAR_URL")
    private String gImageUrl;

    @ManyToMany
    private List<User> users = new ArrayList<>();

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

    public String getVkontakteID() {
        return vkontakteID;
    }

    public void setVkontakteID(String vkontakteID) {
        this.vkontakteID = vkontakteID;
    }

    public String getgImageUrl() {
        return gImageUrl;
    }

    public void setgImageUrl(String gImageUrl) {
        this.gImageUrl = gImageUrl;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
