package com.yueyang.travel.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Yang on 2016/1/7.
 */
public class BitmapUtils {

    public static byte[] bitmap2byte(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static byte[] bitmap2byte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap compressPic(ImageView iv,String photoPath){
        int targetW = iv.getWidth();
        int targetH = iv.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath,options);

        options.inSampleSize = calculateInSize(options,targetW,targetH);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(photoPath,options);
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
