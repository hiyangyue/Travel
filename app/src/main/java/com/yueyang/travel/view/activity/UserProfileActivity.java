package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.BitmapUtils;
import com.yueyang.travel.Utils.FileUtils;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.Utils.MaterialUtils;
import com.yueyang.travel.Utils.SnackbarUtils;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.adapter.ProfilePagerAdapter;
import com.yueyang.travel.view.wiget.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/14.
 */
public class UserProfileActivity extends BaseActivity {

    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @Bind(R.id.viewpager)
    ViewPager pager;
    @Bind(R.id.profile_bg)
    ImageView profileBg;
    @Bind(R.id.profile_avatar)
    CircleImageView profileAvatar;
    @Bind(R.id.profile_nickName)
    TextView profileNickName;

    private String userId;
    private String avatarUrl;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
        MaterialUtils.setToolbarPattle(profileBg, collapseToolbar);
        setUpViewPager();
        initUserInfo();
        updateUserAvatar(profileAvatar);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICK_PHOTO
                && resultCode == RESULT_OK && data != null) {

            final String imagePath = FileUtils.getRealPathFromURI(this, data.getData());
            final Bitmap bitmap = BitmapUtils.compressPic(profileAvatar, imagePath);
            UserManager.getInstance(this).
                    updateMyPhoto(BitmapUtils.bitmap2byte(bitmap), new IAnSocialCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            profileAvatar.setImageBitmap(bitmap);
                            SnackbarUtils.getSnackbar(pager, getString(R.string.avatar_update_success));
                        }

                        @Override
                        public void onFailure(JSONObject jsonObject) {
                            SnackbarUtils.getSnackbar(pager, getString(R.string.avatar_update_error));
                        }
                    });
        }

    }

    private void init(){
        userId = getIntent().getExtras().getString(Constants.USER_ID);
        nickName = getIntent().getExtras().getString(Constants.USER_NICKNAME);
        avatarUrl = getIntent().getExtras().getString(Constants.USER_AVATAR_URL);
    }

    private void setUpViewPager() {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager(),userId);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initUserInfo(){
        profileNickName.setText(nickName);
        if (avatarUrl != null){
            GlideUtils.loadImg(this,avatarUrl,profileAvatar);
        }
    }

    private void updateUserAvatar(ImageView avatarImg){
        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constants.REQUEST_PICK_PHOTO);
            }
        });
    }
}















