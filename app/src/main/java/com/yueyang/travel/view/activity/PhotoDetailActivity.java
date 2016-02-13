package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.BitmapUtils;
import com.yueyang.travel.domin.Utils.DistanceUtils;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.wiget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/19.
 */
public class PhotoDetailActivity extends BaseActivity{


    @Bind(R.id.photo_detail_photo)
    ImageView photoDetailPhoto;
    @Bind(R.id.photo_detail_avatar)
    CircleImageView photoDetailAvatar;
    @Bind(R.id.photo_detail_nickname)
    TextView photoDetailNickname;
    @Bind(R.id.photo_detail_time)
    TextView photoDetailTime;
    @Bind(R.id.photo_detail_like)
    ImageView photoDetailLike;
    @Bind(R.id.photo_detail_comment)
    ImageView photoDetailComment;

    private boolean hideView = true;
    private String avatarUrl;
    private String photoUrl;
    private String nickname;
    private String createTime;
    private String content;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        init();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_share:
                shareImage(photoUrl);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (hideView) {
                getSupportActionBar().hide();
                photoDetailAvatar.setVisibility(View.INVISIBLE);
                photoDetailNickname.setVisibility(View.INVISIBLE);
                photoDetailTime.setVisibility(View.INVISIBLE);
                photoDetailLike.setVisibility(View.INVISIBLE);
                photoDetailComment.setVisibility(View.INVISIBLE);
            } else {
                getSupportActionBar().show();
                photoDetailAvatar.setVisibility(View.VISIBLE);
                photoDetailNickname.setVisibility(View.VISIBLE);
                photoDetailTime.setVisibility(View.VISIBLE);
                photoDetailLike.setVisibility(View.VISIBLE);
                photoDetailComment.setVisibility(View.VISIBLE);
            }
            hideView = !hideView;
        }
        return super.onTouchEvent(event);
    }


    private void init() {
        final Bundle bundle = getIntent().getExtras();
        photoUrl = bundle.getString(Constants.TRANSITIONS_PHOTO);
        nickname = bundle.getString(Constants.TRANSITIONS_NICK_NAME);
        avatarUrl = bundle.getString(Constants.TRANSITIONS_AVATAR);
        createTime = bundle.getString(Constants.TRANSITIONS_TIME);
        content = bundle.getString(Constants.TRANSITIONS_CONTENT);

        if (photoUrl != null){
            GlideUtils.loadImg(this,photoUrl,photoDetailPhoto, DistanceUtils.getScreenWidth(this),300);
        }
        if (avatarUrl != null) {
            GlideUtils.loadImg(this, avatarUrl, photoDetailAvatar,40,40);
        }
        photoDetailTime.setText(createTime);
        photoDetailNickname.setText(nickname);
        getSupportActionBar().setTitle(content);

        photoDetailComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toCommentActivity(bundle.getString(Constants.TRANSITIONS_POST_ID));
                return true;
            }
        });
    }

    private void toCommentActivity(String postId){
        Intent intent = new Intent(this, CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SEND_POST_ID,postId);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void shareImage(String photoUrl){
        Glide.with(this).load(photoUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        shareIntent(BitmapUtils.getUriFromBitmap(PhotoDetailActivity.this,resource));
                    }
                });
    }

    private void shareIntent(Uri uri){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        if (isIntentSafe(shareIntent)){
            startActivity(shareIntent);
        }else {
            SnackbarUtils.getSnackbar(photoDetailLike,getString(R.string.no_received));
        }
    }

    private boolean isIntentSafe(Intent intent){
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,0);
        return activities.size() > 0;
    }

}





