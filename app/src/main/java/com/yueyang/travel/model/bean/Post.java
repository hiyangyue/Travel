package com.yueyang.travel.model.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Yang on 2015/12/19.
 */
@Table(name = "Post")
public class Post extends Model {
    @Column(name = "postId")
    public String postId;

    @Column(name = "content")
    public String content;

    @Column(name = "photoUrls")
    public String photoUrls;

    @Column(name = "createdAt")
    public long createdAt;

    @Column(name = "Owner")
    public User owner;

    @Column(name = "wallId")
    public String wallId;

    @Column(name = "likeCount")
    public int likeCount;

    @Column(name = "commentCount")
    public int commentCount;

    public Post(){

    }

    public Post(String photoUrls, long createdAt, User owner,int likeCount, String content, String postId) {
        this.photoUrls = photoUrls;
        this.createdAt = createdAt;
        this.owner = owner;
        this.likeCount = likeCount;
        this.content = content;
        this.postId = postId;
    }

    public Like myLike(User user) {
        user = user.getFromTable();
        return new Select().from(Like.class)
                .where("Post = \"" + getFromTable().getId() + "\" and Owner = \"" + user.getId() + "\"")
                .executeSingle();
    }

    public List<Like> likes() {
        return new Select().from(Like.class).where("Post = ?", getFromTable().getId()).execute();
    }

    public void deleteAllLikes() {
        new Delete().from(Like.class).where("Post = ?", getFromTable().getId()).execute();
    }

    public void parseJSON(JSONObject json) {
        try {
            if (json.has("parentId")) {
                wallId = json.getString("parentId");
            }
            postId = json.getString("id");
            likeCount = json.getInt("likeCount");
            commentCount = json.getInt("commentCount");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            c.setTime(sdf.parse(json.getString("created_at")));
            createdAt = c.getTimeInMillis();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            content = json.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            User user = new User();
            user.parseJSON(json.getJSONObject("user"));
            user = user.update();
            owner = user;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (json.has("customFields")) {
                if (json.getJSONObject("customFields").has("photoUrls")) {
                    photoUrls = json.getJSONObject("customFields").getString("photoUrls");
                }
                if (json.getJSONObject("customFields").has("circle_id")) {
                    wallId = json.getJSONObject("customFields").getString("circle_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Post getFromTable() {
        Post exisit = new Select().from(Post.class).where("postId = ?", postId).executeSingle();
        return exisit;
    }

    public void update() {
        Post exisit = new Select().from(Post.class).where("postId = ?", postId).executeSingle();

        // 不存在
        if (exisit == null) {
            save();
        } else {
            exisit.likeCount = likeCount;
            exisit.commentCount = commentCount;
            exisit.save();
        }
    }
}