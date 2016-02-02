package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class WebFragment extends Fragment {


    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    private String articleUrl;

    public static WebFragment getInstance(String articleUrl) {
        WebFragment webFragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(Constants.WEB_URL, articleUrl);
        webFragment.setArguments(args);
        return webFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_web, container, false);
        ButterKnife.bind(this, view);
        articleUrl = getArguments().getString(Constants.WEB_URL);
        if (articleUrl != null) {
            initWebView();
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initWebView() {
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
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else if (newProgress != 100) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
