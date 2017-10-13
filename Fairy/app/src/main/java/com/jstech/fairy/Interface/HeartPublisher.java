package com.jstech.fairy.Interface;

/**
 * Created by SONY on 2017-10-13.
 */

public interface HeartPublisher {
    public void add(HeartObserver observer);
    public void notifyObserver();
}
