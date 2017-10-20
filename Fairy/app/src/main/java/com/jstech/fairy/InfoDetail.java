package com.jstech.fairy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.DataType.InfoDataType;
import com.jstech.fairy.MoreFunction.PicassoTransformations;
import com.squareup.picasso.Picasso;

public class InfoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        Intent intent = getIntent();
        InfoDataType infoData = intent.getParcelableExtra("InfoData");

        Toolbar toolbar = (Toolbar)findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("");

        //  infoData안에 해당 카드뷰 클릭했을 때, 자세한 정보가 모두 들어있으니 꺼내서 배치하면 됨.
        //   To. 현지

        ImageView mainIv = (ImageView)findViewById(R.id.main_iv);

        Picasso.with(getApplicationContext()).load(infoData.getStrMainImg())
                .placeholder(R.drawable.loading_image)                              // 이미지 불러오는 동안 이미지
                // .transform(PicassoTransformations.resizeTransformation)           //  리사이즈
                .error(R.drawable.no_image)                                  // 다운로드 실패 시, 이미지
                .fit()                                                            // 이미지뷰에 맞추기
                .into(mainIv);

        TextView titleTv = (TextView)findViewById(R.id.info_detail_title);
        titleTv.setText(infoData.getStrTitle());

        TextView dateTv = (TextView)findViewById(R.id.info_detail_date);
        dateTv.setText(infoData.getStrStartDate() + " ~ " + infoData.getStrEndDate());

        TextView timeTv = (TextView)findViewById(R.id.info_detail_time);
        timeTv.setText(infoData.getStrTime());

        TextView placeTv = (TextView)findViewById(R.id.info_detail_place);
        placeTv.setText(infoData.getStrPlace());

        TextView targetTv = (TextView)findViewById(R.id.info_detail_target);
        targetTv.setText(infoData.getStrUseTarget());

        TextView feeTv = (TextView)findViewById(R.id.info_detail_fee);

        if("1".equals(infoData.getStrIsFree())) //무료일 경우
        {
            feeTv.setText("무료");
        }
        else // 유료일 경우
        {
            feeTv.setText(infoData.getStrUseFee());
        }

        TextView hostTv = (TextView)findViewById(R.id.info_detail_host);
        hostTv.setText(infoData.getStrSponsor());

        TextView inquiryTv = (TextView)findViewById(R.id.info_detail_inquiry);
        inquiryTv.setText(infoData.getStrInquiry());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

            case R.id.action_heart:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }

    public void onClick_EnlargImage(View v){
        setContentView(R.layout.activity_enlarge);

        Intent intent = getIntent();
        InfoDataType infoData = intent.getParcelableExtra("InfoData");

        ImageView mainIv = (ImageView)findViewById(R.id.enlarge_image);

        Picasso.with(getApplicationContext()).load(infoData.getStrMainImg())
                .placeholder(R.drawable.loading_image)                              // 이미지 불러오는 동안 이미지
                // .transform(PicassoTransformations.resizeTransformation)           //  리사이즈
                .error(R.drawable.no_image)                                  // 다운로드 실패 시, 이미지
                .fit()                                                            // 이미지뷰에 맞추기
                .into(mainIv);
    }
}
