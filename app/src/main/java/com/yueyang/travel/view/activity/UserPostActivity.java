package com.yueyang.travel.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yueyang.travel.R;
import com.yueyang.travel.view.fragment.UserPostFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/14.
 */
public class UserPostActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_container);
        ButterKnife.bind(this);

        setUpToolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,new UserPostFragment())
                .commit();
    }

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.post_title));
    }

}
