package com.yueyang.travel.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Yang on 2016/1/7.
 */
public class BitmapUtils {

    public static byte[] bitmap2byte(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        return bos.toByteArray();
    }

    public static void compressPic(ImageView iv,String photoPath){
        Log.e("hello1","world");
        int targetW = iv.getWidth();
        int targetH = iv.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath,options);

        options.inSampleSize = calculateInSize(options,targetW,targetH);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath,options);
        iv.setImageBitmap(bitmap);
        Log.e("hello","world");
    }

    public static int calculateInSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int weight = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || weight > reqHeight){
            int halfHeight = height / 2;
            int halfWidth = height / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
