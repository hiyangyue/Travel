package com.yueyang.travel.model.Impl;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.Utils.TravelApi;
import com.yueyang.travel.model.bean.Topic;
import com.yueyang.travel.model.callBack.RecommendTopicCallback;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Yang on 2015/12/17.
 */
public class IRecommendImpl implements IRecommendModel {

    @Override
    public void loadTopic(final RecommendTopicCallback callback) {
        TravelApi.RestClient.getRecommendTopics(TravelApi.POPULAR_TOPIC, null, new BaseJsonHttpResponseHandler<List<Topic>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<Topic> response) {
                callback.loadTopicSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<Topic> errorResponse) {
                callback.loadTopicError();
            }

            @Override
            protected List<Topic> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                List<Topic> topicList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Topic topic = ParseUtils.getTopics(rawJsonData, i);
                    topicList.add(topic);
                }

                return topicList;
            }
        });

    }
}
