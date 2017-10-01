package com.jstech.fairy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.jstech.fairy.Adapter.FairyFragmentPagerAdapter;
import com.jstech.fairy.Navigation.Navi_ContactUs;
import com.jstech.fairy.Navigation.Navi_Developers;
import com.jstech.fairy.Navigation.Navi_LicenseInfo;
import com.jstech.fairy.Navigation.Navi_Secuity;

public class MainActivity extends AppCompatActivity {
    final int PAGE_COUNT = 3;   //페이지 개수
    private DrawerLayout mDrawerLayout;
    private ViewPager viewpager;    // ViewPager에 Fragment 올려서 액티비티 구성.

    /********** 진기***********/

    static int isSecure; // 앱의 잠금 설정 여부 , 1 = off & -1 = on

     /*************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        setContentView(R.layout.activity_main);

        /********** 진기*********** 어플이 종료되었다가 다시 켜졌을 때 앱의 잠금 상태를 저장한 것을 받아온다*/

        SharedPreferences SecureState = getSharedPreferences("State",0);
        isSecure = SecureState.getInt("SavedState",1);

        /*************************/

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //  Navigation View 추가.
        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);

        //  들어가는 내용은 정상동작 여부를 위한 테스트용. 추후 수정 필요.
        NavigationView navigationView = (NavigationView)findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent;

                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navi_btn_Secuity:
                        intent = new Intent(getApplicationContext(), Navi_Secuity.class);
                        intent.putExtra("isSecure",isSecure); // 현재 암호잠금 여부를 "isSecure" Key값으로 전달한다.
                        startActivityForResult(intent,0);
                        break;
                    case R.id.navi_btn_ContactUs:
                        intent = new Intent(getApplicationContext(), Navi_ContactUs.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_btn_Developers:
                        intent = new Intent(getApplicationContext(), Navi_Developers.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_btn_LicenseInfo:
                        intent = new Intent(getApplicationContext(), Navi_LicenseInfo.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        //페이지 2개 미리 띄움. 페이지 이동 시 데이터 로드 때문.
        viewpager = (ViewPager)findViewById(R.id.main_viewpager);
        viewpager.setOffscreenPageLimit(PAGE_COUNT);
        viewpager.setAdapter(new FairyFragmentPagerAdapter(getSupportFragmentManager()));

        //ViewPager를 Tab Strip에 연결
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)findViewById(R.id.main_tabs);
        tabsStrip.setViewPager(viewpager);

    }



    /*************************************************진기 *****************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 어플 잠금 스위치 정보 받아와서 반영하기
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){ // 변화가 있을 경우
            isSecure = data.getIntExtra("IsChanged",1);
            if(isSecure == -1)
                Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),"OFF",Toast.LENGTH_LONG).show();
        }
    }

    protected  void onStop(){  //어플이 종료될때 앱의 잠금 여부를 저장한다.
        super.onStop();
        SharedPreferences SecureState = getSharedPreferences("State",0);
        SharedPreferences.Editor editor = SecureState.edit();
        editor.putInt("SavedState",isSecure);
        editor.commit();
    }
    /**************************************************************************************************************/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
