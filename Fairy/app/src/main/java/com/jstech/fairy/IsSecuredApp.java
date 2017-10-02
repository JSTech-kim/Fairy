package com.jstech.fairy;

import android.app.Application;

import org.wordpress.passcodelock.AppLockManager;

public class IsSecuredApp extends Application {
    @Override

    /*
    시작할때 이 엑티비티를 거쳐서 시작합니다.
    어플의 설정이 잠겨있는지 아닌지 확인하는 절차입니다.
     */
    public void onCreate() {
        super.onCreate();
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);
    }
}
