package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.model.bean.Topic;
import com.yueyang.travel.presenter.RecommendPresenter;
import com.yueyang.travel.view.IRecommendView;
import com.yueyang.travel.view.adapter.RecommendAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/10.
 */
public class RecommendFragment extends Fragment implements IRecommendView{

    @Bind(R.id.recommend_recycler)
    RecyclerView recommendRecycler;
    private RecommendAdapter adapter;
    private List<Object> mItems;
    private RecommendPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initPresenter();
        return view;
    }

    private void initRecyclerView() {

        mItems = new ArrayList<>();
        mItems.add("1");
        mItems.add("2");
        mItems.add("2");
        mItems.add("2");

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 | position == 1 | position == 2 | position == 4
                        | RecommendAdapter.isHeader(position)) {
                    return mLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recommendRecycler.setLayoutManager(mLayoutManager);
        //设置Item的大小一样
        recommendRecycler.setHasFixedSize(true);
        //添加默认的动画
        recommendRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecommendAdapter(getActivity(), mItems);
        recommendRecycler.setAdapter(adapter);
    }

    private void initPresenter(){
        presenter = new RecommendPresenter(this);
        presenter.loadTopic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void loadPagers() {

    }

    @Override
    public void loadHeaders(String headers) {

    }

    @Override
    public void loadTopics(List<Topic> topics) {
        mItems.addAll(topics);
        adapter.notifyDataSetChanged();
    }
}














