package com.yueyang.travel.model.callBack;

import com.yueyang.travel.model.bean.Post;

/**
 * Created by Yang on 2016/1/3.
 */
public interface FeedCallBack {

//    void notifyFeed(String pictureDescribe,List<byte[]> picBytes);
//    void notifyFeed(JSONObject jsonObject);
    void notifyFeed(Post post);

}
