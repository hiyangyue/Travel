package com.yueyang.travel.view;

import com.yueyang.travel.model.bean.Topic;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public interface IRecommendView {

    void loadPagers();

    void loadHeaders(String headers);

    void loadTopics(List<Topic> topics);
}
