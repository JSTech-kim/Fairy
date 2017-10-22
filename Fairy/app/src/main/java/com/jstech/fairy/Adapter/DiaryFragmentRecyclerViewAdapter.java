package com.jstech.fairy.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.DataType.DiaryDataType;
import com.jstech.fairy.DiaryDetail;
import com.jstech.fairy.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SONY on 2017-10-12.
 */

public class DiaryFragmentRecyclerViewAdapter  extends RecyclerView.Adapter<DiaryFragmentRecyclerViewAdapter.ViewHolder>{

    Context mContext;
    ArrayList<DiaryDataType> aListDiary;
    SQLiteDatabase mSQLiteDatabase;


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
        holder.tvDate.setText(aListDiary.get(pos).getStrDate());
        holder.tvTitle.setText(aListDiary.get(pos).getStrTitle());

        Uri uri = Uri.fromFile(new File(aListDiary.get(pos).getStrImgPath())); // file:///storage/emulated/0/Pictures/Fairy20171021_162240.jpg
        Uri uri2 = Uri.parse(aListDiary.get(pos).getStrImgPath());//              /storage/emulated/0/Pictures/Fairy20171021_162240.jpg
        Log.d("gogogo",uri2.toString());
        //holder.ivImg.setImageURI(uri2);
        holder.ivImg.setImageBitmap(BitmapFactory.decodeFile(aListDiary.get(pos).getStrImgPath()));

        //  카드뷰 클릭 이벤트
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  세부 보여주기 액티비티로 옮길 데이터 복사
                DiaryDataType diaryData = new DiaryDataType();
                diaryData.CopyData(aListDiary.get(pos));

                //  일기 자세히 보기 액티비티로 옮겨야함. (이건 임시)
                Intent intent = new Intent(mContext.getApplicationContext(), DiaryDetail.class);
                intent.putExtra("DiaryData", diaryData);
                mContext.startActivity(intent);

            }
        });

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
