package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
@Entity
@Table(name = "COMMENT")
public class Comment {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "COMMENT_UUID")
    private String commentUUID;

    @Column(name = "USER_NEWS_UUID")
    private String userNewsUUID;

    @Column(name = "AUTHOR_UUID")
    private String authorUUID;

    @Column(name = "TO_USER_UUID")
    private String toUserUUID;

    @Column(name = "COMMENT_DATE")
    private Long date;

    public Comment() {
    }

    public Long getId() {
        return id;
    }


    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorUUID() {
        return authorUUID;
    }

    public void setAuthorUUID(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    public String getToUserUUID() {
        return toUserUUID;
    }

    public void setToUserUUID(String toUserUUID) {
        this.toUserUUID = toUserUUID;
    }

    public String getCommentUUID() {
        return commentUUID;
    }

    public void setCommentUUID(String commentUUID) {
        this.commentUUID = commentUUID;
    }

    public String getUserNewsUUID() {
        return userNewsUUID;
    }

    public void setUserNewsUUID(String userNewsUUID) {
        this.userNewsUUID = userNewsUUID;
    }
}
