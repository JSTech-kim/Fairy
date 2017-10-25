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

import com.jstech.fairy.Adapter.HeartFragmentRecyclerViewAdapter;
import com.jstech.fairy.DataType.InfoDataType;
import com.jstech.fairy.Interface.HeartObserver;
import com.jstech.fairy.MoreFunction.HeartAlarm;
import com.jstech.fairy.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SONY on 2017-09-25.
 */

public class HeartFragment extends Fragment implements HeartObserver{
    public static final String ARG_PAGE = "ARG_PAGE";   //  Position값 받아올 구분자
    public static final int POSITION_SETTING = 2;
    private int mPage;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    HeartFragmentRecyclerViewAdapter mAdapter;

    ArrayList<InfoDataType> aListHeart;

    HeartAlarm heartPublisher;
    HeartAlarm heartCancelPublisher;

    //  Constructor
    public HeartFragment(){

    }

    //  Constructor
    @SuppressLint("ValidFragment")
    public HeartFragment(int page, HeartAlarm heartPublisher, HeartAlarm heartCancelPublisher) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        this.setArguments(args);

        this.heartPublisher = heartPublisher;               //  Info에서 좋아요 한것을 알림받기 위함.
        heartPublisher.add(this);

        this.heartCancelPublisher = heartCancelPublisher;   //  좋아요 취소 시, Info에 알리기위함.
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
        if(mPage == POSITION_SETTING){
            view = inflater.inflate(R.layout.fragment_heart, container, false);
        }

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.heart_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //  DB로부터 데이터 가져와서 채우기.
        aListHeart = new ArrayList<InfoDataType>();
        GetDataFromDatabase();
        mAdapter = new HeartFragmentRecyclerViewAdapter(getActivity(), aListHeart, heartCancelPublisher);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }

    /*
            *
            *   17/10/12
            *   DataBase의 Heart Table의 모든 값을 불러와 리스트에 넣는다.
            *   이렇게 하면 List 탭에서 바뀐 정보가 최신화 안됨. 추후 구현.
            *
            * */
    public void GetDataFromDatabase()
    {
        try{
            SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase(getString(R.string.database_name), MODE_PRIVATE, null);
            String strQuery = "SELECT * FROM " + getString(R.string.heart_table_name);
            Log.e("Query", strQuery);
            Cursor cursor = ReadDB.rawQuery(strQuery, null);

            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do{
                        InfoDataType objHeart = new InfoDataType();
                        objHeart.setStrCultCode(cursor.getString(cursor.getColumnIndex("CULTCODE")));
                        objHeart.setStrSubjCode(cursor.getString(cursor.getColumnIndex("SUBJCODE")));
                        objHeart.setStrCodeName(cursor.getString(cursor.getColumnIndex("CODENAME")));
                        objHeart.setStrTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                        objHeart.setStrStartDate(cursor.getString(cursor.getColumnIndex("START_DATE")));
                        objHeart.setStrEndDate(cursor.getString(cursor.getColumnIndex("END_DATE")));
                        objHeart.setStrTime(cursor.getString(cursor.getColumnIndex("TIME")));
                        objHeart.setStrPlace(cursor.getString(cursor.getColumnIndex("PLACE")));
                        objHeart.setStrOrgLink(cursor.getString(cursor.getColumnIndex("ORG_LINK")));
                        objHeart.setStrMainImg(cursor.getString(cursor.getColumnIndex("MAIN_IMG")));
                        objHeart.setStrUseTarget(cursor.getString(cursor.getColumnIndex("USE_TARGET")));
                        objHeart.setStrUseFee(cursor.getString(cursor.getColumnIndex("USE_FEE")));
                        objHeart.setStrSponsor(cursor.getString(cursor.getColumnIndex("SPONSOR")));
                        objHeart.setStrInquiry(cursor.getString(cursor.getColumnIndex("INQUIRY")));
                        objHeart.setStrIsFree(cursor.getString(cursor.getColumnIndex("IS_FREE")));
                        objHeart.setStrTicket(cursor.getString(cursor.getColumnIndex("TICKET")));
                        objHeart.setStrContents(cursor.getString(cursor.getColumnIndex("CONTENTS")));
                        objHeart.setStrIsHeart("1");            //  테이블에 있는 값은 모두 좋아요가 눌러진 정보들.

                        aListHeart.add(objHeart);

                    }while(cursor.moveToNext());
                }
            }

            ReadDB.close();

        }catch(SQLiteException se)
        {
            se.printStackTrace();
        }

    }

    //  ViewPager가 Heart 가리키면 해당 콜백함수 호출.
    //  DB에서 데이터 새로 받아와서 Refresh 해줌.
    @Override
    public void DataUpdate() {
        try{
            aListHeart.clear();
            GetDataFromDatabase();
            mAdapter.UpdateItemList(aListHeart);
        }catch(Exception e)
        {
            Log.e("HeartFragment", "DataUpdate()");
        }
    }

    @Override
    public void ChangeHeartData(boolean bPushHeart, String strCultCode) {

    }

    @Override
    public void SetDiaryOrdered(boolean bAsc) {

    }
}
