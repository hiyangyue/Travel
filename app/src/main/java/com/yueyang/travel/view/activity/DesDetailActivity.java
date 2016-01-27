package com.yueyang.travel.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.CountryFragment;

import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/16.
 */
public class DesDetailActivity extends AppCompatActivity {

    private String cnName,enName;
    private int countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_detail);
        ButterKnife.bind(this);
        getBundleData();
        initFragment(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void getBundleData(){
        Bundle bundle = getIntent().getExtras();
        countryId = bundle.getInt("country_id");
        enName = bundle.getString("en_name");
        cnName = bundle.getString("cn_name");

    }

    private void initFragment(Bundle saveInstanceState){
        if (saveInstanceState != null){
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, CountryFragment.newInstance(countryId,cnName,enName))
                .commit();
    }

}





