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
    private boolean status;

    @Column(name = "USER_UUID")
    private String userUUID;

    public LikeNews() {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean like) {
        this.status = like;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public LikeNews changeStatus(){
        if(this.status){
            this.status = false;
        }else{
            this.status = true;
        }
        return this;
    }

    public int getDigitalStatus(){
        int status;
        if(this.status){
            status = 1;
        }else{
            status = 0;
        }
        return status;
    }
}
