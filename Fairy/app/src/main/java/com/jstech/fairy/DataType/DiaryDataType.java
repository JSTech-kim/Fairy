package com.jstech.fairy.DataType;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SONY on 2017-10-12.
 */

public class DiaryDataType implements Parcelable{

    private String strDate;
    private String strTitle;
    private String strMainText;
    private String strImgPath;

    public DiaryDataType()
    {

    }

    public DiaryDataType(String strDate, String strTitle, String strMainText, String strImgPath)
    {
        this.strDate = strDate;
        this.strTitle = strTitle;
        this.strMainText = strMainText;
        this.strImgPath = strImgPath;
    }

    protected DiaryDataType(Parcel in) {
        strDate = in.readString();
        strTitle = in.readString();
        strMainText = in.readString();
        strImgPath = in.readString();
    }

    public void CopyData(DiaryDataType diarydatatype)
    {
        this.strDate = diarydatatype.getStrDate();
        this.strTitle = diarydatatype.getStrTitle();
        this.strMainText = diarydatatype.getStrMainText();
        this.strImgPath = diarydatatype.getStrImgPath();
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrImgPath() {
        return strImgPath;
    }

    public void setStrImgPath(String strImgPath) {
        this.strImgPath = strImgPath;
    }

    public String getStrMainText() {
        return strMainText;
    }

    public void setStrMainText(String strMainText) {
        this.strMainText = strMainText;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public static final Creator<DiaryDataType> CREATOR = new Creator<DiaryDataType>(){

        @Override
        public DiaryDataType createFromParcel(Parcel source) {
            return new DiaryDataType(source);
        }

        @Override
        public DiaryDataType[] newArray(int size) {
            return new DiaryDataType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strDate);
        dest.writeString(strTitle);
        dest.writeString(strMainText);
        dest.writeString(strImgPath);
    }

}
