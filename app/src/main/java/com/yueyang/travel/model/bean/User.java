package com.yueyang.travel.model.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Yang on 2015/12/18.
 */
@Table(name = "User")
public class User extends Model implements Serializable {
    @Column(name = "userId")
    public String userId;
    @Column(name = "clientId")
    public String clientId;
    @Column(name = "userName")
    public String userName;
    @Column(name = "userPhotoUrl")
    public String userPhotoUrl;
    @Column(name = "nickname")
    public String nickname;

    public User(){
    }

    public User(String userId,String nickname,String userPhotoUrl){
        this.userId = userId;
        this.nickname = nickname;
        this.userPhotoUrl = userPhotoUrl;
    }

    public User(String userId, String userName, String userPhotoUrl, String nickname) {
        this.userId = userId;
        this.userName = userName;
        this.userPhotoUrl = userPhotoUrl;
        this.nickname = nickname;
    }

    public User(String userId, String userName, String userPhotoUrl, String nickname,String clientId) {
        this.userId = userId;
        this.userName = userName;
        this.userPhotoUrl = userPhotoUrl;
        this.nickname = nickname;
        this.clientId = clientId;
    }

    public User update() {
        User userExisit = new Select().from(User.class)
                .where("clientId = ? ", clientId).executeSingle();

        // 不存在
        if (userExisit == null) {
            userExisit = this;
        }else{
            if(userId!=null)
                userExisit.userId = userId;
            if(clientId!=null)
                userExisit.clientId = clientId;
            if(userName!=null)
                userExisit.userName = userName;
            if(userPhotoUrl!=null)
                userExisit.userPhotoUrl = userPhotoUrl;
        }

        userExisit.save();
        return userExisit;
    }

    public boolean same(){
        User userExisit = new Select().from(User.class)
                .where("clientId = ? ", clientId).executeSingle();
        if( (userExisit!=null) && userExisit.userName!=null && userExisit.userId!=null && (userExisit.userId.equals(userId)) && (userExisit.userName.equals(userName))){
            return (userExisit.userPhotoUrl==null && userPhotoUrl==null);
        }else{
            return false;
        }

    }

    public User getFromTable(){
        User userExisit = new Select().from(User.class).where("clientId = ? ", clientId).executeSingle();
        return userExisit;
    }

    public void parseJSON(JSONObject json){
        try {
            clientId = json.getString("clientId");
            userId = json.getString("id");
            userName = json.getString("username");
            nickname = json.getString("firstName");
            if(json.has("photo")){
                userPhotoUrl = json.getJSONObject("photo").getString("url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
