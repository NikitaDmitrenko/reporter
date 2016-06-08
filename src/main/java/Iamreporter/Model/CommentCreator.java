package Iamreporter.Model;

public class CommentCreator {

    private String name;

    private String text;

    private Long date;

    private String toCommentUUID;

    private String toAuthorUUID;

    public String getToAuthorUUID() {
        return toAuthorUUID;
    }

    public void setToAuthorUUID(String toAuthorUUID) {
        this.toAuthorUUID = toAuthorUUID;
    }

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

    public String getToCommentUUID() {
        return toCommentUUID;
    }

    public void setToCommentUUID(String toCommentUUID) {
        this.toCommentUUID = toCommentUUID;
    }
}
