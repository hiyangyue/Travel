package com.yueyang.travel.view;

import com.yueyang.travel.model.bean.City;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public interface IDesDetailView {

    void loadBg(String url);

    void loadCitys(List<City> cityList);

    int getCountryId();

}
