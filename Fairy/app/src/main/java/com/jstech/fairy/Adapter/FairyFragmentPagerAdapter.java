package com.jstech.fairy.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jstech.fairy.DataType.FilterDataType;
import com.jstech.fairy.Fragment.DiaryFragment;
import com.jstech.fairy.Fragment.HeartFragment;
import com.jstech.fairy.Fragment.InfoFragment;
import com.jstech.fairy.MoreFunction.HeartAlarm;


public class FairyFragmentPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 3;   //페이지 개수
    HeartAlarm heartPublisher;                  //  Heart Fragment에 Observer Pattern 추가하기 위함.
    HeartAlarm heartCancelPublisher;                  //  Info Fragment에 Observer Pattern 추가하기 위함.
    HeartAlarm DiaryOrderPublisher;             //  다이어리 정렬 알림
    Context mContext;
    FilterDataType filterData;

    public FairyFragmentPagerAdapter(FragmentManager fm, HeartAlarm heartPublisher, HeartAlarm heartCancelPublisher, HeartAlarm DiaryOrderPublisher, Context context, FilterDataType filterData) {
        super(fm);
        this.heartPublisher = heartPublisher;
        this.heartCancelPublisher = heartCancelPublisher;
        this.DiaryOrderPublisher = DiaryOrderPublisher;
        this.mContext = context;
        this.filterData = filterData;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new InfoFragment(position, heartCancelPublisher, filterData);
        }
        else if(position == 1){
            return new DiaryFragment(position, DiaryOrderPublisher);
        }
        else{
            return new HeartFragment(position, heartPublisher, heartCancelPublisher);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

}
