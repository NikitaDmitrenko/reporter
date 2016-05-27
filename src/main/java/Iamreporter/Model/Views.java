package Iamreporter.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "VIEWS")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Views {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "NEWS_UUID")
    private String newsUUID;

    @Column(name = "IP_ADRESS")
    private String ipAdress;

    @Column(name = "COMMENT_UUID")
    private String commentUUID;

    public Views() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewsUUID() {
        return newsUUID;
    }

    public void setNewsUUID(String newsUUID) {
        this.newsUUID = newsUUID;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }
}
