package com.yueyang.travel.model.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Yang on 2015/12/24.
 */
@Table(name = "Comment")
public class Comment extends Model {
    @Column(name = "commentId")
    public String commentId;

    @Column(name = "content")
    public String content;

    @Column(name = "createdAt")
    public long createdAt;

    @Column(name = "Owner")
    public User owner;

    @Column(name = "replyUser")
    public User replyUser;

    @Column(name = "Post")
    public Post post;



}
