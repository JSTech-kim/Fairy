package com.jstech.fairy.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jstech.fairy.Adapter.InfoFragmentRecyclerViewAdapter;
import com.jstech.fairy.R;

import java.util.ArrayList;

/**
 * Created by SONY on 2017-09-25.
 */
/*
*
*   17/09/25 문화 행사 정보를 뿌려주는 InfoFragment
*   이곳에서 RecyclerView + CardView로 문화 행사 정보를 뿌려줘야한다.
* */
public class InfoFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";   //  Position값 받아올 구분자
    public static final int POSITION_INFO = 0;          //  Info Fragment Index
    private int mPage;                                      //  Page Index
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    ArrayList<String> arrayList;

    //  Constructor
    public InfoFragment(){

    }

    //  Constructor
    @SuppressLint("ValidFragment")
    public InfoFragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        this.setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        if(mPage == POSITION_INFO){
            view = inflater.inflate(R.layout.fragment_info, container, false);
        }

        arrayList = new ArrayList<String>();
        arrayList.add("HyeonJee");
        arrayList.add("JinGi");
        arrayList.add("JinSub");

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.info_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        InfoFragmentRecyclerViewAdapter adapter = new InfoFragmentRecyclerViewAdapter(getActivity(), arrayList);

        Log.e("onCreate[noticeList]", "" + arrayList.size());

        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}
