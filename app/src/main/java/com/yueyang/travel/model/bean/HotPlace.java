package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2016/1/19.
 */
public class HotPlace {

    private String authorPhotoUrl;
    private String title;
    private String placeUrl;
    private String placeBgUrl;

    public HotPlace(String authorPhotoUrl, String title, String placeUrl, String placeBgUrl) {
        this.authorPhotoUrl = authorPhotoUrl;
        this.title = title;
        this.placeUrl = placeUrl;
        this.placeBgUrl = placeBgUrl;
    }

    public String getAuthorPhotoUrl() {
        return authorPhotoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public String getPlaceBgUrl() {
        return placeBgUrl;
    }
}
