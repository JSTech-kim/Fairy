package com.jstech.fairy.Fragment.Add_Diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jstech.fairy.Fragment.Add_Diary.Crop_Image_Library.CropView;
import com.jstech.fairy.R;

public class Crop_Image extends AppCompatActivity {

    CropView cropView = (CropView)findViewById(R.id.crop_view);
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__image);


        Intent intent = getIntent();
        bitmap = (Bitmap)intent.getParcelableExtra("image");
        cropView.setImageBitmap(bitmap);
    }

}
