package com.yueyang.travel.model.callBack;

import com.yueyang.travel.model.bean.Desitination;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public interface DesitinationCallback {

    void onSuccess(List<Desitination> desitinationList);

    void onError();

}
