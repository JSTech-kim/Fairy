package com.jstech.fairy.DataType;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SONY on 2017-10-20.
 */

public class FilterDataType implements Parcelable {

    private int iIsFee; // 0 - 무료, 1 - 유료, 2 - 전체
    private boolean bCheckArt;
    private boolean bCheckConcert;
    private boolean bCheckDrama;
    private boolean bCheckFestival;
    private boolean bCheckOpera;
    private String strDate;
    private String strSearch;   //검색어

    public FilterDataType()
    {
        iIsFee = 2;
        bCheckArt = true;
        bCheckConcert = true;
        bCheckDrama = true;
        bCheckFestival = true;
        bCheckOpera = true;
        strDate = "";
        strSearch = "";
    }

    public FilterDataType(int iIsFee, boolean bCheckArt, boolean bCheckConcert, boolean bCheckDrama, boolean bCheckFestival,
                          boolean bCheckOpera, String strDate, String strSearch)
    {
        this.iIsFee = iIsFee;
        this.bCheckArt = bCheckArt;
        this.bCheckConcert = bCheckConcert;
        this.bCheckDrama = bCheckDrama;
        this.bCheckFestival = bCheckFestival;
        this.bCheckOpera = bCheckOpera;
        this.strDate = strDate;
        this.strSearch = strSearch;
    }

    protected FilterDataType(Parcel in) {
        iIsFee = in.readInt();
        bCheckArt = in.readByte() != 0;
        bCheckConcert = in.readByte() != 0;
        bCheckDrama = in.readByte() != 0;
        bCheckFestival = in.readByte() != 0;
        bCheckOpera = in.readByte() != 0;
        strDate = in.readString();
        strSearch = in.readString();
    }

    public void CopyData(FilterDataType filterData)
    {
        this.iIsFee = filterData.getiIsFee();
        this.bCheckArt = filterData.isbCheckArt();
        this.bCheckConcert = filterData.isbCheckConcert();
        this.bCheckDrama = filterData.isbCheckDrama();
        this.bCheckFestival = filterData.isbCheckFestival();
        this.bCheckOpera = filterData.isbCheckOpera();
        this.strDate = filterData.getStrDate();
        this.strSearch = filterData.getStrSearch();
    }

    public static final Creator<FilterDataType> CREATOR = new Creator<FilterDataType>() {
        @Override
        public FilterDataType createFromParcel(Parcel in) {
            return new FilterDataType(in);
        }

        @Override
        public FilterDataType[] newArray(int size) {
            return new FilterDataType[size];
        }
    };

    public boolean isbCheckArt() {
        return bCheckArt;
    }

    public void setbCheckArt(boolean bCheckArt) {
        this.bCheckArt = bCheckArt;
    }

    public boolean isbCheckConcert() {
        return bCheckConcert;
    }

    public void setbCheckConcert(boolean bCheckConcert) {
        this.bCheckConcert = bCheckConcert;
    }

    public boolean isbCheckDrama() {
        return bCheckDrama;
    }

    public void setbCheckDrama(boolean bCheckDrama) {
        this.bCheckDrama = bCheckDrama;
    }

    public boolean isbCheckFestival() {
        return bCheckFestival;
    }

    public void setbCheckFestival(boolean bCheckFestival) {
        this.bCheckFestival = bCheckFestival;
    }

    public boolean isbCheckOpera() {
        return bCheckOpera;
    }

    public void setbCheckOpera(boolean bCheckOpera) {
        this.bCheckOpera = bCheckOpera;
    }

    public int getiIsFee() {
        return iIsFee;
    }

    public void setiIsFee(int iIsFee) {
        this.iIsFee = iIsFee;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrSearch() {
        return strSearch;
    }

    public void setStrSearch(String strSearch) {
        this.strSearch = strSearch;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iIsFee);
        dest.writeByte((byte) (bCheckArt ? 1 : 0));
        dest.writeByte((byte) (bCheckConcert ? 1 : 0));
        dest.writeByte((byte) (bCheckDrama ? 1 : 0));
        dest.writeByte((byte) (bCheckFestival ? 1 : 0));
        dest.writeByte((byte) (bCheckOpera ? 1 : 0));
        dest.writeString(strDate);
        dest.writeString(strSearch);
    }
}