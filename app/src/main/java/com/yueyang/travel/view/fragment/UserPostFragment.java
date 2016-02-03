package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.manager.SocialManager;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.view.adapter.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/14.
 */
public class UserPostFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private String userId;
    private List<Post> postList;
    private FeedAdapter adapter;

    public static UserPostFragment getInstance(String userId){
        UserPostFragment fragment = new UserPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_ID,userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_layout, container, false);
        ButterKnife.bind(this,view);
        getUserId();
        initRecyclerView();
        getUserPost(UserManager.getInstance(getActivity()).getCurrentUser().userId);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getUserId(){
        userId = getArguments().getString(Constants.USER_ID);
    }

    private void initRecyclerView(){

        postList = new ArrayList<>();
        adapter = new FeedAdapter(getActivity(),postList);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void getUserPost(String userId){
        SocialManager.fetchUserPost(getActivity(), userId, 1, new SocialManager.FetchPostsCallback() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onFinish(List<Post> data) {
                postList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }





}






