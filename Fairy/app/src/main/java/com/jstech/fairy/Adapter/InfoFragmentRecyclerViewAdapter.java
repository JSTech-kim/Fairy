package com.jstech.fairy.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
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

    @Override
    public void onBindViewHolder(InfoFragmentRecyclerViewAdapter.ViewHolder holder, int position) {

        //  이미지 스트링이 없으면 비운다.
        if(aListInfo.get(position).getStrMainImg().isEmpty())
        {
            Log.e("BindImg", "Failed");
            holder.ivImg.setVisibility(View.GONE);
        }
        else
        {
            Log.e("BingImg", aListInfo.get(position).getStrMainImg());
            Picasso.with(mContext).load(aListInfo.get(position).getStrMainImg())
                    .placeholder(R.mipmap.ic_launcher)                              // 이미지 불러오는 동안 이미지
                    .transform(PicassoTransformations.resizeTransformation)           //  리사이즈
                    .error(R.drawable.test_jinsub)                                  // 다운로드 실패 시, 이미지
                    .fit()                                                            // 이미지뷰에 맞추기
                    .into(holder.ivImg);
        }

        holder.tvTitle.setText(aListInfo.get(position).getStrTitle());
        holder.tvStartDate.setText(aListInfo.get(position).getStrStartDate());
        holder.tvEndDate.setText(aListInfo.get(position).getStrEndDate());
        holder.tvPlace.setText(aListInfo.get(position).getStrPlace());
        holder.tvFee.setText(aListInfo.get(position).getStrUseFee());

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

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView)itemView.findViewById(R.id.info_cardview);
            ivImg = (ImageView)itemView.findViewById(R.id.info_cardview_imageview);
            tvTitle = (TextView)itemView.findViewById(R.id.info_cardview_title);
            tvStartDate = (TextView)itemView.findViewById(R.id.info_cardview_start_date);
            tvEndDate = (TextView)itemView.findViewById(R.id.info_cardview_end_date);
            tvPlace = (TextView)itemView.findViewById(R.id.info_cardview_place);
            tvFee = (TextView)itemView.findViewById(R.id.info_cardview_fee);
        }
    }

}
