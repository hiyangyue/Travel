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

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWallId() {
        return wallId;
    }

    public void setWallId(String wallId) {
        this.wallId = wallId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}