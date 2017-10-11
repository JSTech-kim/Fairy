package com.jstech.fairy.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jstech.fairy.DataType.InfoDataType;
import com.jstech.fairy.InfoDetail;
import com.jstech.fairy.MoreFunction.PicassoTransformations;
import com.jstech.fairy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SONY on 2017-09-26.
 */

public class InfoFragmentRecyclerViewAdapter extends RecyclerView.Adapter<InfoFragmentRecyclerViewAdapter.ViewHolder>{

    Context mContext;
    ArrayList<InfoDataType> aListInfo;

    public InfoFragmentRecyclerViewAdapter(Context context, ArrayList<InfoDataType> aListInfo) {
        this.mContext = context;
        this.aListInfo = aListInfo;
    }

    @Override
    public InfoFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    /*
    *
    *   17/10/11
    *   좋아요 클릭 시, DB에 저장.
    *   취소 시, DB에서 제거.
    *   최초에 바인딩 할 때, DB 참조해야한다. 라고 생각하고있는 중임. 구현 ㄴㄴ.
    *
    * */
    @Override
    public void onBindViewHolder(InfoFragmentRecyclerViewAdapter.ViewHolder holder, int position) {

        final int pos = position;

        //  이미지 스트링이 없으면 비운다.
        if(aListInfo.get(pos).getStrMainImg().isEmpty())
        {
            Log.e("BindImg", "Failed");
            holder.ivImg.setVisibility(View.GONE);
        }
        else
        {
            //  Picasso 라이브러리를 통해 URL에 해당하는 이미지를 가져와 뷰에 넣는다.
            Log.e("BingImg", aListInfo.get(pos).getStrMainImg());
            Picasso.with(mContext).load(aListInfo.get(pos).getStrMainImg())
                    .placeholder(R.mipmap.ic_launcher)                              // 이미지 불러오는 동안 이미지
                    .transform(PicassoTransformations.resizeTransformation)           //  리사이즈
                    .error(R.drawable.test_jinsub)                                  // 다운로드 실패 시, 이미지
                    .fit()                                                            // 이미지뷰에 맞추기
                    .into(holder.ivImg);
        }

        //  카드뷰의 각 값 세팅
        holder.tvTitle.setText(aListInfo.get(pos).getStrTitle());
        holder.tvStartDate.setText(aListInfo.get(pos).getStrStartDate());
        holder.tvEndDate.setText(aListInfo.get(pos).getStrEndDate());
        holder.tvPlace.setText(aListInfo.get(pos).getStrPlace());
        holder.tvFee.setText(aListInfo.get(pos).getStrUseFee());

        //  카드뷰 클릭 이벤트
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  세부 보여주기 액티비티로 옮길 데이터 복사
                InfoDataType infoData = new InfoDataType();
                infoData.CopyData(aListInfo.get(pos));

                Intent intent = new Intent(mContext.getApplicationContext(), InfoDetail.class);
                intent.putExtra("InfoData", infoData);
                mContext.startActivity(intent);

            }
        });

        //  좋아요 버튼 클릭 이벤트
        holder.ivHeart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return aListInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardview;
        ImageView ivImg;
        TextView tvTitle;
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvPlace;
        TextView tvFee;
        ImageView ivHeart;          //  좋아요 버튼으로 사용할 ImageView.

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView)itemView.findViewById(R.id.info_cardview);
            ivImg = (ImageView)itemView.findViewById(R.id.info_cardview_imageview);
            tvTitle = (TextView)itemView.findViewById(R.id.info_cardview_title);
            tvStartDate = (TextView)itemView.findViewById(R.id.info_cardview_start_date);
            tvEndDate = (TextView)itemView.findViewById(R.id.info_cardview_end_date);
            tvPlace = (TextView)itemView.findViewById(R.id.info_cardview_place);
            tvFee = (TextView)itemView.findViewById(R.id.info_cardview_fee);

            ivHeart = (ImageView)itemView.findViewById(R.id.info_cardview_btn_heart);
        }
    }

}
