package com.jstech.fairy.Navigation;


import android.app.Dialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jstech.fairy.R;

public class Navi_Developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi__developers);

        TextView tv = (TextView)findViewById(R.id.developer_text);
        //  폰트 적용
        Typeface typeFace= Typeface.createFromAsset(this.getAssets(), "fonts/Airplanes_in_the_Night_Sky.ttf");
        tv.setTypeface(typeFace);
    }

    public void onClick_hj(View v){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hj);
        dialog.show();
    }
    public void onClick_js(View v){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_js);
        dialog.show();
    }
    public void onClick_jk(View v){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_jk);
        dialog.show();
    }
}
