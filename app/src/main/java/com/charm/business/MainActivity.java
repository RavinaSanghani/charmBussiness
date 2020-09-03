package com.charm.business;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView webView;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefManager = new PrefManager(MainActivity.this);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = Constants.BASE_URL_WEB_VIEW + "?pg=dash&lt=" + prefManager.getString(PrefManager.KEY_LOGIN_TOKEN, "");

        Utility.printLog(TAG,"url main:"+url);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Utility.progressBarDialogShow(MainActivity.this);
                Utility.printLog(TAG, "url:" + url);
                if (url.contains("pg=blank")){
                    prefManager.setString(PrefManager.KEY_LOGIN_TOKEN,"");
                    prefManager.getInteger(PrefManager.KEY_EMPLOYEE_ID,0);
                    Utility.startActivity(MainActivity.this,HomeActivity.class,false);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Utility.progressBarDialogDismiss();
            }
        });

    }

}