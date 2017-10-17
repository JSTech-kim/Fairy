package com.jstech.fairy.Fragment.Add_Diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jstech.fairy.Fragment.Add_Diary.Crop_Image_Library.CropView;
import com.jstech.fairy.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Write_Diary extends AppCompatActivity {
    public  static boolean comeback=false;

    final int PICK_PHOTO = 1;
    final int CROP_PHOTO = 2;

    CropView CropView_Photo;
    ImageButton Button_Add_Photo;
    Bitmap finalbitmap;

    TextView TextView_date;
    EditText EditText_Title;

    int mYear, mMonth, mDay;
    String DataBase_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        CropView_Photo=(CropView)findViewById(R.id.Photo) ;
        Button_Add_Photo=(ImageButton)findViewById(R.id.Add_Photo_Button);
        Bitmap bitmap;

        /*===============날짜 고르는 코드=========================*/
        TextView_date = (TextView)findViewById(R.id.Date_Viewer);
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        UpdateNow();
        /*===============날짜 고르는 코드=========================*/


        /*===============  제목 넣는 코드  ==============*/
        EditText_Title = (EditText) findViewById(R.id.Title_Diary);
        DataBase_title = EditText_Title.getText().toString();
        /*===============  제목 넣는 코드  ==============*/
    }

    /*======================================사진 골라 넣기 버튼 이벤트===========================================*/
    public void Add_Photo(View v){
        comeback = true;
        CropView_Photo.extensions().pickUsing(this,PICK_PHOTO);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO&& resultCode == Activity.RESULT_OK) {
            Uri galleryPictureUri = data.getData();
            Intent intent = new Intent(this,Crop.class);
            intent.putExtra("Uri",galleryPictureUri);
            startActivityForResult(intent,CROP_PHOTO);
        }

        else if(requestCode == CROP_PHOTO && resultCode == Activity.RESULT_OK){


            CropView_Photo.setImageBitmap(finalbitmap);
        }
    }

    /*=====================================사진 골라 넣기 버튼 이벤트=============================================*/

    /*==================================================날짜 고르는 코드==========================================================*/
    public void Date_Choise(View v){new DatePickerDialog(Write_Diary.this, mDateSetListener, mYear, mMonth, mDay).show();}

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트
            UpdateNow();
        }
    };
    void UpdateNow(){
        TextView_date.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));}
    /*==================================================날짜 고르는 코드==========================================================*/

}
