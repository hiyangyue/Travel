package com.yueyang.travel.domin.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yueyang.travel.model.callBack.LoadImageCallBack;

/**
 * Created by Yang on 2015/12/10.
 */
public class GlideUtils {

    public static void loadImg(Context context,String imgUrl,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImg(Context context,String imgUrl,ImageView imageView,int width,int height){
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .override(width,height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImg(Context context,String imgUrl,ImageView imageView, final LoadImageCallBack callBack){
        Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        callBack.success(resource);
                    }
                });
    }
}
