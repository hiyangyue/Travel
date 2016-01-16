package com.yueyang.travel.view.activity;

import android.os.Bundle;

import com.yueyang.travel.R;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.view.fragment.UserListFragment;

/**
 * Created by Yang on 2016/1/15.
 */
public class UserListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,UserListFragment.getInstance(true, UserManager.getInstance(this).getCurrentUser().userId))
                .commit();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.base_container;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.post_title));
    }
}
