package com.yueyang.travel.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.yueyang.travel.R;
import com.yueyang.travel.view.adapter.HomePagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseNavActivity{

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setUpViewPager();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setUpViewPager() {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(pager);

        int[] icons = { R.drawable.selector_home,R.drawable.selector_explore,R.drawable.selector_heart,R.drawable.selector_explore };
        for (int i = 0 ; i < tabLayout.getTabCount() ; i ++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateSearchStatus(isExpand(position));

                if (position == 1 || position == 2) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setVisible(false);

        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.action_search_hint));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private boolean isExpand(int position){
        if (position == 3){
            return true;
        }else {
            return false;
        }
    }

    private void updateSearchStatus(boolean isExpand){
        if (isExpand) {
            MenuItemCompat.expandActionView(searchMenuItem);
            searchView.clearFocus();
        }else {
            MenuItemCompat.collapseActionView(searchMenuItem);
        }
    }

}
