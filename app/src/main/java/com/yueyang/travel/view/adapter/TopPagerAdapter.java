package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Yang on 2015/12/10.
 */
public class TopPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private ArrayList<ImageView> viewlist;
    private ArrayList<View> viewDots;
    private Context mContext;

    public TopPagerAdapter(ArrayList<ImageView> viewlist) {
        this.viewlist = viewlist;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= viewlist.size();
        if (position<0){
            position = viewlist.size()+position;
        }
        ImageView view = viewlist.get(position);
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
