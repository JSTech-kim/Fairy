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
import android.os.Environment;
import android.support.annotation.Nullable;
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

import com.jstech.fairy.DataType.DiaryDataType;
import com.jstech.fairy.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Write_Diary extends AppCompatActivity {
    public  static boolean comeback=false;

    final int PICK_PHOTO = 1;
    final int CROP_PHOTO = 2;

    private DiaryDataType diaryData;
    private boolean isRewrite;
    private String pastFilename;
    private String[] pastDate;

    ImageView ImageView_Photo;
    ImageButton Button_Add_Photo;
    Bitmap finalbitmap = null;

    TextView TextView_date;
    EditText EditText_Title;
    EditText EditText_Text;

    private int ImgPathLength;

    int mYear, mMonth, mDay;

    String DataBase_Date = null ;
    String DataBase_title = null;
    String DataBase_PictureURI = null;
    String DataBase_text = null;
    String DataBase_PictureName = null;

    SQLiteDatabase mSQLiteDatabase;     //  SQLite 접근 객체
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        mContext = this.getApplicationContext();
        ImageView_Photo =(ImageView)findViewById(R.id.Photo) ;
        Button_Add_Photo=(ImageButton)findViewById(R.id.Add_Photo_Button);
        TextView_date = (TextView)findViewById(R.id.Date_Viewer);
        EditText_Title = (EditText) findViewById(R.id.Title_Diary);
        EditText_Text = (EditText)findViewById(R.id.Text_Diary);

        Toolbar toolbar = (Toolbar)findViewById(R.id.diary_write_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        diaryData = intent.getParcelableExtra("data");
        isRewrite = intent.getBooleanExtra("isRewrite",false);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        //  Action Bar
        if(!isRewrite) {
            comeback = false;
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitleChange("일기작성");
            UpdateNow();
        }
        else{
            comeback = false;
            DataBase_Date = diaryData.getStrDate();
            DataBase_title = diaryData.getStrTitle();
            DataBase_PictureURI = diaryData.getStrImgPath();
            DataBase_text = diaryData.getStrMainText();
            DataBase_PictureName = diaryData.getstrImgName();
            pastFilename = DataBase_PictureName;

            ImageView_Photo.setImageBitmap(BitmapFactory.decodeFile(diaryData.getStrImgPath()));
            TextView_date.setText(diaryData.getStrDate());
            EditText_Title.setText(diaryData.getStrTitle());
            EditText_Text.setText(diaryData.getStrMainText());

            pastDate = diaryData.getStrDate().split("-");
            mYear = Integer.parseInt(pastDate[0]);
            mMonth = Integer.parseInt(pastDate[1])-1;
            mDay = Integer.parseInt(pastDate[2]);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitleChange("일기수정");
        }
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
            ImageView_Photo.setImageBitmap(finalbitmap);
            Button_Add_Photo.setAlpha(65);
        }
    }
    /*=====================================사진 골라 넣기 버튼 이벤트=============================================================*/

    /*==================================================날짜 고르는 코드==========================================================*/
    public void Date_Choise(View v){
        new DatePickerDialog(Write_Diary.this, mDateSetListener, mYear, mMonth, mDay).show();
    }
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
        //1자리 숫자일 경우 앞에 0을 붙여주기 위해
        String tempmonth;
        String tempday;
        if(((mMonth+1)/10) <1)
            tempmonth = "0"+String.valueOf(mMonth+1);
        else
            tempmonth =String.valueOf(mMonth+1);
        if((mDay/10)<1)
            tempday = "0"+String.valueOf(mDay);
        else
            tempday =String.valueOf(mDay);

        DataBase_Date = String.valueOf(mYear)+"-"+tempmonth+"-"+tempday;
        TextView_date.setText(String.format("%02d/%02d/%02d", mYear, mMonth + 1, mDay));
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
         - strImgName   이미지 이름   - (256)
     */
    public void InsertDiaryDataToDatabase(String strDate, String strTitle, String strMainText, String strImgPath,String strImgName)
    {
        mSQLiteDatabase = mContext.openOrCreateDatabase(mContext.getString(R.string.database_name), MODE_PRIVATE, null);
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + mContext.getString(R.string.diary_table_name)
                + " (DATE VARCHAR(16), TITLE VARCHAR(256), MAINTEXT VARCHAR(2048), IMGPATH VARCHAR(512), IMGNAME VARCHAR(256));");

        mSQLiteDatabase.execSQL("INSERT INTO " + mContext.getString(R.string.diary_table_name)
                + " (DATE, TITLE, MAINTEXT, IMGPATH, IMGNAME) Values"
                + " ('"+ strDate + "', '"
                + strTitle + "', '"
                + strMainText + "', '"
                + strImgPath + "', '"
                + strImgName + "');");

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
                Save_Diary();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }


    public void Save_Diary(){
        if(comeback) {
            DataBase_PictureURI = saveBitmap(finalbitmap);
            ImgPathLength = DataBase_PictureURI.length();
            DataBase_PictureName = DataBase_PictureURI.substring(ImgPathLength - 18, ImgPathLength - 4);
        }
        DataBase_title = EditText_Title.getText().toString();
        DataBase_text = EditText_Text.getText().toString();
        if(DataBase_PictureURI == null){
            Toast.makeText(getApplicationContext(),"사진을 선택하시오.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(DataBase_title.trim().getBytes().length<=0){
            Toast.makeText(getApplicationContext(),"제목을 입력하시오.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(DataBase_text.trim().getBytes().length<=0){
            Toast.makeText(getApplicationContext(),"본문을 입력하시오.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isRewrite){ //  수정했을 때, 먼저 있던 데이터 베이스의 정보를 삭제한다.
            mSQLiteDatabase = mContext.openOrCreateDatabase(mContext.getString(R.string.database_name), MODE_PRIVATE, null);
            mSQLiteDatabase.execSQL("DELETE FROM " +mContext.getString(R.string.diary_table_name)+
                    " WHERE IMGNAME = "+pastFilename+";");
            mSQLiteDatabase.close();
            if(DataBase_PictureName != pastFilename) {
                File removePicture = new File(diaryData.getStrImgPath());
                if (removePicture.exists())
                    removePicture.delete();
            }
        }

        InsertDiaryDataToDatabase(DataBase_Date,DataBase_title,DataBase_text,DataBase_PictureURI,DataBase_PictureName);
        Toast.makeText(getApplicationContext(),"일기를 저장하였습니다.",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Nullable
    private String saveBitmap(Bitmap bitmap){
        String FileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Fairy";
         try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Fairy");
            if(!file.isDirectory()){
                file.mkdir();
            }
            FileOutputStream outputStream = new FileOutputStream(file+"/"+FileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return path+"/"+FileName;
    }


}
