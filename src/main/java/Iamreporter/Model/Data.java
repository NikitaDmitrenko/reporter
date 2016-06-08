package Iamreporter.Model;

public class Data {


    private String userName;
    private String publicUserUUID;
    private int type;
    private long date;
    private String newsUUID;
    private String newsTheme;
    private String text;
    private String userAvatarURL;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPublicUserUUID() {
        return publicUserUUID;
    }

    public void setPublicUserUUID(String publicUserUUID) {
        this.publicUserUUID = publicUserUUID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getNewsTheme() {
        return newsTheme;
    }

    public void setNewsTheme(String newsTheme) {
        this.newsTheme = newsTheme;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserAvatarURL() {
        return userAvatarURL;
    }

    public void setUserAvatarURL(String userAvatarURL) {
        this.userAvatarURL = userAvatarURL;
    }

    public static class DataBuilder{

        private String userName;
        private String publicUserUUID;
        private int type;
        private long date;
        private String newsUUID;
        private String newsTheme;
        private String text;
        private String userAvatarURL;


        public DataBuilder userName(String userName){
            this.userName = userName;
            return this;
        }

        public DataBuilder type(int type){
            this.type = type;
            return this;
        }

        public DataBuilder date(long date){
            this.date = date;
            return this;
        }

        public DataBuilder publicUserUUID(String publicUserUUID){
            this.publicUserUUID = publicUserUUID;
            return this;
        }

        public DataBuilder newsUUID(String newsUUID){
            this.newsUUID = newsUUID;
            return this;
        }

        public DataBuilder newsTheme(String newsTheme){
            this.newsTheme = newsTheme;
            return this;
        }

        public DataBuilder text(String text){
            this.text = text;
            return this;
        }

        public DataBuilder userAvatarURL(String userAvatarURL){
            this.userAvatarURL = userAvatarURL;
            return this;
        }

        public Data build(){
            return new Data(this);
        }
    }

    public Data(DataBuilder builder) {
        this.userAvatarURL = builder.userAvatarURL;
        this.type = builder.type;
        this.text = builder.text;
        this.newsTheme = builder.newsTheme;
        this.newsUUID = builder.newsUUID;
        this.publicUserUUID = builder.publicUserUUID;
        this.userName = builder.userName;
        this.date = builder.date;
        this.type = builder.type;
    }
}
