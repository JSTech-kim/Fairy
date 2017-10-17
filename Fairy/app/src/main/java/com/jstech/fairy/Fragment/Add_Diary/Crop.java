package com.jstech.fairy.Fragment.Add_Diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.jstech.fairy.Fragment.Add_Diary.Crop_Image_Library.CropView;
import com.jstech.fairy.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;

public class Crop extends AppCompatActivity {

    private Uri galleryPictureUri;
    private FloatingActionButton Rotate_Button ;
    private FloatingActionButton Crop_Button ;
    private float degree=0;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Crop_Button= (FloatingActionButton)findViewById(R.id.Crop_fab);
        Rotate_Button = (FloatingActionButton)findViewById(R.id.Rotate_fab);

        final CropView cropView = (CropView)findViewById(R.id.Crop_CropView);

        /*                        네모 비율을 완벽하게 레이아웃과 맞춤                    */
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        float HeightRatio = (float)(height*0.3333334);
        float WidthRatio = width;
        cropView.setViewportRatio(WidthRatio/HeightRatio);
        /*                       오차율 0.00000몇 %                                        */

        Intent intent = getIntent();
        galleryPictureUri = intent.getParcelableExtra("Uri");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), galleryPictureUri);
        }catch (IOException e){

        }
        cropView.setImageBitmap(bitmap);

        Rotate_Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bitmap = CropView.imgRotate(bitmap);
                degree+=90;
                cropView.setImageBitmap(bitmap);
            }

        });

        Crop_Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bitmap = cropView.crop();


                Intent data = new Intent();
                setResult(RESULT_OK,data);
                finish();
            }
        });

    }
}
