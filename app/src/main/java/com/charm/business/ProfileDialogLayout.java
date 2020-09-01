package com.charm.business;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileDialogLayout extends Dialog  {

    private int[] img_list;
    private ImageView img_select_profile;
    private Context context;
    public ProfileDialogLayout(Context context, int[] img_list, ImageView img_select_profile) {
        super(context);
        this.context=context;
        this.img_list = img_list;
        this.img_select_profile=img_select_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_dialog_layout);

        RecyclerView recycler_dialog = findViewById(R.id.recycler_dialog);
        ProfileDialogRecyclerViewAdapter adapter=new ProfileDialogRecyclerViewAdapter(context,img_list,img_select_profile);
        recycler_dialog.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        recycler_dialog.setAdapter(adapter);

        recycler_dialog.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(15, 20, 15,20);
            }
        });

    }
}
