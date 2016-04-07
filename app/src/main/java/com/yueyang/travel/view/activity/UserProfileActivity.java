package com.yueyang.travel.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.BitmapUtils;
import com.yueyang.travel.domin.Utils.BlurUtils;
import com.yueyang.travel.domin.Utils.FileUtils;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.callBack.LoadImageCallBack;
import com.yueyang.travel.view.adapter.ProfilePagerAdapter;
import com.yueyang.travel.view.wiget.CircleImageView;

import org.json.JSONException;
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

    private boolean isCurrent;
    private String userAvatar;
    private String userNickName;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
                            try {
                                String avatarUrl = ParseUtils.getUpdateAvatarUrl(jsonObject);
                                SpfHelper.getInstance(UserProfileActivity.this).updateAvatar(avatarUrl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            profileAvatar.setImageBitmap(bitmap);
                            blur(bitmap);
                            SnackbarUtils.getSnackbar(pager, getString(R.string.avatar_update_success));
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
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager(), userId);
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
        Bundle bundle = getIntent().getExtras();
        isCurrent = bundle.getBoolean(Constants.IS_CURRENT);
        if (isCurrent){
            userAvatar = SpfHelper.getInstance(this).getAvatar();
            userId = SpfHelper.getInstance(this).getMyUserId();
            userNickName = SpfHelper.getInstance(this).getMyNickname();
        }else {
            userAvatar = bundle.getString(Constants.USER_AVATAR_URL);
            userId = bundle.getString(Constants.USER_ID);
            userNickName = bundle.getString(Constants.USER_NICKNAME);
        }

        profileNickName.setText(userNickName);
        if (TextUtils.isEmpty(userAvatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.icon_default_avatar);
            blur(bitmap);
        }else {
            GlideUtils.loadImg(this, userAvatar, profileAvatar, new LoadImageCallBack() {
                @Override
                public void success(Bitmap bitmap) {
                    profileAvatar.setImageBitmap(bitmap);
                    blur(bitmap);
                }

                @Override
                public void onError() {
                    SnackbarUtils.getSnackbar(tabLayout, getString(R.string.avatar_load_error));
                }
            });
        }



    }

    public void updateNickname(View view){
        View customDialog = LayoutInflater.from(UserProfileActivity.this).inflate(R.layout.custom_dialog,null);
        final EditText nickNameEt = (EditText) customDialog.findViewById(R.id.dialog_et);
        nickNameEt.setText(userNickName);
        nickNameEt.setSelection(userNickName.length());

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialog);
        builder.setView(customDialog);
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = nickNameEt.getText().toString();
                if(TextUtils.isEmpty(text)||text.trim().isEmpty()) {
                    SnackbarUtils.getSnackbar(tabLayout,getString(R.string.nick_null));
                    return;
                }else if (nickNameEt.equals(text)){
                    dialog.cancel();
                }

                UserManager.getInstance(UserProfileActivity.this)
                        .updateNickName(SpfHelper.getInstance(UserProfileActivity.this).getMyUsername(), text, new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                try {
                                    String updateName = ParseUtils.getUpdateNickname(jsonObject);
                                    profileNickName.setText(updateName);
                                    SpfHelper.getInstance(UserProfileActivity.this).updateNickname(updateName);
                                    SnackbarUtils.getSnackbar(tabLayout,getString(R.string.update_nick_success));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {

                            }
                        });

            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
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

    private void blur(Bitmap bitmap){
        Bitmap blurred = BlurUtils.blurRenderScript(this, bitmap, 10);
        blurImg.setImageBitmap(blurred);
    }
}















