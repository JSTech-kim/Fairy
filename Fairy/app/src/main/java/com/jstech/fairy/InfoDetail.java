package com.jstech.fairy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jstech.fairy.DataType.InfoDataType;
import com.squareup.picasso.Picasso;

import static com.jstech.fairy.R.attr.title;

public class InfoDetail extends AppCompatActivity {
    public  static boolean comeback=false;

    InfoDataType mInfoData;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        Intent intent = getIntent();
        mInfoData = intent.getParcelableExtra("InfoData");
        mContext = this.getApplicationContext();

        Toolbar toolbar = (Toolbar)findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitleChange("");

        //  infoData안에 해당 카드뷰 클릭했을 때, 자세한 정보가 모두 들어있으니 꺼내서 배치하면 됨.
        //   To. 현지
        ImageView mainIv = (ImageView)findViewById(R.id.main_iv);

        mainIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PoPupImage.class);
                intent.putExtra("Uri",mInfoData.getStrMainImg());
                startActivity(intent);
            }
        });


        Picasso.with(getApplicationContext()).load(mInfoData.getStrMainImg())
                .placeholder(R.drawable.loading_image)                              // 이미지 불러오는 동안 이미지
                .error(R.drawable.no_image2)                                  // 다운로드 실패 시, 이미지
                .fit()                                                            // 이미지뷰에 맞추기
                .into(mainIv);

        TextView titleTv = (TextView)findViewById(R.id.info_detail_title);
        titleTv.setText(mInfoData.getStrTitle());

        TextView dateTv = (TextView)findViewById(R.id.info_detail_date);
        dateTv.setText(mInfoData.getStrStartDate() + " ~ " + mInfoData.getStrEndDate());

        TextView timeTv = (TextView)findViewById(R.id.info_detail_time);
        timeTv.setText(mInfoData.getStrTime());

        TextView placeTv = (TextView)findViewById(R.id.info_detail_place);
        placeTv.setText(mInfoData.getStrPlace());

        TextView targetTv = (TextView)findViewById(R.id.info_detail_target);
        targetTv.setText(mInfoData.getStrUseTarget());

        TextView feeTv = (TextView)findViewById(R.id.info_detail_fee);

        if("1".equals(mInfoData.getStrIsFree())) //무료일 경우
        {
            feeTv.setText("무료");
        }
        else // 유료일 경우
        {
            feeTv.setText(mInfoData.getStrUseFee());
        }

        TextView hostTv = (TextView)findViewById(R.id.info_detail_host);
        hostTv.setText(mInfoData.getStrSponsor());

        TextView inquiryTv = (TextView)findViewById(R.id.info_detail_inquiry);
        inquiryTv.setText(mInfoData.getStrInquiry());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        setTitleChange("상세정보");
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

            case R.id.action_share:
                ActionForClickShare();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }

    //  공유하기 클릭이벤트.
    public void ActionForClickShare()
    {
        comeback = true;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, mInfoData.getStrOrgLink());

        Intent chooser = Intent.createChooser(intent, "공유");
        startActivity(chooser);

    }

    //  상세보기 페이지에서 사이트 바로가기 눌렀을 때.
    public void ClickFabSite(View v)
    {
        comeback = true;
        String strUrl = mInfoData.getStrOrgLink();

        if(strUrl == null || strUrl.length() <= 0)
        {
            Toast.makeText(mContext, "연결할 수 있는 인터넷 주소가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
        startActivity(intent);
    }

    //  상세보기 페이지에서 전화하기 눌렀을 때.
    public void ClickFabCall(View v)
    {
        comeback = true;
        String strInquiry = mInfoData.getStrInquiry();
        int iHyphen = 0;
        String strNum = "";

        if(strInquiry == null || strInquiry.length() <= 0)
        {
            Toast.makeText(mContext, "연결할 수 있는 전화번호가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        strInquiry = strInquiry.replace(')', '-');
        for(int i = 0; i < strInquiry.length(); i++)
        {
            if(strInquiry.charAt(i) == '-')
            {
                iHyphen++;
            }

            if(strInquiry.charAt(i) == '~')
            {
                break;
            }

            if(strInquiry.charAt(i) >= '0' && strInquiry.charAt(i) <= '9')
            {
                strNum += strInquiry.charAt(i);
            }
        }

        String strDialNumber = "tel:";
        if(iHyphen == 1)
        {
            if(strInquiry.contains("1544"))
            {
                strDialNumber += strNum;
            }
            else
            {
                strDialNumber += "02";
                strDialNumber += strNum;
            }
        }
        else if(iHyphen == 2)
        {
            strDialNumber += strNum;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(strDialNumber));
        startActivity(intent);
    }
}