package com.yueyang.travel.model.bean;

/**
 * Created by Yang on 2015/12/12.
 */
public class Desitination {

    private int id;
    private String cnName;
    private String enName;
    private String photoUrl;

    public Desitination(String cnName, String enName, String photoUrl) {
        this.cnName = cnName;
        this.enName = enName;
        this.photoUrl = photoUrl;
    }

    public Desitination(int id, String cnName, String enName, String photoUrl) {
        this.id = id;
        this.cnName = cnName;
        this.enName = enName;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
