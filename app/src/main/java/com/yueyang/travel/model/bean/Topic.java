package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/10.
 */
public class Topic {

    private String imgUrl;
    private String noteUrl;

    public Topic(String imgUrl, String noteUrl) {
        this.imgUrl = imgUrl;
        this.noteUrl = noteUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNoteUrl() {
        return noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }
}
