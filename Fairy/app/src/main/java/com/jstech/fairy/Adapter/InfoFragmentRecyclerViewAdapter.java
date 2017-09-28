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
import com.jstech.fairy.R;

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

        holder.textview.setText(aListInfo.get(position).getStrTitle());
        Log.e("text", aListInfo.get(position).getStrTitle());

    }

    @Override
    public int getItemCount() {
        return aListInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardview;
        ImageView imageview;
        TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView)itemView.findViewById(R.id.info_cardview);
            imageview = (ImageView)itemView.findViewById(R.id.info_cardview_imageview);
            textview = (TextView)itemView.findViewById(R.id.info_cardview_textview);
        }
    }

}
