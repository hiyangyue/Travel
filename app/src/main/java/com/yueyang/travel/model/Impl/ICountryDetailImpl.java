package com.yueyang.travel.model.Impl;

import android.util.Log;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.Utils.TravelApi;
import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.model.callBack.BgCallBack;
import com.yueyang.travel.model.callBack.DesDetailCallBack;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Yang on 2015/12/17.
 */
public class ICountryDetailImpl implements ICountryDetailModel {

    @Override
    public void loadBg(int countryId, final BgCallBack callBack) {
        TravelApi.RestClient.getBg(TravelApi.getDestinationDetail(countryId), null, new BaseJsonHttpResponseHandler<String>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, String response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, String errorResponse) {
                callBack.onError();
            }

            @Override
            protected String parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return ParseUtils.getImgUrl(rawJsonData);
            }
        });
    }

    @Override
    public void loadCitys(int countryId, final DesDetailCallBack callBack) {
        TravelApi.RestClient.getDesDetail(TravelApi.getCityList(countryId), null, new BaseJsonHttpResponseHandler<List<City>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<City> response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<City> errorResponse) {
                callBack.onError();
            }

            @Override
            protected List<City> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                List<City> cityList = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    City city = ParseUtils.getCity(rawJsonData, i);
                    cityList.add(city);
                }

                return cityList;
            }
        });
    }
}
