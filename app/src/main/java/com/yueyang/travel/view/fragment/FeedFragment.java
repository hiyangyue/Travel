package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.manager.IMManager;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.manager.UserManager;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/19.
 */
public class FeedFragment extends Fragment {

    @Bind(R.id.recommend_recycler)
    RecyclerView feedRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        createPost();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void createPost(){
        SocialManager.createPost(getContext(),
                UserManager.getInstance(getContext()).getCurrentUser().userId,
                "Hello World",
                null, new IAnSocialCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Log.e("create_post_success","..");
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        Log.e("create_post_error","..");
                    }
                });
    }
}















