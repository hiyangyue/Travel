package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/15.
 */
public class City {

    private int cityId;
    private String cityName;
    private String enCityName;
    private String cityImgUrl;
    private String cityBeenStr;
    private String cityRepresent;

    public City(int cityId,String cityName, String enCityName, String cityImgUrl,String cityBeenStr,String cityRepresent) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.enCityName = enCityName;
        this.cityImgUrl = cityImgUrl;
        this.cityBeenStr = cityBeenStr;
        this.cityRepresent = cityRepresent;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getEnCityName() {
        return enCityName;
    }


    public String getCityImgUrl() {
        return cityImgUrl;
    }

    public String getCityBeenStr(){
        return cityBeenStr;
    }

    public String getCityRepresent(){
        return cityRepresent;
    }


}
