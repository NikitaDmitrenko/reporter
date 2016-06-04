package Iamreporter.Model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class User {

    public User() {
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(name="USER_NAME")
    private String name;

    @Column(name = "AVATAR_URL")
    private String avatarURL;

    @Column(name = "CALL_NAME")
    private String callName;

    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    private long date;

    @Column(name = "PRIVATE_UUID")
    private String privateUUID;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PUBLIC_UUID")
    private String publicUUID;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "VK_ID")
    private String vkId;
    @Column(name = "FACEBOOK_ID")
    private String facebookId;
    @Column(name = "TWITTER_ID")
    private String twitterId;

    public String getPublicUUID() {
        return publicUUID;
    }

    public void setPublicUUID(String publicUUID) {
        this.publicUUID = publicUUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameSurName) {
        this.name = nameSurName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPrivateUUID() {
        return privateUUID;
    }

    public void setPrivateUUID(String userUUID) {
        this.privateUUID = userUUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", callName='" + callName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", privateUUID='" + privateUUID + '\'' +
                ", email='" + email + '\'' +
                ", publicUUID='" + publicUUID + '\'' +
                ", password='" + password + '\'' +
                ", vkId='" + vkId + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", twitterId='" + twitterId + '\'' +
                '}';
    }
}
