package com.yueyang.travel.presenter;

import android.util.Log;

import com.yueyang.travel.model.Impl.ICountryDetailImpl;
import com.yueyang.travel.model.Impl.ICountryDetailModel;
import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.model.callBack.BgCallBack;
import com.yueyang.travel.model.callBack.DesDetailCallBack;
import com.yueyang.travel.view.IDesDetailView;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public class DesDetailPresenter {

    private ICountryDetailModel desDetailModel;
    private IDesDetailView desDetailView;

    public DesDetailPresenter(IDesDetailView desDetailView) {
        this.desDetailView = desDetailView;
        desDetailModel = new ICountryDetailImpl();
    }

    public void loadBg(){
        desDetailModel.loadBg(desDetailView.getCountryId(), new BgCallBack() {
            @Override
            public void onSuccess(String bgUrl) {
                desDetailView.loadBg(bgUrl);
            }

            @Override
            public void onError() {
                Log.e("bg","error");
            }
        });
    }

    public void loadCityList(){

        desDetailModel.loadCitys(desDetailView.getCountryId(), new DesDetailCallBack() {
            @Override
            public void onSuccess(List<City> cityList) {
                desDetailView.loadCitys(cityList);
            }

            @Override
            public void onError() {
                Log.e("load_city","error");
            }
        });
    }
}
