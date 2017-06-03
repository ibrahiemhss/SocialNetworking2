package com.example.administrator.complettedmyspli.RecyclerPosts;

/**
 * Created by Administrator on 25/04/2017.
 */

public class Models {

    String textComent;
    String editcomment;
    boolean isLiked;
    String name;
    String subject;
    public   String ImageItems;
    String timeapost;
    String id_post;
String textUbdatePost;

    public String getTextUbdatePost() {
        return textUbdatePost;
    }

    public void setTextUbdatePost(String textUbdatePost) {
        this.textUbdatePost = textUbdatePost;
    }

    String editubdate;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getEditubdate() {
        return editubdate;
    }

    public void setEditubdate(String editubdate) {
        this.editubdate = editubdate;
    }

    String LikeCounts;

    public String getLikeCounts() {
        return LikeCounts;
    }

    public void setLikeCounts(String likeCounts) {
        LikeCounts = likeCounts;
    }

    public String getTextComent() {
        return textComent;
    }

    public void setTextComent(String textComent) {
        this.textComent = textComent;
    }

    public String getEditcomment() {
        return editcomment;
    }

    public void setEditcomment(String editcomment) {
        this.editcomment = editcomment;
    }

    public boolean getisLiked() {
        return isLiked;
    }

    public void setisLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    String id_user;

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getId_post() {
        return id_post;
    }






    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }

    int sync_status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageItems() {
        return ImageItems;
    }

    public void setImageItems(String imageItems) {
        ImageItems = imageItems;
    }

    public String getTimeapost() {
        return timeapost;
    }

    public void setTimeapost(String timeapost) {
        this.timeapost = timeapost;
    }
}