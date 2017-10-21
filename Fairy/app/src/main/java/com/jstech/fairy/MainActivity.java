package com.jstech.fairy;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jstech.fairy.Adapter.FairyFragmentPagerAdapter;
import com.jstech.fairy.DataType.FilterDataType;
import com.jstech.fairy.Fragment.Add_Diary.Write_Diary;
import com.jstech.fairy.MoreFunction.Filter;
import com.jstech.fairy.MoreFunction.HeartAlarm;
import com.jstech.fairy.Navigation.Navi_ContactUs;
import com.jstech.fairy.Navigation.Navi_Developers;
import com.jstech.fairy.Navigation.Navi_LicenseInfo;
import com.jstech.fairy.Navigation.Navi_Security.AppLockManager;
import com.jstech.fairy.Navigation.Navi_Security.Navi_Secuity;

public class MainActivity extends AppCompatActivity{
    final int PAGE_COUNT = 3;   //페이지 개수
    private DrawerLayout mDrawerLayout;
    private ViewPager viewpager;    // ViewPager에 Fragment 올려서 액티비티 구성.
    private FairyFragmentPagerAdapter mAdapter;
    private HeartAlarm heartPublisher;               //  Info에서 좋아요 누른것을 Heart 탭에 알리기 위함.
    private HeartAlarm heartCancelPublisher;        //  Heart에서 좋아요 취소한 것을 Info에 알리기 위함.
    private Context mContext;

    Menu mMenu;
    Toolbar toolbar;

    //  Selector
    private int tabImg[] = {R.drawable.tab_info_selector, R.drawable.tab_diary_selector, R.drawable.tab_heart_selector};

    //  Filter
    private FilterDataType filterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupPermission(); // 내부저장소 권한 획득

        /**********************************************************************************************************/
        Application thisApp = (Application)getApplication(); // 어플 잠금 여부 확인 및 비밀번호 입력  by JinGi
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(thisApp);
        /**********************************************************************************************************/
        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mContext = this.getApplicationContext();
        filterData = new FilterDataType();

        //  Filter 데이터
        Intent intentFilter = getIntent();
        FilterDataType filterDataInput = intentFilter.getParcelableExtra("Filter");
        if(filterDataInput != null)
        {
            filterData.CopyData(filterDataInput);
        }

        //  Heart Observer Pattern을 위한 Publisher.
        heartPublisher = new HeartAlarm();
        heartCancelPublisher = new HeartAlarm();

        //  Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //  Navigation View 추가.
        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);

        //  들어가는 내용은 정상동작 여부를 위한 테스트용. 추후 수정 필요.
        final NavigationView navigationView = (NavigationView)findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent;

                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navi_btn_Secuity:
                        startActivity(new Intent(MainActivity.this, Navi_Secuity.class));
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
        mAdapter = new FairyFragmentPagerAdapter(getSupportFragmentManager(), heartPublisher, heartCancelPublisher, mContext, filterData);
        viewpager.setAdapter(mAdapter);

        //  Page 바꿀 때 이벤트 처리.
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //  여기서 Heart Fragment refresh 진행.
            @Override
            public void onPageSelected(int position) {

                if(position == 0){
                    toolbar.getMenu().clear();
                    setTitleChange("Festival");
                    getMenuInflater().inflate(R.menu.menu, mMenu);
                }
                else if(position == 1){
                    toolbar.getMenu().clear();
                    setTitleChange("Diary");
                    getMenuInflater().inflate(R.menu.menu_diary, mMenu);
                }
                else if(position == 2)
                {
                    heartPublisher.notifyObserver();
                    toolbar.getMenu().clear();
                    setTitleChange("Favorite");
                    getMenuInflater().inflate(R.menu.menu_heart, mMenu);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Tab 이미지 및 viewpager 등록.
        TabLayout tabLayout = (TabLayout)findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewpager);
        for(int i = 0; i < tabLayout.getTabCount(); i++)
        {
            tabLayout.getTabAt(i).setIcon(tabImg[i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        setTitleChange("Festival");

        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch(id)
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_filter:
                intent = new Intent(this, Filter.class);
                startActivity(intent);
                return true;

            case R.id.action_write:
                intent = new Intent(this, Write_Diary.class);
                startActivity(intent);
                return true;

            case R.id.action_sort:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitleChange(String title){
        getSupportActionBar().setTitle(title);
    }

    // 내부저장소 저장 권한 획득
    private void setupPermission() {
        //check for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }


}
