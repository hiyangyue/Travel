package com.yueyang.travel.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.view.wiget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/19.
 */
public class PhotoDetailActivity extends BaseActivity {


    @Bind(R.id.photo_detail_iv)
    ImageView photoDetailIv;
    @Bind(R.id.photo_detail_avatar)
    CircleImageView photoDetailAvatar;
    @Bind(R.id.photo_detail_nickname)
    TextView photoDetailNickname;
    @Bind(R.id.photo_detail_time)
    TextView photoDetailTime;

    private boolean hideView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            ViewCompat.setTransitionName(photoDetailIv,"test");
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (hideView) {
                getSupportActionBar().hide();
                photoDetailAvatar.setVisibility(View.INVISIBLE);
                photoDetailNickname.setVisibility(View.INVISIBLE);
                photoDetailTime.setVisibility(View.INVISIBLE);
            } else {
                getSupportActionBar().show();
                photoDetailAvatar.setVisibility(View.VISIBLE);
                photoDetailNickname.setVisibility(View.VISIBLE);
                photoDetailTime.setVisibility(View.VISIBLE);
            }

            hideView = !hideView;
        }
        return super.onTouchEvent(event);
    }

}
