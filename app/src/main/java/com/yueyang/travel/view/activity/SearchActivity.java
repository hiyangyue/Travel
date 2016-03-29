package com.yueyang.travel.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lapism.searchview.view.SearchView;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.manager.SocialManager;
import com.yueyang.travel.view.activity.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YueYang on 2016/3/29.
 */
public class SearchActivity extends AppCompatActivity{

    @Bind(R.id.searchView)
    SearchView searchView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        ButterKnife.bind(this);
        initSearchView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initSearchView(){
        searchView.show(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressDialog();
                SocialManager.fetchUserByUserName(SearchActivity.this, query, new SocialManager.FetchUserCallback() {
                    @Override
                    public void onFailure(JSONObject object) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onSuccess(JSONObject object) {
                        hideProgressDialog();
                        try {
                            ParseUtils.getUserByName(object);
                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登入中...");
        progressDialog.show();
    }

    private void hideProgressDialog(){
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

}
















