package nus.iss.Neko.Server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Post {
    private int post_id;
    private String email;
    private String username;
    private String title;
    private String caption;
    private String cat_label;
    private String cat_id;
    private int likes;
    private String date;
    private String imageUUID;
    private boolean liked = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCat_label() {
        return cat_label;
    }

    public void setCat_label(String cat_label) {
        this.cat_label = cat_label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    @Override
    public String toString() {
        return "Post [post_id=" + post_id + ", title=" + title + ", caption=" + caption + ", cat_id=" + cat_id
                + ", likes=" + likes + ", date=" + date + ", imageUUID=" + imageUUID + "]";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLiked() {
        return this.liked;
    }

    public void likedPost() {
        this.liked = true;
    }

    public void unlikedPost() {
        this.liked = false;
    }

    public static Post createPost(SqlRowSet result) {
        Post post = new Post();
        post.setTitle(result.getString("title"));
        post.setCaption(result.getString("caption"));
        post.setDate(result.getString("date"));
        post.setEmail(result.getString("email"));
        post.setImageUUID(result.getString("imageUUID"));
        post.setLikes(result.getInt("likes"));
        post.setPost_id(result.getInt("post_id"));
        post.setCat_id(result.getString("cat_id"));
        post.setCat_label(result.getString("cat_label"));
        post.setUsername(result.getString("username"));

        if (result.getString("likedPostsEmail") != null) {
            post.likedPost();
        }

        return post;
    }

}
