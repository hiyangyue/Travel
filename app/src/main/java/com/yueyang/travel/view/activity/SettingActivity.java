package com.yueyang.travel.view.activity;

import android.os.Bundle;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.SettingFragment;

import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/12.
 */
public class SettingActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);
        ButterKnife.bind(this);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new SettingFragment()).commit();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.base_container;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle(R.string.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
