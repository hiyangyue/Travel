package com.yueyang.travel.model.callBack;

import com.yueyang.travel.model.bean.HotPlace;

import java.util.List;

/**
 * Created by Yang on 2016/1/19.
 */
public interface HotPlaceCallback {

    void onSuccess(List<HotPlace> hotPlaceList);

    void onError(String errorMessage);

}
