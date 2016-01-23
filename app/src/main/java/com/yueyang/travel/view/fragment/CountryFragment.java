package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.presenter.DesDetailPresenter;
import com.yueyang.travel.view.IDesDetailView;
import com.yueyang.travel.view.adapter.DesDetailAdapter;
import com.yueyang.travel.view.wiget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/16.
 */
public class CountryFragment extends Fragment implements IDesDetailView{

//    @Bind(R.id.header)
//    ImageView header;
    @Bind(R.id.recycler_des_detail)
    RecyclerView recyclerDesDetail;

    private int countryId;
    private String cnName,enName;
    private List<City> cityList;
    private DesDetailAdapter adapter;
    private DesDetailPresenter presenter;

    public static CountryFragment newInstance(int countryId,String cnName,String enName){
        CountryFragment fragment = new CountryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("country_id",countryId);
        bundle.putString("cn_name",cnName);
        bundle.putString("en_name",enName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_des_detail, container, false);

        ButterKnife.bind(this, view);

        getBundleData();
        initToolbar();
        initRecyclerView();
        return view;
    }

    private void getBundleData(){
        countryId = getArguments().getInt("country_id");
        enName = getArguments().getString("en_name");
        cnName = getArguments().getString("cn_name");

        presenter = new DesDetailPresenter(this);
    }

    private void initToolbar() {


    }

    private void initRecyclerView(){
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerDesDetail.setLayoutManager(mLayoutManager);
        recyclerDesDetail.setHasFixedSize(true);
        recyclerDesDetail.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        recyclerDesDetail.setItemAnimator(new DefaultItemAnimator());

        cityList = new ArrayList<>();
        adapter = new DesDetailAdapter(getActivity(),cityList);
        recyclerDesDetail.setAdapter(adapter);

        presenter.loadCityList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void loadBg(String url) {
//        MaterialUtils.setToolbarPattle(getActivity(), header, url, collapsingToolbar);
    }

    @Override
    public void loadCitys(List<City> cities) {
        cityList.addAll(cities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getCountryId() {
        return countryId;
    }
}
