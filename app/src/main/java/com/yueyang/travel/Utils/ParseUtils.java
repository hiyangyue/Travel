package com.yueyang.travel.Utils;

import android.util.Log;

import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.model.bean.Comment;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.model.bean.Topic;
import com.yueyang.travel.model.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2015/12/10.
 */
public class ParseUtils {


    public static void getPopularNotes(String popularUrl,int position) throws JSONException {

        JSONObject jsonObject = new JSONObject(popularUrl);
        JSONArray array = jsonObject.getJSONArray("data");
        JSONObject object = array.getJSONObject(position);
        String imgUrl = object.getString("photo");
        String title = object.getString("title");
        String noteUrl = object.getString("view_url");
        int like = object.getInt("likes");
        int views = object.getInt("views");

    }

    public static Topic getTopics(String bannerUrl,int position) throws JSONException {

        JSONObject jsonObject = new JSONObject(bannerUrl);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = dataObject.getJSONArray("subject");
        JSONObject object = jsonArray.getJSONObject(position);
        String imgUrl = object.getString("photo");
        String noteUrl = object.getString("url");

        return new Topic(imgUrl,noteUrl);
    }

    public static Desitination getDesitination(String desitinationUrl,int position) throws JSONException{

        JSONObject jsonObject = new JSONObject(desitinationUrl);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        JSONArray countryArray = jsonObject1.getJSONArray("hot_country");
        JSONObject object = countryArray.getJSONObject(position);
        String cnName = object.getString("cnname");
        String enName = object.getString("enname");
        String photo = object.getString("photo");
        int id = object.getInt("id");

        return new Desitination(id,cnName,enName,photo);
    }

    public static String getImgUrl(String url) throws JSONException{

        JSONObject object = new JSONObject(url);
        JSONObject dataObject = object.getJSONObject("data");
        JSONArray array = dataObject.getJSONArray("photos");
        return array.getString(0);
    }

    public static City getCity(String cityUrl,int position) throws JSONException{

        JSONObject object = new JSONObject(cityUrl);
        JSONArray jsonArray = object.getJSONArray("data");
        JSONObject dataObj = jsonArray.getJSONObject(position);
        int id = dataObj.getInt("id");
        String cityName = dataObj.getString("catename");
        String enCityName = dataObj.getString("catename_en");
        String photoUrl = dataObj.getString("photo");
        String cityBeenStr = dataObj.getString("beenstr");
        String cityRepresent = dataObj.getString("representative");

        return new City(id,cityName,enCityName,photoUrl,cityBeenStr,cityRepresent);
    }

    public static User getUserFromRegister(JSONObject jsonObject) throws JSONException{
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONObject userObj = responseObj.getJSONObject("user");
        String userId = userObj.getString("id");
        String nickName = userObj.getString("firstName");
        String userName = userObj.getString("username");
        String avatarUrl = "";
        if (userObj.has("photo")){
            JSONObject photoObj = userObj.getJSONObject("photo");
            avatarUrl = photoObj.getString("url");
        }
        return new User(userId,userName,avatarUrl,nickName);
    }

    public static List<User> getFriendListByUserId(JSONObject jsonObject) throws JSONException{
        List<User> userList = new ArrayList<>();
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONArray friendArray = responseObj.getJSONArray("friends");
        for (int i = 0; i < friendArray.length() ; i ++){
            JSONObject friendObj = friendArray.getJSONObject(i);
            String userId = friendObj.getString("id");
            String nickName = friendObj.getString("firstName");
            String avatarUrl = null;
            if (friendObj.has("photo")){
                JSONObject photoObj = friendObj.getJSONObject("photo");
                if (photoObj.has("url")){
                    avatarUrl = photoObj.getString("url");
                }
            }
            userList.add(new User(userId,nickName,avatarUrl));
        }

        return userList;
    }

    public static List<User> getFollowsByUserId(JSONObject jsonObject) throws JSONException{
        List<User> userList = new ArrayList<>();
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONArray followersArray = responseObj.getJSONArray("followers");
        for (int i = 0 ; i < followersArray.length() ; i ++){
            JSONObject userObj = followersArray.getJSONObject(i);
            String userId = userObj.getString("id");
            String nickName = userObj.getString("firstName");
            String avatarUrl = null;
            if (userObj.has("photo")){
                JSONObject photoObj = userObj.getJSONObject("photo");
                if (photoObj.has("url")){
                    avatarUrl = photoObj.getString("url");
                }
            }

            userList.add(new User(userId,nickName,avatarUrl));
        }

        return userList;
    }

