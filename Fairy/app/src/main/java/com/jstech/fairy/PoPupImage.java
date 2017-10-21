package com.jstech.fairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.jstech.fairy.Fragment.Add_Diary.Crop_Image_Library.CropView;
import com.squareup.picasso.Picasso;

public class PoPupImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_pup_image);
        CropView BigPucture = (CropView)findViewById(R.id.BigPucture);

        //this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // 배경 어두워지는거 막기 ... 이거 추가하는데 고생했는데 현지가 하지 말자한다.

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        BigPucture.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.8),(int)(height*0.7)));

        Intent intent = getIntent();
        String path = intent.getStringExtra("Uri");

        Picasso.with(getApplicationContext()).load(path)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.no_image)
                .fit()
                .into(BigPucture);
    }


}
