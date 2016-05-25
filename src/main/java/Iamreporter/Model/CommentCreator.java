package Iamreporter.Model;

public class CommentCreator {

    private String name;

    private String text;

    private Long date;

    private String to;

    private int likeCount;

    private int vievCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getVievCount() {
        return vievCount;
    }

    public void setVievCount(int vievCount) {
        this.vievCount = vievCount;
    }
}
