package com.jstech.fairy.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.jstech.fairy.Fragment.DiaryFragment;
import com.jstech.fairy.Fragment.HeartFragment;
import com.jstech.fairy.Fragment.InfoFragment;
import com.jstech.fairy.MoreFunction.HeartAlarm;
import com.jstech.fairy.R;

/**
 * Created by SONY on 2017-09-25.
 */
/*
*
*   17/09/25 FragmentPagerAdapter
*   Fragment들을 생성.
* */

public class FairyFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{
    final int PAGE_COUNT = 3;   //페이지 개수
    private int tabImg[] = {R.drawable.ic_flower, R.drawable.ic_book, R.drawable.ic_heart};
    HeartAlarm heartPublisher;                  //  Heart Fragment에 Observer Pattern 추가하기 위함.
    Context mContext;

    public FairyFragmentPagerAdapter(FragmentManager fm, HeartAlarm heartPublisher, Context context) {
        super(fm);
        this.heartPublisher = heartPublisher;
        this.mContext = context;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new InfoFragment(position);
        }
        else if(position == 1){
            return new DiaryFragment(position);
        }
        else{
            return new HeartFragment(position, heartPublisher);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //  Tab 제목 이미지
    @Override
    public int getPageIconResId(int position) {
        return tabImg[position];
    }
}
