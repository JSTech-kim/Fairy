package com.jstech.fairy.Fragment.Add_Diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jstech.fairy.R;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Write_Diary extends AppCompatActivity {
    public  static boolean comeback=false;

    final int PICK_PHOTO = 1;
    final int CROP_PHOTO = 2;

    ImageView CropView_Photo;
    ImageButton Button_Add_Photo;
    Bitmap finalbitmap;

    TextView TextView_date;
    EditText EditText_Title;
    EditText EditText_Text;

    int mYear, mMonth, mDay;

    String DataBase_Date;
    String DataBase_title;
    String DataBase_PictureURI;
    String DataBase_text;

    SQLiteDatabase mSQLiteDatabase;     //  SQLite 접근 객체
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        mContext = this.getApplicationContext();
        CropView_Photo=(ImageView)findViewById(R.id.Photo) ;
        Button_Add_Photo=(ImageButton)findViewById(R.id.Add_Photo_Button);
        Bitmap bitmap;

        Toolbar toolbar = (Toolbar)findViewById(R.id.diary_write_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("");

        /*===============날짜 고르는 코드=========================*/
        TextView_date = (TextView)findViewById(R.id.Date_Viewer);
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        UpdateNow();
        /*===============날짜 고르는 코드=========================*/


        /*===================  제목 넣는 코드  ====================*/
        EditText_Title = (EditText) findViewById(R.id.Title_Diary);
        DataBase_title = EditText_Title.getText().toString();

        EditText_Text = (EditText)findViewById(R.id.Text_Diary);
        DataBase_text = EditText_Text.getText().toString();
        /*===================  제목 넣는 코드  ====================*/
    }

    /*======================================사진 골라 넣기 버튼 이벤트============================================================*/
    public void Add_Photo(View v){
        comeback = true;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_PHOTO);
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
            String filename = "Temp.png";
            try {
                FileInputStream TempStream = this.openFileInput(filename);
                finalbitmap = BitmapFactory.decodeStream(TempStream);
                TempStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            CropView_Photo.setImageBitmap(finalbitmap);
            Button_Add_Photo.setAlpha(65);
        }
    }
    /*=====================================사진 골라 넣기 버튼 이벤트=============================================================*/

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

        DataBase_Date = String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay);
        TextView_date.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));

    }
    /*==================================================날짜 고르는 코드==========================================================*/



    /*
        To. 진기 : 파라미터 4개로 값을 넣으면 DataBase에 저장될 걸!?
                    Table에서 데이터 삭제하는거는 상세보기 같은 화면 등에서 삭제버튼 눌렀을 때, 구현되어야할 듯.
        [DiaryTable]
         - strDate      날짜          - (16)
         - strTitle     제목          - (256)
         - strMainText  본문          - (2048)
         - strImgPath   이미지 경로   - (512)
     */
    public void InsertDiaryDataToDatabase(String strDate, String strTitle, String strMainText, String strImgPath)
    {
        mSQLiteDatabase = mContext.openOrCreateDatabase(mContext.getString(R.string.database_name), MODE_PRIVATE, null);
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + mContext.getString(R.string.diary_table_name)
                + " (DATE VARCHAR(16), TITLE VARCHAR(256), MAINTEXT VARCHAR(2048), IMGPATH VARCHAR(512));");

        mSQLiteDatabase.execSQL("INSERT INTO " + mContext.getString(R.string.diary_table_name)
                + " (DATE, TITLE, MAINTEXT, IMGPATH) Values"
                + " ('"+ strDate + "', '"
                + strTitle + "', '"
                + strMainText + "', '"
                + strImgPath + "');");

        mSQLiteDatabase.close();
    }

    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_write, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_save:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }


    public void Save_Diary(){
        Toast.makeText(getApplicationContext(),":aaaaa",Toast.LENGTH_LONG);
        //InsertDiaryDataToDatabase(DataBase_Date,DataBase_title,DataBase_text,"file Uri");
    }
}
