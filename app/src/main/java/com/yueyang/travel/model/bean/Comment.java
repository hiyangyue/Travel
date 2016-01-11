package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/24.
 */
public class Comment{

    public String commentId;
    public String content;
    public User commentUser;

    public Comment(String commentId, String content, User commentUser) {
        this.commentId = commentId;
        this.content = content;
        this.commentUser = commentUser;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public User getCommentUser() {
        return commentUser;
    }
}
