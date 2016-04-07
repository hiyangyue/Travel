package com.yueyang.travel.view.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lapism.searchview.view.SearchView;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.BlurUtils;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.SocialManager;
import com.yueyang.travel.model.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YueYang on 2016/3/29.
 */
public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.searchView)
    SearchView searchView;

    private ProgressDialog progressDialog;
    private ViewStub viewStub;

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

    private void initSearchView() {
        searchView.show(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showProgressDialog();
                SocialManager.fetchUserByUserName(SearchActivity.this, query, new SocialManager.FetchUserCallback() {
                    @Override
                    public void onFailure(JSONObject object) {
                        hideProgressDialog();
                        SnackbarUtils.getSnackbar(searchView, getString(R.string.action_search_failure));
                    }

                    @Override
                    public void onSuccess(JSONObject object) {
                        hideProgressDialog();
                        searchView.out();
                        SnackbarUtils.getSnackbar(searchView, getString(R.string.action_search_success));
                        try {
                            initViewStub(ParseUtils.getUserByName(object));
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

    private void initViewStub(User user){
        viewStub = (ViewStub) findViewById(R.id.view_stub_user);
        final View view  = viewStub.inflate();

        TextView tvNickName = (TextView) view.findViewById(R.id.tv_nick_name);
        ImageView ivBlurBg = (ImageView) view.findViewById(R.id.iv_blur_bg);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_follow);

        tvNickName.setText(user.nickname);
        if (user.userPhotoUrl == null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap blurBg = BlurUtils.blurRenderScript(
                    this,
                    BitmapFactory.decodeResource(this.getResources(),R.drawable.test_profile,options),
                    20);

            ivBlurBg.setImageBitmap(blurBg);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtils.getSnackbar(view,"Clicked");
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登入中...");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

}
















