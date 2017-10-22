package com.jstech.fairy.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jstech.fairy.Adapter.DiaryFragmentRecyclerViewAdapter;
import com.jstech.fairy.DataType.DiaryDataType;
import com.jstech.fairy.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    DiaryFragmentRecyclerViewAdapter mAdapter;
    ArrayList<DiaryDataType> aListDiary;

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

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.diary_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //  DB로부터 데이터 가져와서 채우기.
        aListDiary = new ArrayList<DiaryDataType>();
        GetDiaryDataFromDatabase();

        mAdapter = new DiaryFragmentRecyclerViewAdapter(getActivity(), aListDiary);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //  Floating Button 연결.
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.diary_fab);
        fab.attachToRecyclerView(mRecyclerView);

        //  Floating Button 클릭 리스너
        //  클릭시 맨 위로
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

        return view;
    }

    //  DB의 Diary Table로부터 데이터 읽어와서 리스트 채운다.
    public void GetDiaryDataFromDatabase()
    {
        try{
            SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase(getString(R.string.database_name), MODE_PRIVATE, null);
            String strQuery = "SELECT * FROM " + getString(R.string.diary_table_name);
            Log.e("Query", strQuery);
            Cursor cursor = ReadDB.rawQuery(strQuery, null);

            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do{
                        DiaryDataType objDiary = new DiaryDataType();
                        objDiary.setStrDate(cursor.getString(cursor.getColumnIndex("DATE")));
                        objDiary.setStrTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                        objDiary.setStrMainText(cursor.getString(cursor.getColumnIndex("MAINTEXT")));
                        objDiary.setStrImgPath(cursor.getString(cursor.getColumnIndex("IMGPATH")));
                        objDiary.setstrImgName(cursor.getString(cursor.getColumnIndex("IMGNAME")));
                        aListDiary.add(objDiary);
                    }while(cursor.moveToNext());
                }
            }

            ReadDB.close();

        }catch(SQLiteException se)
        {
            se.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        aListDiary = new ArrayList<DiaryDataType>();
        GetDiaryDataFromDatabase();
        mAdapter = new DiaryFragmentRecyclerViewAdapter(getActivity(), aListDiary);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
