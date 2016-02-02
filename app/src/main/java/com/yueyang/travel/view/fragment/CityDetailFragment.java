package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.Utils.TravelApi;
import com.yueyang.travel.model.bean.CityDetail;
import com.yueyang.travel.view.adapter.CityDetailAdapter;
import com.yueyang.travel.view.wiget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Yang on 2015/12/15.
 */
public class CityDetailFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.imageDetail)
    ImageView imageDetail;
    @Bind(R.id.city_cn_name)
    TextView cityCnName;
    @Bind(R.id.city_en_name)
    TextView cityEnName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;

    private int cityId;
    private String cityPhotoUrl, cnName, enName;
    private CityDetailAdapter adapter;
    private List<CityDetail> cityDetailList;

    public static CityDetailFragment getInstance(int cityId, String cityPhotoUrl, String cnName, String enName) {
        CityDetailFragment detailFragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putInt("city_id", cityId);
        args.putString("photo_url", cityPhotoUrl);
        args.putString("cnName", cnName);
        args.putString("enName", enName);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_city_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        initToolbar();
        setUpRecyclerView();
        getData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init() {
        cityId = getArguments().getInt("city_id");
        cityPhotoUrl = getArguments().getString("photo_url");
        cnName = getArguments().getString("cnName");
        enName = getArguments().getString("enName");

        cityCnName.setText(cnName);
        cityEnName.setText(enName);
        if (cityPhotoUrl != null) {
            GlideUtils.loadImg(getContext(), cityPhotoUrl, imageDetail);
        }

    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getData() {

        showProgressBar();

        TravelApi.RestClient.getCityDetaill(TravelApi.getDesCityDetail(cityId), null, new BaseJsonHttpResponseHandler<List<CityDetail>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<CityDetail> response) {
                hideProgressBar();
                cityDetailList.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<CityDetail> errorResponse) {

            }

            @Override
            protected List<CityDetail> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return ParseUtils.getCityDetail(rawJsonData, 5);
            }
        });
    }

    private void setUpRecyclerView() {

        cityDetailList = new ArrayList<>();
        adapter = new CityDetailAdapter(getContext(), cityDetailList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
}
