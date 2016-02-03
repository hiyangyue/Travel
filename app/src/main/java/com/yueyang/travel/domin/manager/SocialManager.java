package com.yueyang.travel.domin.manager;

import android.content.Context;
import android.widget.Toast;

import com.arrownock.exception.ArrownockException;
import com.arrownock.social.AnSocial;
import com.arrownock.social.AnSocialFile;
import com.arrownock.social.AnSocialMethod;
import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.DBug;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.application.IMppApp;
import com.yueyang.travel.model.bean.Comment;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.model.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yang on 2015/12/24.
 */
public class SocialManager {

    public static void createPhoto(final Context context, String userId, byte[] data, final IAnSocialCallback callback) {
        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        AnSocialFile AnFile = new AnSocialFile("photo", new ByteArrayInputStream(data));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("photo", AnFile);
        params.put("mime_type", "image/png");
        params.put("user_id", userId);

        try {
            anSocial.sendRequest("photos/create.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    try {
                        String message = arg0.getJSONObject("meta").getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (callback != null) {
                        callback.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(JSONObject arg0) {
                    if (callback != null) {
                        callback.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void createPost(final Context context, final String wallId, final String userId,
                                  final String content, List<byte[]> dataList, final IAnSocialCallback callback) {
        PhotoUploader mPhotoUploader = new PhotoUploader(context, userId, dataList, new PhotoUploader.PhotoUploadCallback() {
            @Override
            public void onFailure(String errorMsg) {
                DBug.e("createPost.uploadPhotos.onFailure", errorMsg);
            }

            @Override
            public void onSuccess(List<String> urlList) {
                String photoUrls = "";
                for (String url : urlList) {
                    photoUrls += url + ",";
                }

                AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("user_id", userId);
                params.put("wall_id", wallId);
                params.put("title", "_EMPTY_");

                if (content != null && content.length() > 0) {
                    params.put("content", content);
                }

                if (photoUrls.length() > 0) {
                    photoUrls = photoUrls.substring(0, photoUrls.length() - 1);
                    Map<String, String> custom_fields = new HashMap<String, String>();
                    custom_fields.put("photoUrls", photoUrls);
                    params.put("custom_fields", custom_fields);
                }

                try {
                    anSocial.sendRequest("posts/create.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                        @Override
                        public void onFailure(JSONObject arg0) {
                            try {
                                String message = arg0.getJSONObject("meta").getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            if (callback != null) {
                                callback.onFailure(arg0);
                            }
                        }

                        @Override
                        public void onSuccess(JSONObject arg0) {
                            if (callback != null) {
                                callback.onSuccess(arg0);
                        }
                        }
                    });
                } catch (ArrownockException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        mPhotoUploader.startUpload();
    }

    public static void fetchUserPost(final Context context,final String userId,final int page, final FetchPostsCallback callback){

        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<>();
        params.put("wall_id", context.getResources().getString(R.string.wall_id));
        params.put("user_id", userId);
        params.put("page", page);
        params.put("limit", 100);
        params.put("sort", "-created_at");

        try {
            anSocial.sendRequest("posts/query.json", AnSocialMethod.GET, params, new IAnSocialCallback(){
                @Override
                public void onFailure(final JSONObject arg0) {
                    callback.onFailure(arg0.toString());
                }
                @Override
                public void onSuccess(JSONObject arg0) {


                    try {
                        final List<Post> posts = new ArrayList<Post>();
                        JSONArray postArray = arg0.getJSONObject("response").getJSONArray("posts");
                        for(int i =0;i<postArray.length();i++){
                            JSONObject postJson = postArray.getJSONObject(i);
                            Post post = ParseUtils.getPostFromUser(postJson);
                            posts.add(post);
                        }

                        callback.onFinish(posts);
                    } catch (final JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (final ArrownockException e) {
            e.printStackTrace();
        }
    }

    public static void fetchPostByUserLike(final Context context,final String userId,final int page, final FetchPostsCallback callback){

        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<>();
        params.put("wall_id", context.getResources().getString(R.string.wall_id));
        params.put("like_user_id", userId);
        params.put("page", page);
        params.put("limit", 100);
        params.put("sort", "-created_at");

        try {
            anSocial.sendRequest("posts/query.json", AnSocialMethod.GET, params, new IAnSocialCallback(){
                @Override
                public void onFailure(final JSONObject arg0) {
                    callback.onFailure(arg0.toString());
                }
                @Override
                public void onSuccess(JSONObject arg0) {

                    try {
                        final List<Post> posts = new ArrayList<Post>();
                        JSONArray postArray = arg0.getJSONObject("response").getJSONArray("posts");
                        for(int i =0;i<postArray.length();i++){
                            JSONObject postJson = postArray.getJSONObject(i);
                            Post post = ParseUtils.getPostFromUser(postJson);
                            posts.add(post);
                        }

                        callback.onFinish(posts);
                    } catch (final JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (final ArrownockException e) {
            e.printStackTrace();
        }

    }

    public static void fetchAllPosts(final Context context,final Set<String> friendSet,final int page, final FetchPostsCallback callback){

        String userIds ="";

        for(String friendUserId : friendSet){
            userIds += friendUserId + ",";
        }
        userIds = userIds.substring(0, userIds.length()-1);

        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<>();
        params.put("wall_id", context.getResources().getString(R.string.wall_id));
        params.put("user_id", userIds);
        params.put("page", page);
        params.put("limit", 100);
        params.put("sort", "-created_at");

        try {
            anSocial.sendRequest("posts/query.json", AnSocialMethod.GET, params, new IAnSocialCallback(){
                @Override
                public void onFailure(final JSONObject arg0) {
                    callback.onFailure(arg0.toString());
                }
                @Override
                public void onSuccess(JSONObject arg0) {

                    try {
                        final List<Post> posts = new ArrayList<Post>();
                        JSONArray postArray = arg0.getJSONObject("response").getJSONArray("posts");
                        for(int i =0;i<postArray.length();i++){
                            JSONObject postJson = postArray.getJSONObject(i);
                            Post post = ParseUtils.getPostFromUser(postJson);
                            posts.add(post);
                        }

                        callback.onFinish(posts);
                    } catch (final JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (final ArrownockException e) {
            e.printStackTrace();
        }

    }

    public static void getLikeIdByUser(Context context, String postId, final FetchLikeByIdCallback callback){
        Map<String,Object> params = new HashMap<>();
        params.put("object_type","Post");
        params.put("object_id",postId);
        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        try {
            anSocial.sendRequest("likes/query.json", AnSocialMethod.GET, params,
                    new IAnSocialCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            callback.onSuccess(jsonObject);
                        }

                        @Override
                        public void onFailure(JSONObject jsonObject) {
                            callback.onFailure(jsonObject);
                        }
                    });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }


    public static void createLike(Context context,User user, final Post post, final LikeCallback callback){

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("object_type", "Post");
        params.put("object_id", post.postId);
        params.put("like", "true");
        params.put("user_id", user.userId);

        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;

        try {
            anSocial.sendRequest("likes/create.json", AnSocialMethod.POST, params, new IAnSocialCallback(){
                @Override
                public void onFailure(JSONObject jsonObject) {
                    callback.onFailure(jsonObject);
                }
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    callback.onSuccess(jsonObject);
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLike(Context context,final String likeId, final Post post, final LikeCallback callback){
        Map<String, Object> params = new HashMap<>();
        params.put("like_id", likeId);

        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;

        try {
            anSocial.sendRequest("likes/delete.json", AnSocialMethod.POST, params, new IAnSocialCallback(){
                @Override
                public void onFailure(JSONObject jsonObject) {
                    callback.onSuccess(jsonObject);
                }
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    callback.onSuccess(jsonObject);
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public static void createComment(final Context context, String postId, String replyUserId, String userId,
                                     String content, final IAnSocialCallback callback) {
        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", content);
        params.put("object_type", "Post");
        params.put("object_id", postId);
        params.put("user_id", userId);
        if (replyUserId != null && replyUserId.length() > 0) {
            params.put("reply_user_id", replyUserId);
        }

        try {
            anSocial.sendRequest("comments/create.json", AnSocialMethod.POST, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    try {
                        String message = arg0.getJSONObject("meta").getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (callback != null) {
                        callback.onFailure(arg0);
                    }
                }

                @Override
                public void onSuccess(JSONObject arg0) {
                    if (callback != null) {
                        callback.onSuccess(arg0);
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public static void fetchCommentByPostId(final Context context, String postId, final FetchCommentCallback callback) {
        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<>();
        params.put("object_type", "Post");
        params.put("object_id", postId);

        try {
            anSocial.sendRequest("comments/query.json", AnSocialMethod.GET, params, new IAnSocialCallback() {
                @Override
                public void onFailure(JSONObject arg0) {
                    callback.onFailure();
                }

                @Override
                public void onSuccess(JSONObject arg0) {
                    try {
                        List<Comment> commentList = new ArrayList<>();
                        JSONArray postArray = arg0.getJSONObject("response").getJSONArray("comments");
                        for (int i = 0 ; i < postArray.length() ; i ++){
                            Comment comment = ParseUtils.getCommentByPostId(postArray.getJSONObject(i));
                            commentList.add(comment);
                        }
                        callback.onSuccess(commentList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }


    public  interface FetchLikeByIdCallback{
        void onFailure(JSONObject jsonObject);
        void onSuccess(JSONObject jsonObject);
    }

    public interface FetchPostsCallback{
        void onFailure(String errorMsg);
        void onFinish(List<Post> data);
    }

    public interface FetchCommentCallback {
        void onFailure();
        void onSuccess(List<Comment> data);
    }

    public interface LikeCallback{
        void onFailure(JSONObject object);
        void onSuccess(JSONObject object);
    }

}
