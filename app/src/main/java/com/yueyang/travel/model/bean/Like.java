package com.yueyang.travel.model.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yang on 2015/12/19.
 */
@Table(name = "Like")
public class Like extends Model {
    @Column(name = "likeId")
    public String likeId;

    @Column(name = "Owner")
    public User owner;

    @Column(name = "Post")
    public Post post;

    public void parseJSON(JSONObject json){
        try {
            likeId = json.getString("id");

            User user = new User();
            user.parseJSON(json.getJSONObject("user"));
            user = user.update();
            owner = user;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean update(){
        Like exisit = new Select().from(Like.class).where("likeId = ?",likeId).executeSingle();

        // 不存在
        if (exisit == null) {
            save();
            return true;
        }else{
            return false;
        }


    }
}