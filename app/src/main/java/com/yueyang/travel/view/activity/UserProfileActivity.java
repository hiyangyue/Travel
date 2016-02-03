package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.BitmapUtils;
import com.yueyang.travel.domin.Utils.BlurUtils;
import com.yueyang.travel.domin.Utils.FileUtils;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.domin.manager.UserManager;
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

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager pager;
    @Bind(R.id.profile_avatar)
    CircleImageView profileAvatar;
    @Bind(R.id.profile_nickName)
    TextView profileNickName;
    @Bind(R.id.blur_img)
    ImageView blurImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        ButterKnife.bind(this);

        initUserInfo();
        setUpViewPager();
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
                            blur();
                        }

                        @Override
                        public void onFailure(JSONObject jsonObject) {
                            SnackbarUtils.getSnackbar(pager, getString(R.string.avatar_update_error));
                        }
                    });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_setting:
                startActivity(new Intent(this,SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager() {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager(), SpfHelper.getInstance(this).getMyUserId());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }
    }

    private String[] tabTitles = { "Feed" , "Followers" , "Fans" };
    private String[] nums = { "118" , "90" , "22"};

    public View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setText(tabTitles[position]);
        TextView num = (TextView) v.findViewById(R.id.tab_num);
        num.setText(nums[position]);
        return v;
    }

    private void initUserInfo() {
        profileNickName.setText(SpfHelper.getInstance(this).getMyNickname());
        if (SpfHelper.getInstance(this).getAvatar() != null) {
            Log.e("2", SpfHelper.getInstance(this).getAvatar());
            GlideUtils.loadImg(this, SpfHelper.getInstance(this).getAvatar(), profileAvatar);
            blur();
        }


    }

    private void updateUserAvatar(ImageView avatarImg) {
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

    private void blur(){
//        Bitmap bitmap = ((BitmapDrawable) profileAvatar.getDrawable()).getBitmap();
        BitmapDrawable drawable = (BitmapDrawable) profileAvatar.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap blurred = BlurUtils.blurRenderScript(this, bitmap, 25);
        blurImg.setImageBitmap(blurred);
    }
}















