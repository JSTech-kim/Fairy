package com.jstech.fairy.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SONY on 2017-09-25.
 */
/*
*
*   17/09/25 문화 행사 정보를 뿌려주는 InfoFragment
*   이곳에서 RecyclerView + CardView로 문화 행사 정보를 뿌려줘야한다.
*
*   17/10/10 동적으로 데이터 갯수에 따라 전체 데이터 다운로드 가능.
*   1페이지 데이터 시트 다운로드 -> list_total_count 값 추출 -> 1~Count의 데이터 시트 다운로드 -> 리스트 생성
*
* */
public class InfoFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";   //  Position값 받아올 구분자
    public static final int POSITION_INFO = 0;          //  Info Fragment Index
    private int mPage;                                      //  Page Index
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    JSONArray aJson = null;
    ArrayList<InfoDataType> aListInfo;

    String mStrDefaultURL = "http://openapi.seoul.go.kr:8088/727046784e6568663130354363776d6c/json/SearchConcertDetailService/1/";

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

        /*
        *       /1/1 페이지로부터 전체 인덱스 개수 구한 뒤, 전체 데이터에 대해 요청한다.
        *       위 작업을 모두 수행하는 GetInfoDataFromURL 함수.
        * */
        GetInfoDataFromURL();

        return view;
    }

    /*
    *       첫번째 페이지만 받아와서 전체 Count를 알아낸 뒤,
    *       다시 전체에 대한 Request를 보내서 데이터를 가져온다.
    * */
    public void GetInfoDataFromURL()
    {
        String strFirstURL = mStrDefaultURL + "1";
        Log.e("strFirstURL", strFirstURL);

        GetTotalRequest objGetTotalCount = new GetTotalRequest();
        objGetTotalCount.execute(strFirstURL);
    }

    /*
    *
    *       전체 데이터를 받아와서 리스트에 삽입한다.
    * */
    public class GetDataJSON extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressdialog = null;

        //  ProgressDialog Setting
        public GetDataJSON()
        {
            progressdialog = new ProgressDialog(getActivity());
            progressdialog.setTitle("");
            progressdialog.setMessage("잠시만 기다려주세요.");
            progressdialog.setCancelable(false);
        }

        //  ProgressDialog Show
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressdialog != null)
            {
                progressdialog.show();
            }
        }

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
                    sb.append(strJsonData + "\n");
                }
                return sb.toString().trim();
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String strJson)
        {
            MakeInfoArrayList(strJson);
            if(progressdialog != null)
            {
                progressdialog.dismiss();
            }
        }
    }


    /*
    *       첫번째 페이지만 다운로드하는 부분.
    * */
    public class GetTotalRequest extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressdialog = null;

        public GetTotalRequest()
        {
            progressdialog = new ProgressDialog(getActivity());
            progressdialog.setTitle("");
            progressdialog.setMessage("잠시만 기다려주세요.");
            progressdialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressdialog != null)
            {
                progressdialog.show();
            }
        }

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
                    sb.append(strJsonData + "\n");
                }
                return sb.toString().trim();
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String strJson)
        {
            GetTotalCountAndRunRequest(strJson);
            if(progressdialog != null)
            {
                progressdialog.dismiss();
            }
        }
    }

    /*
     *
     *       전체 Data 개수를 추출해서 다시 Request를 보내 데이터를 받아온다.
     * */
    public void GetTotalCountAndRunRequest(String strJson)
    {
        try {
            JSONObject objJson = new JSONObject(strJson);
            JSONObject objData = objJson.getJSONObject("SearchConcertDetailService");
            int nCount = objData.getInt("list_total_count");
            Log.e("Count", Integer.toString(nCount));

            String strRequestURL = mStrDefaultURL+Integer.toString(nCount);
            Log.e("strRequestURL", strRequestURL);

            GetDataJSON objGetData = new GetDataJSON();
            objGetData.execute(strRequestURL);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
     *
     *       JSON 파싱 메소드
     *       이곳에서 리스트에 데이터를 삽입한다.
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

                //  대소문자 가리는 URL 때문에 도메인 소문자로 변경.
                String strImgUrl = jsonobject.getString("MAIN_IMG");
                int iCursor = strImgUrl.indexOf(":");               //  http:// 의 :를 찾기 위함
                iCursor += 3;

                //  도메인 부분만 잘라서 소문자화한 뒤, 다시 합쳐서 저장.
                String strDomain = "";
                strDomain += strImgUrl.substring(0, iCursor);
                strImgUrl = strImgUrl.substring(iCursor, strImgUrl.length());
                iCursor = strImgUrl.indexOf("/");
                strDomain += strImgUrl.substring(0, iCursor);
                strDomain = strDomain.toLowerCase();
                String strUrl = strDomain + "/" +strImgUrl.substring(iCursor+1);

                objInfo.setStrMainImg(strUrl);

                objInfo.setStrUseTarget(jsonobject.getString("USE_TRGT"));
                objInfo.setStrUseFee(jsonobject.getString("USE_FEE"));
                objInfo.setStrSponsor(jsonobject.getString("SPONSOR"));
                objInfo.setStrInquiry(jsonobject.getString("INQUIRY"));
                objInfo.setStrIsFree(jsonobject.getString("IS_FREE"));
                objInfo.setStrTicket(jsonobject.getString("TICKET"));
                objInfo.setStrContents(jsonobject.getString("CONTENTS"));

                //  좋아요 눌러져 있는지 여부 확인.
                if(IsHeartData(jsonobject.getString("CULTCODE")) == true)
                {
                    objInfo.setStrIsHeart("1");
                }
                else
                {
                    objInfo.setStrIsHeart("0");
                }

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

    /*
    *
    *   Database의 Table을 CultCode 값을 통해 탐색해 좋아요 버튼이 눌려져 있는지 여부 판단.
    *   만일 눌러져 있으면 true, 아니면 false 를 리턴.
    *   해당 테이블에 들어있으면 좋아요가 눌려진것임.
    *
    * */
    public boolean IsHeartData(String strCultCode)
    {
        boolean bRet = false;

        try{
            SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase(getString(R.string.database_name), MODE_PRIVATE, null);
            String strQuery = "SELECT * FROM " + getString(R.string.heart_table_name) + " WHERE CULTCODE = " + strCultCode;
            Cursor cursor = ReadDB.rawQuery(strQuery, null);

            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do{
                        bRet = true;
                    }while(cursor.moveToNext());
                }
            }

            ReadDB.close();

        }catch(SQLiteException se)
        {
            se.printStackTrace();
        }

        return bRet;
    }

}
