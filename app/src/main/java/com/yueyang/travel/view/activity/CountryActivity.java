package com.yueyang.travel.view.activity;

import android.os.Bundle;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.DesitinationFragment;

import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/21.
 */
public class CountryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new DesitinationFragment()).commit();

    }

    @Override
    public int getLayoutResource() {
        return R.layout.base_nav;
    }
}
