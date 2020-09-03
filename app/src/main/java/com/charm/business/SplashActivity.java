package com.charm.business;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.charm.business.retrofit.ApiCall;

import java.util.Objects;

import static com.charm.business.PrefManager.KEY_LOGIN_TOKEN;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private PrefManager prefManager;
    private String loginToken;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefManager = new PrefManager(SplashActivity.this);
        loginToken = prefManager.getString(PrefManager.KEY_LOGIN_TOKEN, "");

        Utility.printLog(TAG, "run:loginToken: " + loginToken);

        if (Utility.isConnectedToInternet(SplashActivity.this)) {
            Utility.progressBarDialogShow(SplashActivity.this);
            ApiCall.employeeStatus(SplashActivity.this, loginToken);

        } else {
            Utility.showDialog(SplashActivity.this, Constants.KEY_ALERT, Constants.NO_INTERNET_CONNECTION);
        }

    }

}
