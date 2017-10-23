package com.jstech.fairy.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.DataType.DiaryDataType;
import com.jstech.fairy.DiaryDetail;
import com.jstech.fairy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SONY on 2017-10-12.
 */

public class DiaryFragmentRecyclerViewAdapter  extends RecyclerView.Adapter<DiaryFragmentRecyclerViewAdapter.ViewHolder>{

    Context mContext;
    ArrayList<DiaryDataType> aListDiary;
    String[] arrDay = {"일", "월", "화", "수", "목", "금", "토"};

    public DiaryFragmentRecyclerViewAdapter(Context context, ArrayList<DiaryDataType> aListDiary) {
        this.mContext = context;
        this.aListDiary = aListDiary;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_diary,null);
        return new DiaryFragmentRecyclerViewAdapter.ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos = position;

        //  이미지는 경로를 이용해서 받아오셈!
        //  우선은 텍스트만
        int iWeek = GetDayOfWeek(aListDiary.get(pos).getStrDate());
        holder.tvDate.setText(aListDiary.get(pos).getStrDate() + " (" + arrDay[iWeek] + ")");
        holder.tvTitle.setText(aListDiary.get(pos).getStrTitle());
        holder.ivImg.setImageBitmap(BitmapFactory.decodeFile(aListDiary.get(pos).getStrImgPath()));


        //  카드뷰 클릭 이벤트
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  세부 보여주기 액티비티로 옮길 데이터 복사
                DiaryDataType diaryData = new DiaryDataType();
                diaryData.CopyData(aListDiary.get(pos));
                Intent intent = new Intent(mContext.getApplicationContext(), DiaryDetail.class);
                intent.putExtra("DiaryData", diaryData);
                mContext.startActivity(intent);

            }
        });

    }

    //  요일 구하기
    public int GetDayOfWeek(String strDate)
    {
        String day = "" ;

        Date nDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
            nDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
        return dayNum - 1;
    }

    @Override
    public int getItemCount() {
        return aListDiary.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardview;
        ImageView ivImg;
        TextView tvTitle;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView)itemView.findViewById(R.id.diary_cardview);
            ivImg = (ImageView)itemView.findViewById(R.id.diary_cardview_imageview);
            tvTitle = (TextView)itemView.findViewById(R.id.diary_cardview_title);
            tvDate = (TextView)itemView.findViewById(R.id.diary_cardview_date);
        }
    }
}
