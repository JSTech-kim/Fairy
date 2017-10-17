package com.jstech.fairy.Fragment.Add_Diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jstech.fairy.Fragment.Add_Diary.Crop_Image_Library.CropView;
import com.jstech.fairy.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.data;

public class Crop extends AppCompatActivity {

    private Uri galleryPictureUri;
    private FloatingActionButton Rotate_Button ;
    private FloatingActionButton Crop_Button ;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Crop_Button= (FloatingActionButton)findViewById(R.id.Crop_fab);
        Rotate_Button = (FloatingActionButton)findViewById(R.id.Rotate_fab);

        final CropView cropView = (CropView)findViewById(R.id.Crop_CropView);
        Intent intent = getIntent();
        galleryPictureUri = intent.getParcelableExtra("Uri");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), galleryPictureUri);
        }catch (IOException e){

        }
        cropView.setImageBitmap(bitmap);

        Rotate_Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bitmap = imgRotate(bitmap);
                cropView.setImageBitmap(bitmap);
            }
            private Bitmap imgRotate(Bitmap bmp){
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
                bmp.recycle();
                return resizedBitmap;
            }
        });

        Crop_Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bitmap = cropView.crop();
                Intent data = new Intent();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                data.putExtra("BMP",bytes);
                setResult(RESULT_OK,data);
                finish();
            }
        });

    }
}
