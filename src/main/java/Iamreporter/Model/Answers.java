package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Answers {


    @Id
    @GeneratedValue
    private int id;

    @Column(name = "AUTHOR_UUID")
    private String authorUUID;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "COUNT")
    private int count;

    @Column(name = "USER_WHO_ADD_UUID")
    private String userWhoAddUUID;

    @Column(name = "USER_NEWS_UUID")
    private String userNewsUUID;

    public Answers() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorUUID() {
        return authorUUID;
    }

    public void setAuthorUUID(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUserWhoAddUUID() {
        return userWhoAddUUID;
    }

    public void setUserWhoAddUUID(String userWhoAddUUID) {
        this.userWhoAddUUID = userWhoAddUUID;
    }

    public String getUserNewsUUID() {
        return userNewsUUID;
    }

    public void setUserNewsUUID(String userNewsUUID) {
        this.userNewsUUID = userNewsUUID;
    }
}
