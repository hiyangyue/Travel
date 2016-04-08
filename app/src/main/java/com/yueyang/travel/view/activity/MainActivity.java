package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.manager.SocialManager;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.adapter.HomePagerAdapter;
import com.yueyang.travel.view.wiget.CircleImageView;
import com.yueyang.travel.view.wiget.HighLight;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.toolbar_avatar)
    CircleImageView toolbarAvatar;
    @Bind(R.id.toolbar_nickname)
    TextView toolbarNickname;
    @Bind(R.id.fab_photo)
    FloatingActionButton fabPhoto;
    @Bind(R.id.fab_user)
    FloatingActionButton fabUser;
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.fab_menu)
    FloatingActionMenu fabMenu;

    private MenuItem searchMenuItem;
    private SearchView searchView;
    private HighLight highLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initUserInfo();
        setUpViewPager();

        toolbarAvatar.setOnClickListener(this);
//        isShowHightLight();
    }


    @Override
    public int getLayoutResource() {
        return R.layout.app_bar_main;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_avatar:
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.IS_CURRENT, true);
                intent.putExtras(bundle);
                /**
                 *  Share Elements bugs here
                 */
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//
////                    Pair<View,String> p1 = Pair.create((View)toolbarNickname,"test_name");
////                    Pair<View,String> p2 = Pair.create((View)toolbarAvatar,"test_avatar");
////                    ActivityOptionsCompat options = ActivityOptionsCompat.
////                            makeSceneTransitionAnimation(MainActivity.this,p1,p2);
////
////                    startActivity(intent,options.toBundle());
//                }

                startActivity(intent);
                break;
        }
    }

    private void initUserInfo() {
        toolbarNickname.setText(SpfHelper.getInstance(this).getMyNickname());
        if (TextUtils.isEmpty(SpfHelper.getInstance(this).getAvatar())) {
            Bitmap avatar = BitmapFactory.decodeResource(getResources(),
                    R.drawable.icon_default_avatar);
            toolbarAvatar.setImageBitmap(avatar);
        } else {
            GlideUtils.loadImg(this, SpfHelper.getInstance(this).getAvatar(), toolbarAvatar, 30, 30);
        }
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
                    fabMenu.hideMenu(true);
                } else {
                    fabMenu.showMenu(true);
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

    public void startSearchActivity(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    private void isShowHightLight() {
        if (!SpfHelper.getInstance(this).getFirstMainActivity()) {
            addHightLight();
            SpfHelper.getInstance(this).updateHightLight();
        }
    }

    private void addHightLight() {
        highLight = new HighLight(this)
                .anchor(findViewById(R.id.container))
                .addHighLight(R.id.fab_menu, R.layout.test, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {

                    }
                })
                .addHighLight(R.id.toolbar_avatar, R.layout.test, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {

                    }
                });

        highLight.show();
    }
}
