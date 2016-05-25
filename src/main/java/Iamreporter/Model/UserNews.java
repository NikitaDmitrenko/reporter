package Iamreporter.Model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USER_NEWS")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UserNews {

    @Id
    @GeneratedValue
    private int id;

    public UserNews() {
    }

    @Column(name = "THEME")
    private String userNewsTheme;

    @Column(name = "AUTHOR_UUID")
    private String authorUUID;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "NAME_SURNAME")
    private String nameSurName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NEWS_CATEGORIES")
    private Integer categories;

    @Column(name = "NEWS_UUID")
    private String uuid;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DATE")
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCategories() {
        return categories;
    }

    public void setCategories(Integer categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNewsTheme() {
        return userNewsTheme;
    }

    public void setUserNewsTheme(String userNewsTheme) {
        this.userNewsTheme = userNewsTheme;
    }

    public String getAuthorUUID() {
        return authorUUID;
    }

    public void setAuthorUUID(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameSurName() {
        return nameSurName;
    }

    public void setNameSurName(String nameSurName) {
        this.nameSurName = nameSurName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
