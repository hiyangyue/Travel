package com.yueyang.travel.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.FileUtils;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.model.bean.User;
import com.yueyang.travel.view.activity.PhotoActivity;
import com.yueyang.travel.view.adapter.FeedAdapter;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/19.
 */
public class FeedFragment extends Fragment {

    @Bind(R.id.feed_recycler)
    RecyclerView feedRecycler;
    @Bind(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    private FloatingActionButton fab;

    private String mCurrentPhotoPath;
    private FeedAdapter feedAdapter;
    private List<Post> postList;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        setUpFab();
        setUpRecyclerView();
        initData(getContext());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_TAKE_PHOTO:
                    Intent intent = new Intent(getActivity(), PhotoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PHOTO_PATH, mCurrentPhotoPath);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Constants.REQUEST_GET_POST);
                    break;
                case Constants.REQUEST_GET_POST:
                    try {
                        Post post = ParseUtils.getPost(data.getExtras().getString(Constants.RESULT_POST));
                        postList.add(post);
                        feedAdapter.notifyItemInserted(0);
                        feedRecycler.scrollToPosition(0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setUpFab() {
        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    try {
                        File file = FileUtils.createImgFile();
                        mCurrentPhotoPath = file.getAbsolutePath();
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(takePhotoIntent, Constants.REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setUpRecyclerView() {
        postList = new ArrayList<>();
        feedAdapter = new FeedAdapter(getContext(),postList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecycler.setLayoutManager(layoutManager);
        feedRecycler.setAdapter(feedAdapter);
    }

    private void initData(final Context context){
        UserManager.getInstance(context).getMyLocalFriends(new UserManager.FetchUserCallback() {
            @Override
            public void onFinish(List<User> data) {
                Set<String> friendSet = new HashSet<>();
                for(User friend : data){
                    friendSet.add(friend.userId);
                }

                SocialManager.fetchAllPosts(getContext(), friendSet, page, new SocialManager.FetchPostsCallback() {
                    @Override
                    public void onFailure(String errorMsg) {

                    }

                    @Override
                    public void onFinish(List<Post> data) {
                        page ++;
                        postList.addAll(data);
                        feedAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }


}















