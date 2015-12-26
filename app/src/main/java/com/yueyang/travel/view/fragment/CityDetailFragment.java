package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/15.
 */
public class CityDetailFragment extends Fragment {


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
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recycler_des_detail)
    RecyclerView recyclerDesDetail;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private int cityId;
    private String cityPhotoUrl,cnName,enName;

    public static CityDetailFragment getInstance(int cityId,String cityPhotoUrl,String cnName,String enName) {
        CityDetailFragment detailFragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putInt("city_id", cityId);
        args.putString("photo_url", cityPhotoUrl);
        args.putString("cnName", cnName);
        args.putString("enName",enName);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityId = getArguments().getInt("city_id");
        cityPhotoUrl = getArguments().getString("photo_url");
        cnName = getArguments().getString("cnName");
        enName = getArguments().getString("enName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_des_detail, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(animToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle(cnName);
        desDetailTitle.setText(cnName);
        desDetailSubtitle.setText(enName);
        GlideUtils.loadImg(getActivity(),cityPhotoUrl,header);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
}
