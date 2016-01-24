package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/16.
 */
public class CityDetail {

    private int id;
    private String photoUrl;
    private String title;
    private String description;

    public CityDetail(int id, String photoUrl, String title, String description) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
