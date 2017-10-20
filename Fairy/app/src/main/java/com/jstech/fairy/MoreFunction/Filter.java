package com.jstech.fairy.MoreFunction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jstech.fairy.DataType.FilterDataType;
import com.jstech.fairy.MainActivity;
import com.jstech.fairy.R;

public class Filter extends AppCompatActivity {

    private Context mContext;
    private FilterDataType filterData;
    private int iIsFee;
    private boolean bCheckArt;
    private boolean bCheckConcert;
    private boolean bCheckDrama;
    private boolean bCheckFestival;
    private boolean bCheckOpera;
    private String strDate;
    private RadioButton rbFee0;
    private RadioButton rbFee1;
    private RadioButton rbFeeAll;
    private TextView tvConcert;
    private TextView tvOpera;
    private TextView tvDrama;
    private TextView tvArt;
    private TextView tvFestival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mContext = this.getApplicationContext();
        filterData = new FilterDataType();

        Toolbar toolbar = (Toolbar)findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("필터");

        rbFee0 = (RadioButton)findViewById(R.id.filter_rb_fee_0);
        rbFee1 = (RadioButton)findViewById(R.id.filter_rb_fee_1);
        rbFeeAll = (RadioButton)findViewById(R.id.filter_rb_fee_all);
        tvConcert = (TextView)findViewById(R.id.filter_tv_concert);
        tvOpera = (TextView)findViewById(R.id.filter_tv_opera);
        tvDrama = (TextView)findViewById(R.id.filter_tv_drama);
        tvArt = (TextView)findViewById(R.id.filter_tv_art);
        tvFestival = (TextView)findViewById(R.id.filter_tv_festival);

    }


    /* Filter toolbar*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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

            case R.id.action_filter:
                AdaptFilter();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }

    public void ClickCategory(View v)
    {
        int resId = v.getId();
        switch(resId)
        {
            case R.id.filter_tv_concert:
                if(bCheckConcert)
                {
                    tvConcert.setSelected(false);
                    bCheckConcert = false;
                }
                else
                {
                    tvConcert.setSelected(true);
                    bCheckConcert = true;
                }
                break;
            case R.id.filter_tv_opera:
                if(bCheckOpera)
                {
                    tvOpera.setSelected(false);
                    bCheckOpera = false;
                }
                else
                {
                    tvOpera.setSelected(true);
                    bCheckOpera = true;
                }
                break;
            case R.id.filter_tv_drama:
                if(bCheckDrama)
                {
                    tvDrama.setSelected(false);
                    bCheckDrama = false;
                }
                else
                {
                    tvDrama.setSelected(true);
                    bCheckDrama = true;
                }
                break;
            case R.id.filter_tv_art:
                if(bCheckArt)
                {
                    tvArt.setSelected(false);
                    bCheckArt = false;
                }
                else
                {
                    tvArt.setSelected(true);
                    bCheckArt = true;
                }
                break;
            case R.id.filter_tv_festival:
                if(bCheckFestival)
                {
                    tvFestival.setSelected(false);
                    bCheckFestival = false;
                }
                else
                {
                    tvFestival.setSelected(true);
                    bCheckFestival = true;
                }
                break;
        }
    }

    //  필터 적용
    public void AdaptFilter()
    {
        //  요금 필터
        if(rbFee0.isChecked())
        {
            filterData.setiIsFee(0);
        }
        else if(rbFee1.isChecked())
        {
            filterData.setiIsFee(1);
        }
        else if(rbFeeAll.isChecked())
        {
            filterData.setiIsFee(2);
        }

        //  카테고리 필터
        filterData.setbCheckArt(bCheckArt);
        filterData.setbCheckConcert(bCheckConcert);
        filterData.setbCheckDrama(bCheckDrama);
        filterData.setbCheckFestival(bCheckFestival);
        filterData.setbCheckOpera(bCheckOpera);

        //  날짜 필터 구현해야함

        //  Intent에 Filter정보 담아서 Main 호출.
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("Filter", filterData);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
