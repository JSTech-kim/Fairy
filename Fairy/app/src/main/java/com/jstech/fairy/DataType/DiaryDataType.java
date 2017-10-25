package com.jstech.fairy.DataType;

import android.os.Parcel;
import android.os.Parcelable;


public class DiaryDataType implements Parcelable{

    private String strDate;
    private String strTitle;
    private String strMainText;
    private String strImgPath; // 대표 이미지
    private String strImgName;


    public DiaryDataType()
    {

    }

    public DiaryDataType(String strDate, String strTitle, String strMainText, String strImgPath, String strImgName)
    {
        this.strDate = strDate;
        this.strTitle = strTitle;
        this.strMainText = strMainText;
        this.strImgPath = strImgPath;
        this.strImgName = strImgName;
    }

    protected DiaryDataType(Parcel in) {
        strDate = in.readString();
        strTitle = in.readString();
        strMainText = in.readString();
        strImgPath = in.readString();
        strImgName = in.readString();
    }

    public void CopyData(DiaryDataType diarydatatype)
    {
        this.strDate = diarydatatype.getStrDate();
        this.strTitle = diarydatatype.getStrTitle();
        this.strMainText = diarydatatype.getStrMainText();
        this.strImgPath = diarydatatype.getStrImgPath();
        this.strImgName = diarydatatype.getstrImgName();
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

    public String getstrImgName() {
        return strImgName;
    }

    public void setstrImgName(String strImgName) {
        this.strImgName = strImgName;
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
        dest.writeString(strImgName);
    }

}
