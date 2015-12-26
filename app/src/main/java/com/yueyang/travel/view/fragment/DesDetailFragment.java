package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.MaterialUtils;
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
public class DesDetailFragment extends Fragment implements IDesDetailView{

    @Bind(R.id.header)
    ImageView header;
    @Bind(R.id.des_detail_title)
    TextView desDetailTitle;
    @Bind(R.id.des_detail_subtitle)
    TextView desDetailSubtitle;
    @Bind(R.id.anim_toolbar)
    Toolbar animToolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.recycler_des_detail)
    RecyclerView recyclerDesDetail;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private int countryId;
    private String cnName,enName;
    private List<City> cityList;
    private DesDetailAdapter adapter;
    private DesDetailPresenter presenter;

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
        Bundle bundle = getActivity().getIntent().getExtras();
        countryId = bundle.getInt("country_id");
        enName = bundle.getString("en_name");
        cnName = bundle.getString("cn_name");

        presenter = new DesDetailPresenter(this);
        presenter.loadBg();
    }

    private void initToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(animToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle(cnName);
        desDetailTitle.setText(cnName);
        desDetailSubtitle.setText(enName);
    }

    private void initRecyclerView(){
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerDesDetail.setLayoutManager(mLayoutManager);
        recyclerDesDetail.setHasFixedSize(true);
        recyclerDesDetail.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
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
        MaterialUtils.setToolbarPattle(getActivity(), header, url, collapsingToolbar);
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
