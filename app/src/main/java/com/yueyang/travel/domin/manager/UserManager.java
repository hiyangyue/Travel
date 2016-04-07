package com.yueyang.travel.domin.manager;

import android.content.Context;
import android.util.Log;

import com.arrownock.exception.ArrownockException;
import com.arrownock.social.AnSocial;
import com.arrownock.social.AnSocialFile;
import com.arrownock.social.AnSocialMethod;
import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.application.IMppApp;
import com.yueyang.travel.model.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Yang on 2015/12/24.
 */
public class UserManager extends Observable {
    public static UserManager sUserManager;
    private AnSocial anSocial;
    private Context ct;
    private User currentUser;

    private UserManager(Context ct) {
        this.ct = ct;
        anSocial = ((IMppApp) ct.getApplicationContext()).anSocial;
    }

    public static UserManager getInstance(Context ct) {
        if (sUserManager == null) {
            sUserManager = new UserManager(ct);
        }
        return sUserManager;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void login(final String username, final String pwd, final IAnSocialCallback lsr) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", pwd);
        try {
            anSocial.sendRequest("users/auth.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(final JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void signUp(final String username, final String pwd,final String nickname,final IAnSocialCallback lsr) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", pwd);
        params.put("first_name", nickname);
        params.put("password_confirmation", pwd);
        params.put("enable_im", true);
        try {
            anSocial.sendRequest("users/create.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(final JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void updateNickName(final String username,final String nickName, final IAnSocialCallback lsr) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("first_name", nickName);
        params.put("user_id", currentUser.userId);
        try {
            anSocial.sendRequest("users/update.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(final JSONObject arg0) {

                    if (lsr != null) {
                        lsr.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        SpfHelper.getInstance(ct).clearUserInfo();
        IMManager.getInstance(ct).disconnect(true);
    }

    public void updateMyPhoto(byte[] data, final IAnSocialCallback lsr) {
        AnSocialFile AnFile = new AnSocialFile("photo", new ByteArrayInputStream(data));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("photo", AnFile);
        params.put("user_id", currentUser.userId);

        try {
            anSocial.sendRequest("users/update.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(final JSONObject arg0) {
                    if (lsr != null) {
                        lsr.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void followTargetUser(final String currentUserId, final String targetUserId, final FollowTargetUserCallback callback) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", currentUserId);
        params.put("target_user_id", targetUserId);
        try {
            anSocial.sendRequest("friends/add.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    callback.onError(arg0);
                }

                @Override
                public void onSuccess(final JSONObject arg0) {
                    callback.onSuccess();
                }
            });
        } catch (ArrownockException e) {

        }
    }

    public void fetchUserByUserId(final String userId,final FetchUserCallback callback){
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        try {
            anSocial.sendRequest("friends/list.json", AnSocialMethod.GET, params,
                    new IAnSocialCallback() {
                        @Override
                        public void onFailure(JSONObject response) {
                            callback.onError(response.toString());
                        }
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                User user = ParseUtils.getFriendListByUserId(response).get(0);
                                callback.onSuccess(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void fetchFollowers(String userId,final FetchUserListCallback callback){

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", userId);

        try {
            anSocial.sendRequest("friends/followers.json", AnSocialMethod.GET, params,
                    new IAnSocialCallback() {
                        @Override
                        public void onFailure(JSONObject response) {
                            callback.onError(response.toString());
                        }
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.e("follower_success",response.toString());
                            try {
                                List<User> userList =  ParseUtils.getFollowsByUserId(response);
                                callback.onSuccess(userList);
                            } catch (Exception e1) {

                            }
                        }
                    });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public void fetchFriendList(String userId, final FetchUserListCallback callback){

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        try {
            anSocial.sendRequest("friends/list.json", AnSocialMethod.GET, params,
                    new IAnSocialCallback() {
                        @Override
                        public void onFailure(JSONObject response) {
                            callback.onError(response.toString());
                        }
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                List<User> userList = ParseUtils.getFriendListByUserId(response);
                                callback.onSuccess(userList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }


    public void sendFriendRequest(final Context context,final String userId, final IAnSocialCallback cbk){
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", SpfHelper.getInstance(context).getMyUserId());
        params.put("target_user_id", userId);
        try {
            anSocial.sendRequest("friends/requests/send.json", AnSocialMethod.POST, params, new IAnSocialCallback(){
                @Override
                public void onFailure(JSONObject arg0) {
                    Log.e("error",arg0.toString());
                }
                @Override
                public void onSuccess(final JSONObject arg0) {
                    Log.e("successs",arg0.toString());
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public interface FetchUserListCallback{
        void onSuccess(List<User> userList);
        void onError(String errorMessage);
    }

    public interface FetchUserCallback{
        void onSuccess(User user);
        void onError(String errorMessage);
    }

    public interface FollowTargetUserCallback{
        void onSuccess();
        void onError(JSONObject jsonObject);
    }

}














