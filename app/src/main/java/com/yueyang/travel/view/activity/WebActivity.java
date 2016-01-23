package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.yueyang.travel.R;
import com.yueyang.travel.model.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/23.
 */
public class WebActivity extends BaseActivity {


    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    private String articleUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share){
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_TEXT,articleUrl);
            intent.setType("text/plain");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_web;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {

        articleUrl = getIntent().getStringExtra(Constants.WEB_URL);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ChromeClient());
//        webView.setWebViewClient(new ViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(articleUrl);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressbar.setProgress(newProgress);
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else if (newProgress != 100) {
                progressbar.setVisibility(View.VISIBLE);
            }
        }
    }
}
