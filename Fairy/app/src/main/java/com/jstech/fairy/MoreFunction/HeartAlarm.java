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

    @Override
    public void notifyObserver() {
        for(HeartObserver observer : observers)
        {
            observer.DataUpdate();
        }
    }
}
