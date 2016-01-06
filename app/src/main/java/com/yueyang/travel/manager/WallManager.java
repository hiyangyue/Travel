package com.yueyang.travel.manager;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.arrownock.exception.ArrownockException;
import com.arrownock.social.AnSocial;
import com.arrownock.social.AnSocialMethod;
import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.application.IMppApp;
import com.yueyang.travel.model.bean.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Created by Yang on 2016/1/6.
 */
public class WallManager extends Observable {
    private String wallId;
    private Set<String> friendSet;
    private ArrayList<Post> postList;
    private AnSocial anSocial;
    private Handler handler;
    private Context ct;
    private final static int POST_LIMIT = 20;

    private int page = 0;
    private int totalPostCount = 0;

    private LikeCallback mLikeCallback ;

    public WallManager(Context ct, String wallId, Set<String> friendSet){
        this.ct = ct;
        this.wallId = wallId;
        this.friendSet = friendSet;
        friendSet.add( UserManager.getInstance(ct).getCurrentUser().userId);

        handler = new Handler();
        anSocial = ((IMppApp)ct.getApplicationContext()).anSocial;
    }


    public void init(final FetchPostsCallback callback){

        page = 0;
        postList = new ArrayList<Post>();
        fetchRemotePosts(++page,new FetchPostsCallback(){
            @Override
            public void onFailure(String errorMsg) {
                page--;
//                getLocalPosts(callback);
            }
            @Override
            public void onFinish(List<Post> data) {
                postList.addAll(data);
                if(callback!=null){
                    callback.onFinish(data);
                }
            }
        });
    }


    private void fetchRemotePosts(final int page,final FetchPostsCallback callback){

        new Thread(new Runnable(){
            @Override
            public void run() {
                String userIds ="";
                for(String friendUserId : friendSet){
                    userIds += friendUserId + ",";
                }
                userIds = userIds.substring(0, userIds.length()-1);

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("wall_id", wallId);
                params.put("user_id", userIds);
                params.put("page", page);
                params.put("limit", POST_LIMIT);
                params.put("sort", "-created_at");

                try {
                    anSocial.sendRequest("posts/query.json", AnSocialMethod.GET, params, new IAnSocialCallback(){
                        @Override
                        public void onFailure(final JSONObject arg0) {
                            try{
                                String message = arg0.getJSONObject("meta").getString("message");
                                Toast.makeText(ct, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable(){
                                @Override
                                public void run() {
                                    if(callback!=null){
                                        callback.onFailure(arg0.toString());
                                    }
                                }
                            });
                        }
                        @Override
                        public void onSuccess(JSONObject arg0) {

                            try {
                                totalPostCount = arg0.getJSONObject("meta").getInt("total");
                                final List<Post> posts = new ArrayList<Post>();
                                JSONArray postArray = arg0.getJSONObject("response").getJSONArray("posts");
                                for(int i =0;i<postArray.length();i++){
                                    JSONObject postJson = postArray.getJSONObject(i);
                                    Post post = ParseUtils.getPostFromUser(postJson);
                                    posts.add(post);
                                }
                                handler.post(new Runnable(){
                                    @Override
                                    public void run() {
                                        if(callback!=null){
                                            callback.onFinish(posts);
                                        }
                                    }
                                });


                            } catch (final JSONException e) {
                                e.printStackTrace();
                                handler.post(new Runnable(){
                                    @Override
                                    public void run() {
                                        if(callback!=null){
                                            callback.onFailure(e.getMessage());
                                        }
                                    }
                                });

                            }
                        }
                    });
                } catch (final ArrownockException e) {
                    e.printStackTrace();
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            if(callback!=null){
                                callback.onFailure(e.getMessage());
                            }
                        }
                    });
                }
            }
        }).start();

    }



    public void setOnLikeListener(LikeCallback callback){
        mLikeCallback = callback;
    }


    public interface LikeCallback{
        public void onFailure(Post post);
        public void onSuccess(Post post);
    }

    public interface FetchPostsCallback{
        public void onFailure(String errorMsg);
        public void onFinish(List<Post> data);
    }

    private void notifyPostUpdated(Post post){
        setChanged();
        notifyObservers(post);
    }
}