    public static Post getPost(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONObject postObj = responseObj.getJSONObject("post");
        String content = postObj.getString("content");
        int likeCount = postObj.getInt("likeCount");
        String postId = postObj.getString("id");

        //时间转换
        String createAt = postObj.getString("created_at");
        String time = DateUtils.getStandardTime(createAt);
        Log.e("time",time);
        String calucateTime = "";
        try {
            calucateTime = DateUtils.getDateString(DateUtils.calucateTime(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject customObj = postObj.getJSONObject("customFields");
        String photoUrl = customObj.getString("photoUrls");

        JSONObject userObj = postObj.getJSONObject("user");
        String userId = userObj.getString("id");
        String nickName = userObj.getString("firstName");
        String avatarUrl = "";
        if (userObj.has("photo")){
            avatarUrl = userObj.getJSONObject("photo").getString("url");
        }
        User user = new User(userId,nickName,avatarUrl);

        return new Post(photoUrl,calucateTime,user,likeCount,content,postId);
    }

    public static Post getPostFromUser(JSONObject jsonObject) throws JSONException {

        String content = jsonObject.getString("content");
        String id = jsonObject.getString("id");
        int likeCount = jsonObject.getInt("likeCount");
        String createAt = jsonObject.getString("created_at");

        String time = DateUtils.getStandardTime(createAt);
        String calucateTime = "";
        try {
            calucateTime = DateUtils.getDateString(DateUtils.calucateTime(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        JSONObject customFields = jsonObject.getJSONObject("customFields");
        String photoUrl = customFields.getString("photoUrls");
        JSONObject userObject = jsonObject.getJSONObject("user");
        String userId = userObject.getString("id");
        String username = userObject.getString("username");
        String nickName = userObject.getString("firstName");
        String headerImgUrl = null;
        if (userObject.has("photo")){
            JSONObject photoObj = userObject.getJSONObject("photo");
            headerImgUrl = photoObj.getString("url");
        }
        User user = new User(userId,username,headerImgUrl,nickName);
        return new Post(photoUrl,calucateTime,user,likeCount,content,id);
    }

    public static String getLikeId(JSONObject jsonObject) throws JSONException {
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONArray likesArray = responseObj.getJSONArray("likes");
        JSONObject object = likesArray.getJSONObject(0);
        String likeId = null;
        if (object.has("id")){
            likeId = object.getString("id");
        }
        return likeId;
    }

    public static boolean isHasLike(JSONObject jsonObject) throws JSONException{
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONArray likesArray = responseObj.getJSONArray("likes");
        return likesArray.isNull(0);
    }

    public static Comment getComment(JSONObject jsonObject) throws JSONException{
        JSONObject responseObj = jsonObject.getJSONObject("response");
        JSONObject commentObj = responseObj.getJSONObject("comment");
        String commentId = commentObj.getString("id");
        String content = commentObj.getString("content");

        JSONObject userObj = commentObj.getJSONObject("user");
        String userId = userObj.getString("id");
        String nickName = userObj.getString("firstName");
        String avatarUrl = "";
        if (userObj.has("photo")){
            JSONObject photoObj = userObj.getJSONObject("photo");
            avatarUrl = photoObj.getString("url");
        }

        User commentUser = new User(userId,nickName,avatarUrl);
        return new Comment(commentId,content,commentUser);
    }

    public static Comment getCommentByPostId(JSONObject jsonObject) throws JSONException{
        String commendId = jsonObject.getString("id");
        String content = jsonObject.getString("content");

        JSONObject userObj = jsonObject.getJSONObject("user");
        String userId = userObj.getString("id");
        String nickName = userObj.getString("firstName");
        String avatarUrl = "";
        if (userObj.has("photo")){
            JSONObject photoObj = userObj.getJSONObject("photo");
            avatarUrl = photoObj.getString("url");
        }

        User commentUser = new User(userId,nickName,avatarUrl);
        return new Comment(commendId,content,commentUser);
    }

}
