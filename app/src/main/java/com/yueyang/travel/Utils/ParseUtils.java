package com.yueyang.travel.Utils;

import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.model.bean.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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













}
