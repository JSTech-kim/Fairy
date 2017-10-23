package com.jstech.fairy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jstech.fairy.DataType.DiaryDataType;
import com.jstech.fairy.Fragment.Add_Diary.Write_Diary;

import java.io.File;

public class DiaryDetail extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView picture;
    private TextView title;
    private TextView text;
    private TextView date;
    private DiaryDataType diaryData;
    private Context mContext;

    SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);
        mContext = this.getApplicationContext();

        Intent intent = getIntent();
        diaryData = new DiaryDataType();
        diaryData =intent.getParcelableExtra("DiaryData");

        toolbar = (Toolbar)findViewById(R.id.diary_detail_toolbar);
        picture = (ImageView)findViewById(R.id.diary_detail_photo);
        title = (TextView)findViewById(R.id.diary_detail_Title_Diary);
        text = (TextView)findViewById(R.id.diary_detail_Text_Diary);
        date = (TextView)findViewById(R.id.diary_detail_Date_Viewer);



        setSupportActionBar(toolbar);
        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("Diary");



        picture.setImageBitmap(BitmapFactory.decodeFile(diaryData.getStrImgPath()));
        title.setText(diaryData.getStrTitle());
        text.setText(diaryData.getStrMainText());
        date.setText(diaryData.getStrDate());

    }

    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_rewrite:
                Intent intent = new Intent(this, Write_Diary.class);
                intent.putExtra("data",diaryData);
                intent.putExtra("isRewrite",true);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_remove:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("일기 삭제")
                        .setMessage("일기를 삭제하시겠습니까 ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeDiary();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeDiary(){
        mSQLiteDatabase = mContext.openOrCreateDatabase(mContext.getString(R.string.database_name), MODE_PRIVATE, null);
        mSQLiteDatabase.execSQL("DELETE FROM " +mContext.getString(R.string.diary_table_name)+
                " WHERE IMGNAME = "+diaryData.getstrImgName()+";");
        mSQLiteDatabase.close();
        File removePicture = new File(diaryData.getStrImgPath());
        if(removePicture.exists())
            removePicture.delete();
        Toast.makeText(mContext,"일기를 삭제하였습니다.",Toast.LENGTH_LONG).show();
        finish();
    }
    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }
}
