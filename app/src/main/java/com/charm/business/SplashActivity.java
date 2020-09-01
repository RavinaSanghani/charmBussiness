package com.charm.business;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.charm.business.retrofit.ApiCall;

import static com.charm.business.PrefManager.KEY_LOGIN_TOKEN;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private Activity activity;
    private Context context;
    private PrefManager prefManager;
    private String loginToken;
    private ProgressBar progressBar;
    private DialogProgressBar dialogProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        context = this;
        prefManager = new PrefManager(context);

        progressBar = findViewById(R.id.progressBar);

        loginToken = prefManager.getString(PrefManager.KEY_LOGIN_TOKEN, "");

        Utility.printLog(TAG, "run:loginToken: " + loginToken);

        if (Utility.isConnectedToInternet(context)) {
            //showProgressBar();
            dialogProgressBar = new DialogProgressBar(activity);
            dialogProgressBar.show();
            dialogProgressBar.setCanceledOnTouchOutside(false);

            ApiCall.employeeStatus(SplashActivity.this, loginToken);
        } else {
            Utility.showDialog(SplashActivity.this,Constants.KEY_ALERT,Constants.NO_INTERNET_CONNECTION);
        }

    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void dialogProgressBarDiamiss() {
        dialogProgressBar.dismiss();
    }
}
