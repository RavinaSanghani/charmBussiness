package com.charm.business;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charm.business.control.GoEditText;
import com.charm.business.control.GoEditTextListener;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Utility {

    private static DialogProgressBar dialogProgressBar;

    public static void startActivity(Activity activity, Class<?> c, boolean isFinish) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void printLog(String tag, String msg) {
        Log.e(tag, msg + "");
    }

    public static boolean isConnectedToInternet(Context context) {
        if (context != null) {

            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

            }
        }
        return false;
    }

    public static void showDialog(Activity activity,String title,String message) {
        TextView title_text = new TextView(activity);
        title_text.setText(title);
        title_text.setPadding(10, 10, 10, 10);
        title_text.setGravity(Gravity.CENTER);
        title_text.setTextColor(android.graphics.Color.BLACK);
        title_text.setTextSize(23);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.DialogRounded);
        builder.setCustomTitle(title_text);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView message_text = alertDialog.findViewById(android.R.id.message);
        assert message_text != null;
        message_text.setGravity(Gravity.CENTER);
        message_text.setTextColor(android.graphics.Color.BLACK);

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(activity.getResources().getColor(R.color.themeStepsLinkColor));
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        android.view.View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(android.view.View.GONE);
    }

    public static void progressBarDialogShow(Activity activity){
        dialogProgressBar=new DialogProgressBar(activity);
        dialogProgressBar.show();
        dialogProgressBar.setCanceledOnTouchOutside(false);
    }

    public static void progressBarDialogDismiss() {
        dialogProgressBar.dismiss();
    }

}
