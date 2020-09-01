package com.charm.business;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private WebView webView;
    private ProgressBar progressBar;

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        prefManager = new PrefManager(WebViewActivity.this);

        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = Constants.BASE_URL_WEB_VIEW + "?pg=stp" + prefManager.getInteger(PrefManager.KEY_REGISTRATION_STEP, 0) + "&lt=" + prefManager.getString(PrefManager.KEY_LOGIN_TOKEN, "");

        Utility.printLog(TAG,"url main:"+url);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.setVisibility(View.VISIBLE);
                Utility.printLog(TAG, "url:" + url);
                if (url.contains("pg=blank")){
                    prefManager.setString(PrefManager.KEY_LOGIN_TOKEN,"");
                    prefManager.getInteger(PrefManager.KEY_EMPLOYEE_ID,0);
                    Utility.startActivity(WebViewActivity.this,HomeActivity.class,false);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
