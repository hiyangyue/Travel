package com.yueyang.travel.model.callBack;

import com.yueyang.travel.model.bean.City;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public interface DesDetailCallBack {

    void onSuccess(List<City> cities);

    void onError();

}
