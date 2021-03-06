package com.yueyang.travel.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.BitmapUtils;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.manager.SocialManager;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.model.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/1.
 */
public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.photo_et)
    EditText photoEt;
    @Bind(R.id.photo_thumb_img)
    ImageView photoThumbImg;
    @Bind(R.id.fab_send)
    FloatingActionButton fabSend;

    private String mPhotoPath;
    private List<byte[]> picBytes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        fabSend.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        mPhotoPath = bundle.getString(Constants.PHOTO_PATH);
        if (mPhotoPath != null) {
            GlideUtils.loadImg(this, mPhotoPath, photoThumbImg, 30, 30);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_send:
                picBytes.add(BitmapUtils.bitmap2byte(mPhotoPath));
                SocialManager.createPost(PhotoActivity.this,
                        getString(R.string.wall_id),
                        SpfHelper.getInstance(this).getMyUserId(),
                        photoEt.getText().toString(),
                        picBytes,
                        new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Intent intent = new Intent();
                                intent.putExtra(Constants.RESULT_POST, jsonObject.toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                setResult(Activity.RESULT_OK, intent);
                                PhotoActivity.this.finish();
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Log.e("error", jsonObject.toString());
                            }
                        });
                break;
            default:
                break;
        }
    }


}

















