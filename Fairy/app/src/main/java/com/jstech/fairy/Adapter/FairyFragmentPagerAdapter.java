package com.jstech.fairy.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jstech.fairy.Fragment.DiaryFragment;
import com.jstech.fairy.Fragment.HeartFragment;
import com.jstech.fairy.Fragment.InfoFragment;
import com.jstech.fairy.MoreFunction.HeartAlarm;

/**
 * Created by SONY on 2017-09-25.
 */
/*
*
*   17/09/25 FragmentPagerAdapter
*   Fragment들을 생성.
* */

public class FairyFragmentPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 3;   //페이지 개수

<<<<<<< HEAD
    private String tabTtitles[] = new String[]{"List", "Diary", "♡"};   //  추후 이미지로 수정
=======
    private String tabTtitles[] = new String[]{"List", "Diary", "Heart"};   //  추후 이미지로 수정
    HeartAlarm heartPublisher;                  //  Heart Fragment에 Observer Pattern 추가하기 위함.
>>>>>>> 3358873... [17/10/13]

    public FairyFragmentPagerAdapter(FragmentManager fm, HeartAlarm heartPublisher) {
        super(fm);
        this.heartPublisher = heartPublisher;
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
    public CharSequence getPageTitle(int position) {
        return tabTtitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
