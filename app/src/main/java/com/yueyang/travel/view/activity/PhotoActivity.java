package com.yueyang.travel.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.BitmapUtils;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/1.
 */
public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.photo_et)
    EditText photoEt;
    @Bind(R.id.photo_thumb_img)
    ImageView photoThumbImg;
    @Bind(R.id.location_text)
    TextView locationText;
    @Bind(R.id.submit_photo_btn)
    Button submitPhotoBtn;

    private String mPhotoPath;
    private List<byte[]> picBytes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

        submitPhotoBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        mPhotoPath = bundle.getString(Constants.PHOTO_PATH);
        if (mPhotoPath != null){
            Bitmap bitmap = BitmapUtils.compressPic(photoThumbImg,mPhotoPath);
            photoThumbImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_photo_btn:
                Log.e("sub_clicked","...");
                picBytes.add(BitmapUtils.bitmap2byte(mPhotoPath));
                SocialManager.createPost(PhotoActivity.this,
                        getString(R.string.wall_id),
                        UserManager.getInstance(this).getCurrentUser().userId,
                        photoEt.getText().toString(),
                        picBytes,
                        new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Intent intent = new Intent();
                                intent.putExtra(Constants.RESULT_POST,jsonObject.toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                setResult(Activity.RESULT_OK, intent);
                                PhotoActivity.this.finish();
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Log.e("error",jsonObject.toString());
                            }
                        });
                break;
            default:
                break;
        }
    }


}

















