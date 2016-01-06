package com.yueyang.travel.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.model.Constants;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
    private String mPhotoDescribe;
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
            compressPic();
        }
    }

    //对图片进行缩放
    private void compressPic(){
        int targetW  = 120;
        int targetH = 120;

        BitmapFactory.Options options = new BitmapFactory.Options();
        //避免在解码之前进行内存分配
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(mPhotoPath, options);
        int photoW = options.outWidth;
        int photoH = options.outHeight;

        int scaleSize = 1;
        if (targetH / photoH >= 1 || targetW / photoW >= 1){
            scaleSize = scaleSize * 2;
        }

        //对图片进行解析
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath,options);
        photoThumbImg.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_photo_btn:

                picBytes.add(bitmap2Byte(mPhotoPath));
                Log.e("btn_click","...");
                SocialManager.createPost(PhotoActivity.this,
                        getString(R.string.wall_id),
                        SpfHelper.getInstance(this).getMyUserId(),
                        photoEt.getText().toString(),
                        picBytes,
                        new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Log.e("success",jsonObject.toString());
                                Log.e("success","...");
                                Intent intent = new Intent();
                                intent.putExtra(Constants.TEST_1,jsonObject.toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                setResult(Activity.RESULT_OK, intent);
                                PhotoActivity.this.finish();
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Log.e("error",jsonObject.toString());
                            }
                        });






//                picBytes.add(bitmap2Byte(mPhotoPath));
                mPhotoDescribe = photoEt.getText().toString();

//                Intent intent = new Intent();
//                intent.putExtra(Constants.TEST_1,mPhotoDescribe);
//                intent.putExtra(Constants.TEST_2,bitmap2Byte(mPhotoPath));
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                setResult(Activity.RESULT_OK,intent);
//                this.finish();
                break;
            default:
                break;
        }
    }

    private byte[] bitmap2Byte(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

















