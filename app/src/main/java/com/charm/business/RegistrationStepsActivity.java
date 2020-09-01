package com.charm.business;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charm.business.retrofit.ApiCall;
import com.google.gson.JsonObject;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RegistrationStepsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegistrationStepsActivi";
    private LinearLayout ll1, ll2, ll3, ll4;
    private ImageView img_next;
    private PrefManager prefManager;
    private int registration_step;
    private TextView txt_next;
    private Button btn_re_register;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_steps);

        init();

    }

    private void init() {
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);
        ll4 = findViewById(R.id.ll4);

        img_next = findViewById(R.id.img_next);
        txt_next = findViewById(R.id.txt_next);
        btn_re_register = findViewById(R.id.btn_re_register);
        progressBar = findViewById(R.id.progressBar);

        img_next.setOnClickListener(this);
        txt_next.setOnClickListener(this);
        btn_re_register.setOnClickListener(this);

        prefManager = new PrefManager(RegistrationStepsActivity.this);
        registration_step = prefManager.getInteger(PrefManager.KEY_REGISTRATION_STEP, 1);

        layoutChange(registration_step);
    }

    private void layoutDisable(LinearLayout linearLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout.setBackground(getApplicationContext().getDrawable(R.drawable.steps_disable));
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View viewChild = linearLayout.getChildAt(i);
            if (viewChild instanceof TextView) {
                TextView text = (TextView) viewChild;
                text.setTextColor(getResources().getColor(R.color.themeDisableColor));
            }
        }
    }

    private void layoutEnable(LinearLayout linearLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout.setBackground(getApplicationContext().getDrawable(R.drawable.steps_enable));
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View viewChild = linearLayout.getChildAt(i);
            if (viewChild instanceof TextView) {
                TextView text = (TextView) viewChild;
                text.setTextColor(getResources().getColor(R.color.themeEnableColor));
            }
        }
    }

    private void layoutSelected(LinearLayout linearLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout.setBackground(getApplicationContext().getDrawable(R.drawable.step_selected));
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View viewChild = linearLayout.getChildAt(i);
            if (viewChild instanceof TextView) {
                TextView text = (TextView) viewChild;
                text.setTextColor(getResources().getColor(R.color.themeLabelTitleColor));
            }
        }
    }

    private void layoutChange(int number) {
        switch (number) {
            case 1:
                layoutSelected(ll1);
                layoutEnable(ll2);
                layoutEnable(ll3);
                layoutEnable(ll4);
                break;
            case 2:
                layoutDisable(ll1);
                layoutSelected(ll2);
                layoutEnable(ll3);
                layoutEnable(ll4);
                break;
            case 3:
                layoutDisable(ll1);
                layoutDisable(ll2);
                layoutSelected(ll3);
                layoutEnable(ll4);
                break;
            case 4:
                layoutDisable(ll1);
                layoutDisable(ll2);
                layoutDisable(ll3);
                layoutSelected(ll4);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:
           /* String[] languageList = {"Step 1", "Step 2", "Step 3", "Step 4"};
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationStepsActivity.this);
            builder.setTitle("Select Steps");
            builder.setSingleChoiceItems(languageList, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        layoutChange(1);
                    } else if (which == 1) {
                        layoutChange(2);
                    } else if (which == 2) {
                        layoutChange(3);
                    } else if (which == 3) {
                        layoutChange(4);
                    }

                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();*/
                Utility.startActivity(RegistrationStepsActivity.this,WebViewActivity.class,false);
                break;
            case R.id.txt_next:
                img_next.performClick();
                break;
            case R.id.btn_re_register:
                employeeLogout();
                break;
        }
    }

    private void employeeLogout() {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Constants.KEY_API_USER_ID, prefManager.getInteger(PrefManager.KEY_EMPLOYEE_ID,0));
        Utility.printLog(TAG,"employeeLogout:jsonObject:"+jsonObject);

        if (Utility.isConnectedToInternet(RegistrationStepsActivity.this)){
            showProgressBar();
            ApiCall.employeeLogout(RegistrationStepsActivity.this,jsonObject);
        }else {
            Utility.showDialog(RegistrationStepsActivity.this,Constants.KEY_ALERT,Constants.NO_INTERNET_CONNECTION);
        }

    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}
