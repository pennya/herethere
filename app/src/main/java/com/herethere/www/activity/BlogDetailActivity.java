package com.herethere.www.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.herethere.www.R;
import com.victor.loading.rotate.RotateLoading;

public class BlogDetailActivity extends AppCompatActivity {

    private WebView mWebview;
    private RotateLoading mRotateLoding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        actionBarInit();
        layoutInit();
        setDefaultSetting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        url = url.replace("&amp;", "&");

        /*WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mRotateLoding.setVisibility(View.VISIBLE);

                if(newProgress > 100)
                    mRotateLoding.setVisibility(View.GONE);
            }
        };*/

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRotateLoding.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRotateLoding.stop();
            }
        };

        mWebview.loadUrl(url);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setUserAgentString("Android-WebView-Herethere");
        //mWebview.setWebChromeClient(webChromeClient);
        mWebview.setWebViewClient(webViewClient);
    }

    private void layoutInit() {
        mWebview = (WebView)findViewById(R.id.wv_activity_blog_detail_webview);
        mRotateLoding = (RotateLoading)findViewById(R.id.rotateloading);
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    @Override
    protected void onDestroy() {
        mRotateLoding.stop();
        super.onDestroy();
    }
}
