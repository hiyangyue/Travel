package com.yueyang.travel.Utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.model.bean.CityDetail;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.model.bean.HotPlace;
import com.yueyang.travel.model.bean.Topic;

import java.util.List;

/**
 * Created by Yang on 2015/12/10.
 */
public class TravelApi {

    public static final String[] BANNERS = {
            "http://pic.qyer.com/public/mobileapp/homebanner/2015/12/04/14491954106518/w800",
            "http://pic.qyer.com/public/mobileapp/homebanner/2015/12/02/14490482055269/w800",
            "http://pic.qyer.com/public/mobileapp/homebanner/2015/12/10/14497136835251/w800",
    };

    public static final String HOT_PLACE
            = "http://open.qyer.com/qyer/recommands/trip?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915&type=index&page=2&count=5";

    public static final String POPULAR_TOPIC
            = "http://open.qyer.com/qyer/recommands/entry?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915";

    public static final String DESTINATION =
            "http://open.qyer.com/qyer/footprint/continent_list?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915&v=1";

    public static final String getDestinationDetail(int countryId){
        return "http://open.qyer.com/qyer/footprint/country_detail?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915&v=1&country_id=" + countryId;
    }

    public static final String getDesCityDetail(int cityId){
        return "http://open.qyer.com/qyer/footprint/mguide_list?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915&type=city&id="  + cityId + "&count=20&page=1";
    }

    public static final String getCityList(int countryId){
        return "http://open.qyer.com/place/city/get_city_list?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915&v=1&page=1&countryid=" + countryId;
    }

    /**
     * Created by Yang on 2015/12/17.
     */
    public static class RestClient {

        private static AsyncHttpClient client = new AsyncHttpClient();

        public static void getRecommendTopics(String url,RequestParams params,BaseJsonHttpResponseHandler<List<Topic>> handler){
            client.get(url,params,handler);
        }

        public static void getDesitinations(String url,RequestParams params,BaseJsonHttpResponseHandler<List<Desitination>> handler){
            client.get(url,params,handler);
        }

        public static void getBg(String url,RequestParams params,BaseJsonHttpResponseHandler<String> handler){
            client.get(url,params,handler);
        }

        public static void getDesDetail(String url,RequestParams params,BaseJsonHttpResponseHandler<List<City>> handler){
            client.get(url,params,handler);
        }

        public static void getHotPlaces(String url, RequestParams params, BaseJsonHttpResponseHandler<List<HotPlace>> handler){
            client.get(url,params,handler);
        }

        public static void getCityDetaill(String url,RequestParams params,BaseJsonHttpResponseHandler<List<CityDetail>> handler){
            client.get(url,params,handler);
        }


    }
}
