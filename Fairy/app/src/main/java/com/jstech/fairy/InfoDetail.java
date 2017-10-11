package com.jstech.fairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jstech.fairy.DataType.InfoDataType;

public class InfoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        Intent intent = getIntent();
        InfoDataType infoData = intent.getParcelableExtra("InfoData");

        //  infoData안에 해당 카드뷰 클릭했을 때, 자세한 정보가 모두 들어있으니 꺼내서 배치하면 됨.
        //   To. 현지

        TextView testTv = (TextView)findViewById(R.id.temp_title);
        testTv.setText(infoData.getStrTitle());

    }
}
