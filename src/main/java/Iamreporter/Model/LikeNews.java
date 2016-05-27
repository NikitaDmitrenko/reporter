package Iamreporter.Model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "LIKE_NEWS")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class LikeNews {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NEWS_UUID")
    private String newsUUID;

    @Column(name = "USER_LIKE")
    private boolean like;

    @Column(name = "USER_UUID")
    private String userUUID;

    @Column(name = "COMMENT_UUID")
    private String commentUUID;

    public LikeNews() {
    }

    public String getCommentUUID() {
        return commentUUID;
    }

    public void setCommentUUID(String commentUUID) {
        this.commentUUID = commentUUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsUUID() {
        return newsUUID;
    }

    public void setNewsUUID(String newsUUID) {
        this.newsUUID = newsUUID;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
}
