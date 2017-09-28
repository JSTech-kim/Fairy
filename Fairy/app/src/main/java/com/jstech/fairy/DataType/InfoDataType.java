package com.jstech.fairy.DataType;

/**
 * Created by SONY on 2017-09-28.
 */
/*
*       문화 행사 API로부터 받아와서 사용할 데이터 리스트
*       뒤에 * 붙은건, Main 화면에서 보여질 정보.
*/
public class InfoDataType {
    private String strCultCode;     //  문화 행사 코드
    private String strSubjCode;     //  장르 분류 코드
    private String strCodeName;     //  장르명
    private String strTitle;        //  제목*
    private String strStartDate;    //  시작일*
    private String strEndDate;      //  종료일*
    private String strTime;         //  시간
    private String strPlace;        //  장소*
    private String strOrgLink;      //  원문 링크주소
    private String strMainImg;      //  대표 이미지*
    private String strUseTarget;    //  이용 대상
    private String strUseFee;       //  이용 요금*
    private String strSponsor;      //  주최
    private String strInquiry;      //  문의
    private String strIsFree;       //  무료 구분
    private String strTicket;       //  할인 티켓 예매정보
    private String strContents;     //  본문

    public InfoDataType()
    {

    }

    public InfoDataType(String strCultCode, String strSubjCode, String strCodeName, String strTitle, String strStartDate, String strEndDate,
                        String strTime, String strPlace, String strOrgLink, String strMainImg, String strUseTarget, String strUseFee,
                        String strSponsor, String strInquiry, String strIsFree, String strTicket, String strContents)
    {
        this.strCultCode = strCultCode;
        this.strSubjCode = strSubjCode;
        this.strCodeName = strCodeName;
        this.strTitle = strTitle;
        this.strStartDate = strStartDate;
        this.strEndDate = strEndDate;
        this.strTime = strTime;
        this.strPlace = strPlace;
        this.strOrgLink = strOrgLink;
        this.strMainImg = strMainImg;
        this.strUseTarget = strUseTarget;
        this.strUseFee = strUseFee;
        this.strSponsor = strSponsor;
        this.strInquiry = strInquiry;
        this.strIsFree = strIsFree;
        this.strTicket = strTicket;
        this.strContents = strContents;
    }

    public String getStrCodeName() {
        return strCodeName;
    }

    public void setStrCodeName(String strCodeName) {
        this.strCodeName = strCodeName;
    }

    public String getStrContents() {
        return strContents;
    }

    public void setStrContents(String strContents) {
        this.strContents = strContents;
    }

    public String getStrCultCode() {
        return strCultCode;
    }

    public void setStrCultCode(String strCultCode) {
        this.strCultCode = strCultCode;
    }

    public String getStrEndDate() {
        return strEndDate;
    }

    public void setStrEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }

    public String getStrInquiry() {
        return strInquiry;
    }

    public void setStrInquiry(String strInquiry) {
        this.strInquiry = strInquiry;
    }

    public String getStrIsFree() {
        return strIsFree;
    }

    public void setStrIsFree(String strIsFree) {
        this.strIsFree = strIsFree;
    }

    public String getStrMainImg() {
        return strMainImg;
    }

    public void setStrMainImg(String strMainImg) {
        this.strMainImg = strMainImg;
    }

    public String getStrOrgLink() {
        return strOrgLink;
    }

    public void setStrOrgLink(String strOrgLink) {
        this.strOrgLink = strOrgLink;
    }

    public String getStrPlace() {
        return strPlace;
    }

    public void setStrPlace(String strPlace) {
        this.strPlace = strPlace;
    }

    public String getStrSponsor() {
        return strSponsor;
    }

    public void setStrSponsor(String strSponsor) {
        this.strSponsor = strSponsor;
    }

    public String getStrStartDate() {
        return strStartDate;
    }

    public void setStrStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }

    public String getStrSubjCode() {
        return strSubjCode;
    }

    public void setStrSubjCode(String strSubjCode) {
        this.strSubjCode = strSubjCode;
    }

    public String getStrTicket() {
        return strTicket;
    }

    public void setStrTicket(String strTicket) {
        this.strTicket = strTicket;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrUseFee() {
        return strUseFee;
    }

    public void setStrUseFee(String strUseFee) {
        this.strUseFee = strUseFee;
    }

    public String getStrUseTarget() {
        return strUseTarget;
    }

    public void setStrUseTarget(String strUseTarget) {
        this.strUseTarget = strUseTarget;
    }
}
