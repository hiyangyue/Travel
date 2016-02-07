package com.yueyang.travel.model.callBack;

import android.graphics.Bitmap;

/**
 * Created by Yang on 2016/2/7.
 */
public interface LoadImageCallBack {

    void success(Bitmap bitmap);

    void onError();

}
