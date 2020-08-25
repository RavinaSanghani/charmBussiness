package com.app.charmbusiness;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.app.charmbusiness.retrofit.ApiCall;

import static com.app.charmbusiness.PrefManager.KEY_LOGIN_TOKEN;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private Activity activity;
    private Context context;
    private PrefManager prefManager;
    private String loginToken;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity=this;
        context=this;
        prefManager=new PrefManager(context);

        progressBar = findViewById(R.id.progressBar);

        if(!"".equals(prefManager.getString(KEY_LOGIN_TOKEN,""))){
            loginToken=prefManager.getString(KEY_LOGIN_TOKEN,"");
        }

        Utility.printLog(TAG, "run:loginToken: "+loginToken );

        if (Utility.isConnectedToInternet(context)){
            showProgressBar();
            ApiCall.employeeStatus(SplashActivity.this,loginToken);
        }else {
            Utility.showMessageDialog(SplashActivity.this,Constants.NO_INTERNET_CONNECTION);
        }

       /* Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        },3000);*/
    }
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
