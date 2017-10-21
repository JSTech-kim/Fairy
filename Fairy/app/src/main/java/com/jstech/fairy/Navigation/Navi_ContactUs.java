package com.jstech.fairy.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jstech.fairy.R;

public class Navi_ContactUs extends AppCompatActivity {
    ArrayAdapter Developers_List_Adapter;
    String UserTitle;
    String Receiver;
    String EmailBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_email);

        Toolbar toolbar = (Toolbar)findViewById(R.id.email_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("문의 사항");

        Spinner Developer_List = (Spinner)findViewById(R.id.Developer_List);
        Developers_List_Adapter = ArrayAdapter.createFromResource(this,R.array.Developers,android.R.layout.simple_spinner_item);
        Developers_List_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Developer_List.setAdapter(Developers_List_Adapter);

        //리스너 부탁
        Developer_List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1)
                    Receiver = "Sujinsub@naver.com";
                else if(position == 2)
                    Receiver = "rlqkrwls@naver.com";
                else if(position ==  3)
                    Receiver = "ehfkddl29@naver.com";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Receiver = null;
            }
        });
    }

    /*현지 : 전송버튼 액션바로 이동*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_send:
                EditText EmailTitle = (EditText) findViewById(R.id.EmailTitle) ; // 메일 제목 받아오기
                UserTitle = EmailTitle.getText().toString();
                EditText EmailText = (EditText) findViewById(R.id.EmailText) ; // 메일 본문 받아오기
                EmailBody = EmailText.getText().toString();

                if(UserTitle.trim().getBytes().length <= 0) { // 공백 또는 아무 내용이 없을 때
                    Toast.makeText(getApplicationContext(), "제목을 입력하시오.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(Receiver == null) { // 받는 사람 선택안했을 때
                    Toast.makeText(getApplicationContext(), "받는 사람을 정하시오.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(EmailBody.trim().getBytes().length <= 0){ // 공백 또는 아무 내용이 없을 때
                    Toast.makeText(getApplicationContext(), "본문을 입력하시오.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",Receiver, null));
                intent.putExtra(Intent.EXTRA_SUBJECT, UserTitle);
                intent.putExtra(Intent.EXTRA_TEXT , EmailBody);
                startActivity(Intent.createChooser(intent, "Send mail..."));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }
}
