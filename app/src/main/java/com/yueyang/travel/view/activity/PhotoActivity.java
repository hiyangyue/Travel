package com.yueyang.travel.view.activity;

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
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private List<byte[]> byteList;
    private String mPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        byteList = new ArrayList<>();
        submitPhotoBtn.setOnClickListener(this);
        setThumbImg();

    }

    private void setThumbImg() {
        mPhotoPath = getIntent().getExtras().getString(Constants.PHOTO_PATH);

        if (mPhotoPath != null) {
            //通过地址解析图片
            Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath);
            photoThumbImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_photo_btn:

                Log.e("click", "...");
                byteList.add(getDataFromFilePath(mPhotoPath));

                SocialManager.createPost(PhotoActivity.this,
                        getString(R.string.wall_id),
                        UserManager.getInstance(this).getCurrentUser().userId,
                        photoEt.getText().toString(),
                        byteList, new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {

                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                
                            }
                        });
                break;
            default:
                break;
        }
    }

    private byte[] getDataFromFilePath(String filePath){
        Bitmap bmResized = null ;
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] imgData = new byte[size];
        try{
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(imgData, 0, imgData.length);
                buf.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(filePath);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=256;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(filePath);
            bmResized = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bmResized.compress(Bitmap.CompressFormat.JPEG, 100, blob);
        imgData = blob.toByteArray();

        return imgData;
    }


}

















