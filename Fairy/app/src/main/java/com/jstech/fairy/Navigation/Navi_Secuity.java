package com.jstech.fairy.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jstech.fairy.R;

import static com.jstech.fairy.R.id.SecureSwitch;

public class Navi_Secuity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private int SwitchStatement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi__secuity);

        Intent intent = getIntent();
        SwitchStatement = intent.getIntExtra("isSecure",0); // 현재 암호잠금 여부를 받아온다. default 값은 0이지만 0이 전달될 일이 없다.
        Switch IsSecure = (Switch) findViewById(SecureSwitch);

        if(SwitchStatement == 1) // OFF일 때
            IsSecure.setChecked(false);
        else
            IsSecure.setChecked(true);
        IsSecure.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        if (isChecked) {


            


            Intent intent = new Intent();
            intent.putExtra("IsChanged",-1);
            setResult(RESULT_OK,intent);
        }else {
            Intent intent = new Intent();
            intent.putExtra("IsChanged",1);
            setResult(RESULT_OK,intent);
        }

    }

}
