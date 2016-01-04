package com.yueyang.travel.manager;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.arrownock.exception.ArrownockException;
import com.arrownock.social.AnSocial;
import com.arrownock.social.AnSocialFile;
import com.arrownock.social.AnSocialMethod;
import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.Utils.DBug;
import com.yueyang.travel.application.IMppApp;
import com.yueyang.travel.model.bean.Comment;
import com.yueyang.travel.model.bean.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void createPost(final Context context,  final String userId,
                                  final String content, List<byte[]> dataList, final IAnSocialCallback callback) {
        PhotoUploader mPhotoUploader = new PhotoUploader(context, userId, dataList, new PhotoUploader.PhotoUploadCallback() {
            @Override
            public void onFailure(String errorMsg) {
                DBug.e("createPost.uploadPhotos.onFailure", errorMsg);
            }

            @Override
            public void onSuccess(List<String> urlList) {
                DBug.e("createPost.uploadPhotos.onSuccess", "?");
                String photoUrls = "";
                for (String url : urlList) {
                    DBug.e("createPost.uploadPhotos.onSuccess", url);
                    photoUrls += url + ",";
                }

                AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("user_id", userId);
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

    public static void fetchRemoteComment(final Context context, String postId, final FetchCommentCallback callback) {
        AnSocial anSocial = ((IMppApp) context.getApplicationContext()).anSocial;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("object_type", "Post");
        params.put("object_id", postId);
        params.put("limit", 100);
        params.put("sort", "-created_at");

        try {
            anSocial.sendRequest("comments/query.json", AnSocialMethod.GET, params, new IAnSocialCallback() {
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
                        callback.onFailure();
                    }
                }

                @Override
                public void onSuccess(JSONObject arg0) {
                    try {
                        List<Comment> commentList = new ArrayList<Comment>();
                        JSONArray postArray = arg0.getJSONObject("response").getJSONArray("comments");
                        for (int i = 0; i < postArray.length(); i++) {
                            JSONObject json = postArray.getJSONObject(i);
                            Comment comment = new Comment();
                            comment.parseJSON(json);
                            comment.update();

                            commentList.add(0, comment);
                        }

                        if (callback != null) {
                            callback.onSuccess(commentList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFailure();
                        }
                    }
                }
            });
        } catch (ArrownockException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getLocalComment(final String postId, final FetchCommentCallback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Post post = new Post();
                post.postId = postId;
                post = post.getFromTable();
                final List<Comment> data = new Select().from(Comment.class).where("Post = ?", post.getId()).execute();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            if (data.size() > 0) {
                                callback.onSuccess(data);
                            } else {
                                callback.onFailure();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public interface FetchCommentCallback {
        public void onFailure();

        public void onSuccess(List<Comment> data);
    }

}
