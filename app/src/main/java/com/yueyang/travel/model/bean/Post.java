package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/19.
 */
public class Post{
    public String postId;

    public String content;

    public String photoUrls;

    public long createdAt;

    public User user;

    public String wallId;

    public int likeCount;

    public int commentCount;

    public Post(String photoUrls,long createdAt,User user,int likeCount,String content,String postId){
        this.photoUrls = photoUrls;
        this.createdAt = createdAt;
        this.user = user;
        this.likeCount = likeCount;
        this.content = content;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getWallId() {
        return wallId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}