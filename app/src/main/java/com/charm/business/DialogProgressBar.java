package com.charm.business;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DialogProgressBar extends Dialog {

    private static final String TAG="DialogProgressBar";
    private Activity activity;
    private ImageView img_progress_bar;
    public DialogProgressBar(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_bar);

        img_progress_bar = findViewById(R.id.img_progress_bar);
        Glide.with(activity).load("file:///android_asset/progress_bar_gif.gif").into(img_progress_bar);
    }
}
