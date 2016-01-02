package com.yueyang.travel.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.LoginFragment;
import com.yueyang.travel.view.fragment.RegisterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/18.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.login_pager)
    ViewPager loginPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reigster);
        ButterKnife.bind(this);
        setUpViewPager();
    }

    private void setUpViewPager(){
        LoginPagerAdapter adapter = new LoginPagerAdapter(getSupportFragmentManager());
        loginPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(loginPager);
    }


    private class LoginPagerAdapter extends FragmentPagerAdapter{

        private final int PAGE_COUNT = 2;
        private String mTabs[] = { "登入" , "注册" };

        public LoginPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new LoginFragment();
                case 1:
                    return new RegisterFragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }
    }















}
