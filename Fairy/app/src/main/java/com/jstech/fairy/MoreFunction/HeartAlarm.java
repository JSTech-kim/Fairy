package com.jstech.fairy.MoreFunction;

import com.jstech.fairy.Interface.HeartObserver;
import com.jstech.fairy.Interface.HeartPublisher;

import java.util.ArrayList;

/**
 * Created by SONY on 2017-10-13.
 */

public class HeartAlarm implements HeartPublisher{

    public ArrayList<HeartObserver> observers;

    public HeartAlarm()
    {
        observers = new ArrayList<HeartObserver>();
    }

    @Override
    public void add(HeartObserver observer) {
        observers.add(observer);
    }

    //  전체 데이터 업데이트를 지시.
    @Override
    public void notifyObserver() {
        for(HeartObserver observer : observers)
        {
            observer.DataUpdate();
        }
    }

    //  좋아요 버튼이 바꼈음을 알림.
    @Override
    public void notifyHeartChanged(boolean bPushHeart, String strCultCode) {
        for(HeartObserver observer : observers)
        {
            observer.ChangeHeartData(bPushHeart, strCultCode);
        }
    }

    //  다이어리 정렬하기 클릭 시 알림.
    @Override
    public void notifyDiaryOrdered(boolean bAsc) {
        for(HeartObserver observer : observers)
        {
            observer.SetDiaryOrdered(bAsc);
        }
    }


}
