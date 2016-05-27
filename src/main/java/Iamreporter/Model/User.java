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

    @Column(name = "USER_SURNAME")
    private String surName;

    @Column(name = "USER_NICK_NAME")
    private String nickName;

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

    @Column(name = "USER_UUID")
    private String userUUID;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "VK_ID")
    private String vkId;
    @Column(name = "FACEBOOK_ID")
    private String facebookId;
    @Column(name = "TWITTER_ID")
    private String twitterId;

    @ManyToMany
    private List<VkontakteFriend> vkontakteFriendList = new ArrayList<>();

    @ManyToMany
    private List<TwitterFriend> twitterFriends = new ArrayList<>();

    @ManyToMany
    private List<FacebookFriend> facebookFriends = new ArrayList<>();

    public List<VkontakteFriend> getVkontakteFriendList() {
        return vkontakteFriendList;
    }

    public void setVkontakteFriendList(List<VkontakteFriend> vkontakteFriendList) {
        this.vkontakteFriendList = vkontakteFriendList;
    }

    public List<TwitterFriend> getTwitterFriends() {
        return twitterFriends;
    }

    public void setTwitterFriends(List<TwitterFriend> twitterFriends) {
        this.twitterFriends = twitterFriends;
    }

    public List<FacebookFriend> getFacebookFriends() {
        return facebookFriends;
    }

    public void setFacebookFriends(List<FacebookFriend> facebookFriends) {
        this.facebookFriends = facebookFriends;
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

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
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
}
