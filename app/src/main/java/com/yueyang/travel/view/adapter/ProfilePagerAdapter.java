package com.yueyang.travel.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yueyang.travel.view.fragment.UserListFragment;
import com.yueyang.travel.view.fragment.UserPostFragment;

/**
 * Created by Yang on 2016/1/14.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    private String userId;

    public ProfilePagerAdapter(FragmentManager fm,String userId) {
        super(fm);
        this.userId = userId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return UserPostFragment.getInstance(userId);
            case 1:
                return UserListFragment.getInstance(true,userId);
            case 2:
                return UserListFragment.getInstance(false,userId);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
