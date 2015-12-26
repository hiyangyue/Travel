package com.yueyang.travel.model.Impl;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.Utils.TravelApi;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.model.callBack.DesitinationCallback;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Yang on 2015/12/17.
 */
public class IDesitinationImpl implements IDesitinationModel {

    @Override
    public void loadDesitination(final DesitinationCallback callback) {
        TravelApi.RestClient.getDesitinations(TravelApi.DESTINATION, null, new BaseJsonHttpResponseHandler<List<Desitination>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<Desitination> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<Desitination> errorResponse) {
                callback.onError();
            }

            @Override
            protected List<Desitination> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                List<Desitination> desitinationList = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    Desitination desitination = ParseUtils.getDesitination(rawJsonData, i);
                    desitinationList.add(desitination);
                }
                return desitinationList;
            }
        });
    }
}
