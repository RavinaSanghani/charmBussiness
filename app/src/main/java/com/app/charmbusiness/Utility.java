package com.app.charmbusiness;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;

public class Utility {

    public static void startActivity(Activity activity, Class<?> c, boolean isFinish) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void startActivity(Activity activity, Class<?> c, boolean isFinish, boolean deleteAllStack) {
        Intent intent = new Intent(activity, c);
        if (isFinish) {
            activity.finish();
        }
        if (deleteAllStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
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

    public static void showMessageDialog(Activity activity, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();

        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_launcher_background);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (DialogInterface.OnClickListener) (dialog, which) -> alertDialog.dismiss());

        if (!activity.isFinishing())
            alertDialog.show();
    }

}
