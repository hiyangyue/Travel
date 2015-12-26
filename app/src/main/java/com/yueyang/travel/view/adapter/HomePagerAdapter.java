package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yueyang.travel.view.fragment.DesitinationFragment;
import com.yueyang.travel.view.fragment.FeedFragment;
import com.yueyang.travel.view.fragment.RecommendFragment;
import com.yueyang.travel.view.fragment.TestFragment;

/**
 * Created by Yang on 2015/12/10.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private Context mContext;
    private String mTabs[] = { "推荐" , "目的地" ,"发现" };

    public HomePagerAdapter(Context mContext,FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 2:
                return new RecommendFragment();
            case 1:
                return new DesitinationFragment();
            case 0:
                return new FeedFragment();
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
