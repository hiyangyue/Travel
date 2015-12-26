package com.yueyang.travel.model.callBack;

import com.yueyang.travel.model.bean.Topic;

import java.util.List;

/**
 * Created by Yang on 2015/12/10.
 */
public interface RecommendTopicCallback {

    void loadTopicSuccess(List<Topic> topics);

    void loadTopicError();

}
