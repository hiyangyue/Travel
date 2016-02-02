package com.yueyang.travel.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.arrownock.exception.ArrownockException;
import com.arrownock.push.AnPush;
import com.arrownock.social.AnSocial;
import com.yueyang.travel.R;

/**
 * Created by Yang on 2015/12/18.
 */
public class IMppApp extends Application {

    public AnSocial anSocial;
    public AnPush anPush;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ActiveAndroid.initialize(this);
        try {
            anSocial = new AnSocial(this, getString(R.string.app_key));
            anPush = AnPush.getInstance(this);
            anPush.setSecureConnection(false);
            anPush.setAppKey(getString(R.string.app_key));
            anPush.enable();
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

}