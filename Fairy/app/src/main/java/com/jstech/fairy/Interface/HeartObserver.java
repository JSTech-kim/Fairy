package com.jstech.fairy.Interface;

/**
 * Created by SONY on 2017-10-13.
 */

public interface HeartObserver {
    public void DataUpdate();
    public void ChangeHeartData(boolean bPushHeart, String strCultCode);    //  변경된 좋아요 정보.
    public void SetDiaryOrdered(boolean bAsc);    //  다이어리 정렬하기
}
