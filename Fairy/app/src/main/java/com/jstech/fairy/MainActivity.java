package com.jstech.fairy;

import android.app.Application;
import android.content.Intent;
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

import com.astuetz.PagerSlidingTabStrip;
import com.jstech.fairy.Adapter.FairyFragmentPagerAdapter;
import com.jstech.fairy.Navigation.Navi_ContactUs;
import com.jstech.fairy.Navigation.Navi_Developers;
import com.jstech.fairy.Navigation.Navi_LicenseInfo;
import com.jstech.fairy.Navigation.Navi_Security.AppLockManager;
import com.jstech.fairy.Navigation.Navi_Security.Navi_Secuity;

public class MainActivity extends AppCompatActivity {
    final int PAGE_COUNT = 3;   //페이지 개수
    private DrawerLayout mDrawerLayout;
    private ViewPager viewpager;    // ViewPager에 Fragment 올려서 액티비티 구성.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application thisApp = (Application)getApplication();
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(thisApp);
        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        setContentView(R.layout.activity_main);
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
        viewpager.setAdapter(new FairyFragmentPagerAdapter(getSupportFragmentManager()));

        //ViewPager를 Tab Strip에 연결
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)findViewById(R.id.main_tabs);
        tabsStrip.setViewPager(viewpager);

    }

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
