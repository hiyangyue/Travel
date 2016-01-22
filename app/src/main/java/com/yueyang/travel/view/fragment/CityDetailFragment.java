package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/15.
 */
public class CityDetailFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private int cityId;
    private String cityPhotoUrl, cnName, enName;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_city_detail, container, false);
        ButterKnife.bind(this, view);
        init();
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
    }
}
