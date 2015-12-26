package com.yueyang.travel.presenter;

import android.util.Log;

import com.yueyang.travel.model.Impl.IRecommendImpl;
import com.yueyang.travel.model.Impl.IRecommendModel;
import com.yueyang.travel.model.bean.Topic;
import com.yueyang.travel.model.callBack.RecommendTopicCallback;
import com.yueyang.travel.view.IRecommendView;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public class RecommendPresenter {

    private IRecommendModel recommendModel;
    private IRecommendView recommendView;

    public RecommendPresenter(IRecommendView recommendView) {
        this.recommendView = recommendView;
        recommendModel = new IRecommendImpl();
    }

    public void loadTopic(){
        recommendModel.loadTopic(new RecommendTopicCallback() {
            @Override
            public void loadTopicSuccess(List<Topic> topics) {
                recommendView.loadTopics(topics);
            }

            @Override
            public void loadTopicError() {
                Log.e("loadTopic","error");
            }
        });
    }
}
