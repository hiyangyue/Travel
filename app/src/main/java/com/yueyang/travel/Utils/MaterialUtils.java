package com.yueyang.travel.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Yang on 2015/12/10.
 */
public class MaterialUtils {

    public static void setSnackbar(View view,String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    public static void setPattle(Context mContext, final ImageView img,String imgUrl, final RelativeLayout rl){
        Glide.with(mContext)
                .load(imgUrl)
                .asBitmap()
                .into(new BitmapImageViewTarget(img) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        Palette palette = Palette.generate(bitmap);
                        if (palette.getMutedSwatch() != null) {
                            rl.setBackgroundColor(palette.getDarkMutedSwatch().getRgb());
                        }

                    }
                });
    }

    public static void setToolbarPattle(final Context mContext, final ImageView img,String imgUrl, final CollapsingToolbarLayout toolbar){
        Glide.with(mContext)
                .load(imgUrl)
                .asBitmap()
                .into(new BitmapImageViewTarget(img) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        Palette palette = Palette.generate(bitmap);
                        if (palette.getMutedSwatch() != null){
                            toolbar.setStatusBarScrimColor(palette.getMutedSwatch().getRgb());
                            toolbar.setContentScrimColor(palette.getMutedSwatch().getRgb());
                        }

                    }
                });
    }

    public static void setToolbarPattle(final ImageView img, final CollapsingToolbarLayout toolbar){
        Bitmap bitmap = ((BitmapDrawable) img.getBackground()).getBitmap();
        Palette palette = Palette.generate(bitmap);
        if (palette.getMutedSwatch() != null){
            toolbar.setStatusBarScrimColor(palette.getMutedSwatch().getRgb());
            toolbar.setContentScrimColor(palette.getMutedSwatch().getRgb());
        }
    }

}
