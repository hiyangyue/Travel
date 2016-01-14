package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.BitmapUtils;
import com.yueyang.travel.Utils.FileUtils;
import com.yueyang.travel.Utils.SnackbarUtils;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.adapter.HomePagerAdapter;
import com.yueyang.travel.view.wiget.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private CircleImageView headerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpViewPager();
        setUpNav();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    private void setUpNav() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        View headerView = navView.getHeaderView(0);
        TextView nickNameText = (TextView) headerView.findViewById(R.id.drawer_nickname);
        nickNameText.setText(SpfHelper.getInstance(this).getMyNickname());
        headerImg = (CircleImageView) headerView.findViewById(R.id.drawer_header_img);
        headerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpViewPager() {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 || position == 2){
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICK_PHOTO
                && resultCode == RESULT_OK && data != null) {

            final String imagePath = FileUtils.getRealPathFromURI(this, data.getData());
            final Bitmap bitmap = BitmapUtils.compressPic(headerImg, imagePath);
            UserManager.getInstance(this).
                    updateMyPhoto(BitmapUtils.bitmap2byte(bitmap), new IAnSocialCallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            headerImg.setImageBitmap(bitmap);
                            SnackbarUtils.getSnackbar(drawer, getString(R.string.avatar_update_success));
                        }

                        @Override
                        public void onFailure(JSONObject jsonObject) {
                            SnackbarUtils.getSnackbar(drawer, getString(R.string.avatar_update_error));
                        }
                    });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
