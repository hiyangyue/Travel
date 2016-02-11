package com.yueyang.travel.model.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yang on 2015/12/18.
 */
public class User{
    public String userId;
    public String clientId;
    public String userName;
    public String userPhotoUrl;
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
