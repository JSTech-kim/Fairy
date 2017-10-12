package com.jstech.fairy.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jstech.fairy.R;

/**
 * Created by SONY on 2017-09-25.
 */
/*
*
*   17/09/25 일기장 정보를 뿌려주는 DiaryFragment
*   이곳에서 RecyclerView + CardView로 일기장 정보를 뿌려줘야한다.
* */

public class DiaryFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";   //  Position값 받아올 구분자
    public static final int POSITION_DIARY = 1;
    private int mPage;

    //  Constructor
    public DiaryFragment(){


    }

    //  Constructor
    @SuppressLint("ValidFragment")
    public DiaryFragment(int page) {
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
        if(mPage == POSITION_DIARY){
            view = inflater.inflate(R.layout.fragment_diary, container, false);


        }

        return view;
    }
}
