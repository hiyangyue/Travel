package com.yueyang.travel.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.DesitinationFragment;
import com.yueyang.travel.view.fragment.FeedFragment;
import com.yueyang.travel.view.fragment.RecommendFragment;

/**
 * Created by Yang on 2015/12/10.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private String mTabs[] = { "推荐" , "目的地" ,"发现" };
    private int[] imageResId = {
            R.drawable.icon_add,
            R.drawable.icon_commit,
            R.drawable.icon_search
    };

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 2:
                return new RecommendFragment();
            case 0:
                return new DesitinationFragment();
            case 1:
                return new FeedFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTabs[position];
//    }
}
