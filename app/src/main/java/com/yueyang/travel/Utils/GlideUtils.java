package com.yueyang.travel.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

/**
 * Created by Yang on 2015/12/10.
 */
public class GlideUtils {

    public static void loadImg(Context context,String imgUrl,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static Bitmap getBitmap(Context context,String imgUrl) throws ExecutionException, InterruptedException {

        return Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .into(RecyclerView.LayoutParams.MATCH_PARENT,200)
                .get();
    }

}
