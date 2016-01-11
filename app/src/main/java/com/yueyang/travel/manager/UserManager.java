package com.yueyang.travel.manager;

import android.content.Context;
import android.os.Handler;

import com.activeandroid.query.Select;
import com.arrownock.exception.ArrownockException;
import com.arrownock.social.AnSocial;
import com.arrownock.social.AnSocialFile;
import com.arrownock.social.AnSocialMethod;
import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.Utils.DBug;
import com.yueyang.travel.application.IMppApp;
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
    private Handler handler;
    private Context ct;
    private User currentUser;

    public enum UpdateType {
        Friend, User
    }

    private UserManager(Context ct) {
        this.ct = ct;
        anSocial = ((IMppApp) ct.getApplicationContext()).anSocial;
        handler = new Handler();
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


    public void fetchUserDataByClientId(final String clientId) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("clientId", clientId);
        try {
            anSocial.sendRequest("users/query.json", AnSocialMethod.GET, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                }

                @Override
                public void onSuccess(final JSONObject arg0) {
                    try {
                        DBug.e("fetchUserDataByClientId", clientId + "," + arg0.toString());
                        JSONObject json = arg0.getJSONObject("response").getJSONArray("users").getJSONObject(0);
                        User user = new User();
                        user.parseJSON(json);
                        saveUser(user);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
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
                    try {
                        JSONObject userJson = arg0.getJSONObject("response").getJSONObject("user");
                        User user = new User(userJson);
                        saveUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        JSONObject userJson = arg0.getJSONObject("response").getJSONObject("user");
                        User user = new User(userJson);
                        saveUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

                    try {
                        JSONObject userJson = arg0.getJSONObject("response").getJSONObject("user");
                        User user = new User(userJson);
                        saveUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        JSONObject userJson = arg0.getJSONObject("response").getJSONObject("user");
                        User user = new User(userJson);
                        saveUser(user);

                        currentUser = currentUser.getFromTable();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (lsr != null) {
                        lsr.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(User user) {
        if ((user.userId == null || user.userName == null) && user.clientId != null) {
            fetchUserDataByClientId(user.clientId);
        }
        if (!user.same()) {
            user.update();

            setChanged();
            notifyObservers(UpdateType.User);
        }
    }

    public User getUserByClientId(String clientId) {
        return new Select().from(User.class).where("clientId = ?", clientId).executeSingle();
    }

    public User getUserByUserId(String userId) {
        return new Select().from(User.class).where("userId = ?", userId).executeSingle();
    }

    public void getMyLocalFriends(final FetchUserCallback callback) {
        if (currentUser == null) {
            throw new IllegalArgumentException("currentUser is null");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<User> data = currentUser.friends();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFinish(data);
                        }
                    }
                });
            }
        }).start();
    }

    public interface FetchUserCallback {
        public void onFinish(List<User> data);
    }

}