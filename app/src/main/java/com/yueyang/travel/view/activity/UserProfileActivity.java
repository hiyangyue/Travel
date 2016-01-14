package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.yueyang.travel.manager.SpfHelper;
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
public class UserProfileActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpViewPager();
        initUserInfo();
        updateUserAvatar(profileAvatar);
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

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        MaterialUtils.setToolbarPattle(profileBg, collapseToolbar);
    }

    private void setUpViewPager() {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initUserInfo(){
        profileNickName.setText(SpfHelper.getInstance(this).getMyNickname());
        String avatarUrl = UserManager.getInstance(this).getCurrentUser().userPhotoUrl;
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















