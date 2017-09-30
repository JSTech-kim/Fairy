package com.jstech.fairy.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jstech.fairy.R;

public class Navi_ContactUs extends AppCompatActivity {

    String UserTitle;
    String UserEmail;
    String TextArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi__contact_us);
    }
    public void SendEmail(View v){
        EditText EmailTitle = (EditText) findViewById(R.id.EmailTitle) ;
        UserTitle = EmailTitle.getText().toString();

        EditText EmailString = (EditText) findViewById(R.id.EmailString) ;
        UserEmail = EmailString.getText().toString();

        EditText EmailText = (EditText) findViewById(R.id.EmailText) ;
        TextArea = EmailText.getText().toString();

        Toast.makeText(getApplicationContext(), UserTitle+"   "+UserEmail+"   "+"   "+TextArea, Toast.LENGTH_LONG).show();

    }




}
