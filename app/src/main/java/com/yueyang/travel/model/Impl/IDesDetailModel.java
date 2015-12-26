package com.yueyang.travel.model.Impl;

import com.yueyang.travel.model.callBack.BgCallBack;
import com.yueyang.travel.model.callBack.DesDetailCallBack;

/**
 * Created by Yang on 2015/12/17.
 */
public interface IDesDetailModel {

    void loadBg(int cityId,BgCallBack callBack);

    void loadCitys(int countryId,DesDetailCallBack callBack);

}
