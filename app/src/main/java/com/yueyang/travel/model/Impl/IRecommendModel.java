package com.yueyang.travel.model.Impl;

import com.yueyang.travel.model.callBack.RecommendTopicCallback;

/**
 * Created by Yang on 2015/12/17.
 */
public interface IRecommendModel {

    void loadTopic(RecommendTopicCallback callback);

}
