package Iamreporter.Model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "MEDIA_FILE")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class MediaFile {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "PHOTO_URL")
    private String photoURL;

    @Column(name = "MEDIA_FILE")
    private String uuid;

    @Column(name = "NEWS_UUID")
    private String newsUUID;


    @Column(name = "VIDEO_URL")
    private String videoURL;

    @Column(name = "SMALL_PHOTO_URL")
    private String smallPhotoURL;

    @Column(name = "DATE")
    private long date;


    public MediaFile() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getSmallPhotoURL() {
        return smallPhotoURL;
    }

    public void setSmallPhotoURL(String smallPhotoURL) {
        this.smallPhotoURL = smallPhotoURL;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNewsUUID() {
        return newsUUID;
    }

    public void setNewsUUID(String newsUUID) {
        this.newsUUID = newsUUID;
    }
}
