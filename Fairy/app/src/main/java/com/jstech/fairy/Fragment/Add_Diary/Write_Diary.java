package com.jstech.fairy.Fragment.Add_Diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Write_Diary extends AppCompatActivity {
    private String selectedImagePath;
    ImageView ImageView_Photo;
    Button Button_Add_Photo;

    TextView TextView_date;
    EditText EditText_Title;

    int mYear, mMonth, mDay;
    String DataBase_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write__diary);

        ImageView_Photo=(ImageView)findViewById(R.id.Photo) ;
        Button_Add_Photo=(Button)findViewById(R.id.Add_Photo_Button);

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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    ImageView_Photo.setImageBitmap(bitmap);
                    Button_Add_Photo.getBackground().setAlpha(45);
                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }
    }
    /*======================================사진 골라 넣기 버튼 이벤트=============================================*/

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
