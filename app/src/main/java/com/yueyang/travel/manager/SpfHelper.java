package com.yueyang.travel.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.yueyang.travel.R;

/**
 * Created by Yang on 2015/12/24.
 */
public class SpfHelper {

    private static SpfHelper sSpfHelper;
    public SharedPreferences Account;
    public SharedPreferences.Editor editor;

    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_PWD = "pwd";
    private static final String KEY_USER_USERID = "userid";
    private static final String KEY_USER_CLIENTID = "clientid";
    private static final String KEY_USER_NICKNAME = "nickname";

    private SpfHelper(Context ct) {
        Account = ct.getSharedPreferences(ct.getString(R.string.app_name), 0);
        editor = Account.edit();
    }

    public static SpfHelper getInstance(Context ct) {
        if (sSpfHelper == null) {
            sSpfHelper = new SpfHelper(ct);
        }
        return sSpfHelper;
    }

    public void clearUserInfo() {
        editor.putString(KEY_USER_USERNAME, "").commit();
        editor.putString(KEY_USER_PWD, "").commit();
        editor.putString(KEY_USER_USERID, "").commit();
        editor.putString(KEY_USER_CLIENTID, "").commit();
    }

    public void saveUserInfo(String username, String pwd,String nickname, String userId, String clientId) {
        editor.putString(KEY_USER_USERNAME, username).commit();
        editor.putString(KEY_USER_PWD, pwd).commit();
        editor.putString(KEY_USER_USERID, userId).commit();
        editor.putString(KEY_USER_NICKNAME, nickname).commit();
        editor.putString(KEY_USER_CLIENTID, clientId).commit();
    }

    public void updateNickname(String nickname){
        editor.putString(KEY_USER_NICKNAME, nickname).commit();
    }


    public String getMyUserId() {
        return Account.getString(KEY_USER_USERID, "");
    }

    public String getMyClientId() {
        return Account.getString(KEY_USER_CLIENTID, "");
    }

    public boolean hasSignIn() {
        return getMyUsername() != null && getMyUsername().length() > 0 && getMyPwd() != null && getMyPwd().length() > 0;
    }

    public String getMyUsername() {
        return Account.getString(KEY_USER_USERNAME, "");
    }

    public String getMyPwd() {
        return Account.getString(KEY_USER_PWD, "");
    }

    public String getMyNickname(){
        return Account.getString(KEY_USER_NICKNAME,"");
    }

}
