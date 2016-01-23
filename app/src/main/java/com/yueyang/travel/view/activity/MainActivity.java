package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.adapter.HomePagerAdapter;
import com.yueyang.travel.view.wiget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseNavActivity implements View.OnClickListener{

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar_avatar)
    CircleImageView toolbarAvatar;
    @Bind(R.id.toolbar_nickname)
    TextView toolbarNickname;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setUpViewPager();

        toolbarAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.USER_ID, SpfHelper.getInstance(MainActivity.this).getMyUserId());
                bundle.putString(Constants.USER_NICKNAME, SpfHelper.getInstance(MainActivity.this).getMyNickname());
                bundle.putString(Constants.USER_AVATAR_URL, UserManager.getInstance(MainActivity.this).getCurrentUser().userPhotoUrl);
                intent.putExtras(bundle);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Pair<View,String> p1 = Pair.create((View)toolbarNickname,getString(R.string.transitions_nickname));
                    Pair<View,String> p2 = Pair.create((View)toolbarAvatar,getString(R.string.transitions_avatar));
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, p1,p2);
                    startActivity(intent,options.toBundle());
                }

                startActivity(intent);
            }
        });



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

        int[] icons = {R.drawable.selector_home, R.drawable.selector_explore, R.drawable.selector_heart, R.drawable.selector_explore};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
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

    private boolean isExpand(int position) {
        if (position == 3) {
            return true;
        } else {
            return false;
        }
    }

    private void updateSearchStatus(boolean isExpand) {
        if (isExpand) {
            MenuItemCompat.expandActionView(searchMenuItem);
            searchView.clearFocus();
        } else {
            MenuItemCompat.collapseActionView(searchMenuItem);
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.toolbar_avatar:
//                Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.USER_ID, SpfHelper.getInstance(MainActivity.this).getMyUserId());
//                bundle.putString(Constants.USER_NICKNAME, SpfHelper.getInstance(MainActivity.this).getMyNickname());
//                bundle.putString(Constants.USER_AVATAR_URL, UserManager.getInstance(MainActivity.this).getCurrentUser().userPhotoUrl);
//                intent.putExtras(bundle);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                    Pair<View,String> p1 = Pair.create((View)toolbarNickname,getString(R.string.transitions_nickname));
//                    Pair<View,String> p2 = Pair.create((View)toolbarAvatar,getString(R.string.transitions_avatar));
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, p1,p2);
//                    startActivity(intent,options.toBundle());
//                }
//
//                startActivity(intent);
//                break;
//        }
    }
}
