package com.yueyang.travel.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.view.activity.PhotoActivity;
import com.yueyang.travel.view.adapter.FeedAdapter;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/19.
 */
public class FeedFragment extends Fragment {

    @Bind(R.id.feed_recycler)
    RecyclerView feedRecycler;
    @Bind(R.id.fabButton)
    FloatingActionButton fab;
    @Bind(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;

    private String mCurrentPhotoPath;
    private FeedAdapter feedAdapter;
    private List<Post> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        setUpFab();
        setUpRecyclerView();
//        initData();
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
                        Post post = ParseUtils.getPost(data.getExtras().getString(Constants.TEST_1));
                        postList.add(post);
                        feedAdapter.notifyDataSetChanged();

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    try {
                        File file = createFile();
                        if (file != null) {
                            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            startActivityForResult(takePhotoIntent, Constants.REQUEST_TAKE_PHOTO);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setUpRecyclerView() {
        postList = new ArrayList<>();

        Post post = new Post("Hello World");
        postList.add(post);

//        List<String> stringList = new ArrayList<>();
//        stringList.add("hello world");
        feedAdapter = new FeedAdapter(getContext(),postList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecycler.setItemAnimator(new DefaultItemAnimator());
        feedRecycler.setLayoutManager(layoutManager);
        feedRecycler.setAdapter(feedAdapter);

    }

    public File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_hhMMss").format(new Date());
        String imgFileName = "JPEG" + "_" + timeStamp + "_";
        //使用SD卡的标识
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File file = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
    }

    private byte[] bitmap2Byte(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

    private void hideProgessBar(){
        progressBar.hide();
    }

    private void showProgessBar(){
        progressBar.show();
    }
}















