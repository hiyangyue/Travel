package com.yueyang.travel.view.activity;

import android.os.Bundle;

import com.yueyang.travel.R;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.view.fragment.UserPostFragment;

/**
 * Created by Yang on 2016/1/14.
 */
public class UserPostNavActivity extends BaseNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,UserPostFragment.getInstance(SpfHelper.getInstance(this).getMyUserId()))
                .commit();
    }


    @Override
    public int getLayoutResource() {
        return R.layout.base_nav;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle(getResources().getString(R.string.post_title));
    }
}
