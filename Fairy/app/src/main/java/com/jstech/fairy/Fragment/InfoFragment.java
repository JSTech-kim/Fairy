package com.jstech.fairy.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import com.jstech.fairy.DataType.InfoDataType;
import com.jstech.fairy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    JSONArray aJson = null;
    ArrayList<InfoDataType> aListInfo;

    String strTestUrl = "http://openapi.seoul.go.kr:8088/727046784e6568663130354363776d6c/json/SearchConcertDetailService/1/3";

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

        aListInfo = new ArrayList<InfoDataType>();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.info_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        GetInfoDataFromURL(strTestUrl);
        return view;
    }

    /*
    *
    *       JSON 파싱 메소드
    * */
    public void GetInfoDataFromURL(String strUrl)
    {
        class GetDataJSON extends AsyncTask<String, Void, String>
        {
            @Override
            protected String doInBackground(String... params) {
                //JSON 데이터 시트 받아온다.
                String uri = params[0];
                BufferedReader br = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String strJsonData;
                    while((strJsonData = br.readLine()) != null) {
                        sb.append(strJsonData+"\n");
                    }
                    return sb.toString().trim();
                }catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String strJson) {
                MakeInfoArrayList(strJson);
            }
        }

        GetDataJSON objGetDataJSON = new GetDataJSON();
        objGetDataJSON.execute(strUrl);
    }

 /*
  *
  *       JSON 파싱 메소드
  * */
    public void MakeInfoArrayList(String strJson)
    {
        Log.e("Json", strJson);
        try {
            JSONObject objJson = new JSONObject(strJson);
            JSONObject objData = objJson.getJSONObject("SearchConcertDetailService");
            aJson = objData.getJSONArray("row");
            for(int i = 0; i < aJson.length(); i++)
            {
                JSONObject jsonobject = aJson.getJSONObject(i);
                InfoDataType objInfo = new InfoDataType();
                objInfo.setStrCultCode(jsonobject.getString("CULTCODE"));
                objInfo.setStrSubjCode(jsonobject.getString("SUBJCODE"));
                objInfo.setStrCodeName(jsonobject.getString("CODENAME"));
                objInfo.setStrTitle(jsonobject.getString("TITLE"));
                objInfo.setStrStartDate(jsonobject.getString("STRTDATE"));
                objInfo.setStrEndDate(jsonobject.getString("END_DATE"));
                objInfo.setStrTime(jsonobject.getString("TIME"));
                objInfo.setStrPlace(jsonobject.getString("PLACE"));
                objInfo.setStrOrgLink(jsonobject.getString("ORG_LINK"));
                objInfo.setStrMainImg(jsonobject.getString("MAIN_IMG"));
                objInfo.setStrUseTarget(jsonobject.getString("USE_TRGT"));
                objInfo.setStrUseFee(jsonobject.getString("USE_FEE"));
                objInfo.setStrSponsor(jsonobject.getString("SPONSOR"));
                objInfo.setStrInquiry(jsonobject.getString("INQUIRY"));
                objInfo.setStrIsFree(jsonobject.getString("IS_FREE"));
                objInfo.setStrTicket(jsonobject.getString("TICKET"));
                objInfo.setStrContents(jsonobject.getString("CONTENTS"));

                aListInfo.add(objInfo);
            }

            InfoFragmentRecyclerViewAdapter adapter = new InfoFragmentRecyclerViewAdapter(getActivity(), aListInfo);
            Log.e("onCreate[noticeList]", "" + aListInfo.size());
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
