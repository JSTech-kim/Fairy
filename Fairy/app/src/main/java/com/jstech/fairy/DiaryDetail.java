package com.jstech.fairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.DataType.DiaryDataType;
import com.squareup.picasso.Picasso;

public class DiaryDetail extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView picture;
    private TextView title;
    private TextView text;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        Intent intent = getIntent();

        DiaryDataType diaryData = new DiaryDataType();
        diaryData =intent.getParcelableExtra("DiaryData");

        toolbar = (Toolbar)findViewById(R.id.diary_detail_toolbar);
        picture = (ImageView)findViewById(R.id.diary_detail_photo);
        title = (TextView)findViewById(R.id.diary_detail_Title_Diary);
        text = (TextView)findViewById(R.id.diary_detail_Text_Diary);
        date = (TextView)findViewById(R.id.diary_detail_Date_Viewer);


        Toolbar toolbar = (Toolbar)findViewById(R.id.diary_detail_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("Read Diary");


        Picasso.with(this).load(R.drawable.loading_image).fit().into(picture);
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
        switch(id)
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_rewrite:
                return true;

            case R.id.action_remove:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }
}
