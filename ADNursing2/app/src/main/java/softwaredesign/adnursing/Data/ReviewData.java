package softwaredesign.adnursing.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huacan liang on 2016/7/10.
 */
public class ReviewData {
    private int review_id;
    private UserData user;
    private String content;
    private int floor;
    private String time;
    private int[] image;
    private String imageDir;
    private int postId;
    private String postImageDir;
    private String postTitle;

    private int[] defultImage = {};

    public ReviewData(UserData user, String content, String time, String imageDir) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.imageDir = imageDir;
    }

    public ReviewData(UserData user, String content, String time, String imageDir, int postId, String postImageDir, String postTitle) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.imageDir = imageDir;
        this.postId = postId;
        this.postImageDir = postImageDir;
        this.postTitle = postTitle;
    }

    public ReviewData(int review_id, UserData user, String content, String time, String imageDir, int postId, String postImageDir, String postTitle) {
        this.review_id = review_id;
        this.user = user;
        this.content = content;
        this.time = time;
        this.imageDir = imageDir;
        this.postId = postId;
        this.postImageDir = postImageDir;
        this.postTitle = postTitle;
    }

    public int getReview_id() {
        return review_id;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getTime()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String dateStr = time;
        try {
            date = sdf.parse(time);
            dateStr = (new SimpleDateFormat("MM-dd HH:mm")).format(date);
            System.out.println(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int[] getImage() {
        return image;
    }

    public void setImage(int[] image) {
        this.image = image;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostImageDir() {
        return postImageDir;
    }

    public int getPostId() {
        return postId;
    }

    public String getModifiedTime() {
        String diffStr = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format2 = new SimpleDateFormat("MM月dd日");
        Date current_date = new Date();
        Date post_date = null;
        try {
            post_date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = 0;
        if (post_date != null) {
            diff = current_date.getTime() - post_date.getTime();
        }
        long days = diff / (1000 * 60 * 60 * 24);

        if (days < 1) {
            days = diff / (1000 * 60 * 60);
            if (days < 1) {
                days = diff / (1000 * 60);
                diffStr = String.valueOf(days)+"分钟前";
            } else {
                diffStr = String.valueOf(days)+"小时前";
            }
        } else if (days < 3) {
            diffStr = String.valueOf(days) + "天前";
        } else {
            diffStr = format2.format(post_date);
        }

        return diffStr;
    }
}
