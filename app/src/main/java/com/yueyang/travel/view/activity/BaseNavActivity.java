package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.view.wiget.CircleImageView;

/**
 * Created by Yang on 2016/1/16.
 */
public abstract class BaseNavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private NavigationView navView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        initToolbar();
        initDrawer();
    }

    protected abstract int getLayoutResource();

    protected void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected void initDrawer(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        View headerView = navView.getHeaderView(0);
        TextView nickNameText = (TextView) headerView.findViewById(R.id.drawer_nickname);
        nickNameText.setText(SpfHelper.getInstance(this).getMyNickname());
        CircleImageView headerImg = (CircleImageView) headerView.findViewById(R.id.drawer_header_img);
        headerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseNavActivity.this,UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.USER_ID, SpfHelper.getInstance(BaseNavActivity.this).getMyUserId());
                bundle.putString(Constants.USER_NICKNAME,SpfHelper.getInstance(BaseNavActivity.this).getMyNickname());
                bundle.putString(Constants.USER_AVATAR_URL, UserManager.getInstance(BaseNavActivity.this).getCurrentUser().userPhotoUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_post:
                Intent postIntent = new Intent(this, UserPostNavActivity.class);
                startActivity(postIntent);
                break;
            case R.id.nav_follow:
                Intent intent = new Intent(this,UserListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_setting:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}










