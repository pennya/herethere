package com.herethere.www.Entity.tour;

import android.content.Context;

import com.herethere.www.R;
import com.herethere.www.common.TextViewWithIconCallback;
import com.herethere.www.common.textview.TextViewWithIcon;
import com.herethere.www.util.TextViewWithIconHelper;

import java.util.ArrayList;

/**
 * Created by KIM on 2017-10-20.
 */

public class AttractionDetail implements TourDetail{
    /*common*/
    private String resAddress;
    private String resAddress2;
    private String zipcode;
    private String firstimage;
    private String firstimage2;
    private double resLat;
    private double resLon;
    private int resAPIKind;
    private int contentid;
    private int contenttypeid;
    private long createdtime;
    private String homepage;
    private long modifiedtime;
    private String overview;
    private String resName;

    /*tour*/
    private Object accomcount;
    private Object chkbabycarriage;
    private Object chkcreditcard;
    private Object chkpet;
    private Object expagerange;
    private Object expguide;
    private Object heritage1;
    private Object heritage2;
    private Object heritage3;
    private Object infocenter;
    private Object opendate;
    private Object parking;
    private Object restdate;
    private Object useseason;
    private Object usetime;
    private Object accomcountculture;

    /*tmap restourant*/
    private Object id;
    private Object bizCatName;
    private Object firstNo;
    private Object secondNo;
    private Object zipCode;
    private Object tel;
    private Object mlClass;
    private Object menu1;
    private Object menu2;
    private Object menu3;
    private Object menu4;
    private Object menu5;
    private Object parkFlag;
    private Object twFlag;
    private Object yaFlag;
    private Object homepageURL;
    private Object routeInfo;
    private Object facility;
    private Object upperLegalCode;
    private Object middleLegalCode;
    private Object detailLegalCode;
    private Object lowerLegalCode;
    private Object upperAdminCode;
    private Object middleAdminCode;
    private Object lowerAdminCode;
    private Object upperCode;
    private Object middleColde;
    private Object lowerCode;
    private Object participant;
    private Object point;
    private Object merchaFlag;
    private Object merchantFlag;
    private Object merchantDispType;
    private Object additionalInfo;
    private Object desc;

    @Override
    public ArrayList<TextViewWithIcon> makeTextViewWithTourApi(Context context, TourDetail tourDetail, TourList tourList, TextViewWithIconCallback callback) {
        ArrayList<TextViewWithIcon> textLists = new ArrayList<>();

        textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_address, "주소", tourDetail.getResAddress(), callback));
        textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_call, "전화번호", tourList.getResTel(), callback));
        textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "우편번호", tourDetail.getZipcode(), callback));
        textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "설명", tourDetail.getOverview(), callback));

        AttractionDetail attractionDetail = (AttractionDetail)tourDetail;

        if(checkValidString(String.valueOf(attractionDetail.getOpendate())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_time, "개장일", String.valueOf(attractionDetail.getOpendate()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getAccomcount())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "수용인원", String.valueOf(attractionDetail.getAccomcount()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getChkbabycarriage())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "유모차 대여 여부", String.valueOf(attractionDetail.getChkbabycarriage()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getChkcreditcard())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "신용카드 가능 여부", String.valueOf(attractionDetail.getChkcreditcard()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getChkpet())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "애완동물 가능 여부", String.valueOf(attractionDetail.getChkpet()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getExpagerange())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "체험가능 연령", String.valueOf(attractionDetail.getExpagerange()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getExpguide())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "체험안내", String.valueOf(attractionDetail.getExpguide()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getHeritage1())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "세계 문화유산 유무", String.valueOf(attractionDetail.getHeritage1()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getHeritage2())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "세계 자연유산 유무", String.valueOf(attractionDetail.getHeritage2()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getHeritage3())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "세계 기록유산 유무", String.valueOf(attractionDetail.getHeritage3()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getInfocenter())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "문의 및 안내", String.valueOf(attractionDetail.getInfocenter()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getParking())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "주차시설", String.valueOf(attractionDetail.getParking()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getRestdate())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "쉬는날", String.valueOf(attractionDetail.getRestdate()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getUseseason())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "이용시기", String.valueOf(attractionDetail.getUseseason()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getUsetime())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "이용시간", String.valueOf(attractionDetail.getUsetime()), callback));

        if(checkValidString(String.valueOf(attractionDetail.getAccomcountculture())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "수용인원", String.valueOf(attractionDetail.getAccomcountculture()), callback));

        return textLists;
    }

    @Override
    public ArrayList<TextViewWithIcon> makeTextViewWithTMapApi(Context context, TourDetail tourDetail, TourList tourList, TextViewWithIconCallback callback) {
        return null;
    }

    @Override
    public String getResName() {
        return resName;
    }

    @Override
    public int getResAPIKind() {
        return resAPIKind;
    }

    @Override
    public String getResAddress() {
        return resAddress;
    }

    @Override
    public String getZipcode() {
        return zipcode;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public String getFirstimage() {
        return firstimage;
    }

    private boolean checkValidString(String str) {
        if(str == null || str.equals("") || str.equals("0.0"))
            return false;

        return true;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public String getResAddress2() {
        return resAddress2;
    }

    public void setResAddress2(String resAddress2) {
        this.resAddress2 = resAddress2;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    public double getResLat() {
        return resLat;
    }

    public void setResLat(double resLat) {
        this.resLat = resLat;
    }

    public double getResLon() {
        return resLon;
    }

    public void setResLon(double resLon) {
        this.resLon = resLon;
    }

    public void setResAPIKind(int resAPIKind) {
        this.resAPIKind = resAPIKind;
    }

    public int getContentid() {
        return contentid;
    }

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public int getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(int contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Object getAccomcount() {
        return accomcount;
    }

    public void setAccomcount(Object accomcount) {
        this.accomcount = accomcount;
    }

    public Object getChkbabycarriage() {
        return chkbabycarriage;
    }

    public void setChkbabycarriage(Object chkbabycarriage) {
        this.chkbabycarriage = chkbabycarriage;
    }

    public Object getChkcreditcard() {
        return chkcreditcard;
    }

    public void setChkcreditcard(Object chkcreditcard) {
        this.chkcreditcard = chkcreditcard;
    }

    public Object getChkpet() {
        return chkpet;
    }

    public void setChkpet(Object chkpet) {
        this.chkpet = chkpet;
    }

    public Object getExpagerange() {
        return expagerange;
    }

    public void setExpagerange(Object expagerange) {
        this.expagerange = expagerange;
    }

    public Object getExpguide() {
        return expguide;
    }

    public void setExpguide(Object expguide) {
        this.expguide = expguide;
    }

    public Object getHeritage1() {
        return heritage1;
    }

    public void setHeritage1(Object heritage1) {
        this.heritage1 = heritage1;
    }

    public Object getHeritage2() {
        return heritage2;
    }

    public void setHeritage2(Object heritage2) {
        this.heritage2 = heritage2;
    }

    public Object getHeritage3() {
        return heritage3;
    }

    public void setHeritage3(Object heritage3) {
        this.heritage3 = heritage3;
    }

    public Object getInfocenter() {
        return infocenter;
    }

    public void setInfocenter(Object infocenter) {
        this.infocenter = infocenter;
    }

    public Object getOpendate() {
        return opendate;
    }

    public void setOpendate(Object opendate) {
        this.opendate = opendate;
    }

    public Object getParking() {
        return parking;
    }

    public void setParking(Object parking) {
        this.parking = parking;
    }

    public Object getRestdate() {
        return restdate;
    }

    public void setRestdate(Object restdate) {
        this.restdate = restdate;
    }

    public Object getUseseason() {
        return useseason;
    }

    public void setUseseason(Object useseason) {
        this.useseason = useseason;
    }

    public Object getUsetime() {
        return usetime;
    }

    public void setUsetime(Object usetime) {
        this.usetime = usetime;
    }

    public Object getAccomcountculture() {
        return accomcountculture;
    }

    public void setAccomcountculture(Object accomcountculture) {
        this.accomcountculture = accomcountculture;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getBizCatName() {
        return bizCatName;
    }

    public void setBizCatName(Object bizCatName) {
        this.bizCatName = bizCatName;
    }

    public Object getFirstNo() {
        return firstNo;
    }

    public void setFirstNo(Object firstNo) {
        this.firstNo = firstNo;
    }

    public Object getSecondNo() {
        return secondNo;
    }

    public void setSecondNo(Object secondNo) {
        this.secondNo = secondNo;
    }

    public Object getZipCode() {
        return zipCode;
    }

    public void setZipCode(Object zipCode) {
        this.zipCode = zipCode;
    }

    public Object getTel() {
        return tel;
    }

    public void setTel(Object tel) {
        this.tel = tel;
    }

    public Object getMlClass() {
        return mlClass;
    }

    public void setMlClass(Object mlClass) {
        this.mlClass = mlClass;
    }

    public Object getMenu1() {
        return menu1;
    }

    public void setMenu1(Object menu1) {
        this.menu1 = menu1;
    }

    public Object getMenu2() {
        return menu2;
    }

    public void setMenu2(Object menu2) {
        this.menu2 = menu2;
    }

    public Object getMenu3() {
        return menu3;
    }

    public void setMenu3(Object menu3) {
        this.menu3 = menu3;
    }

    public Object getMenu4() {
        return menu4;
    }

    public void setMenu4(Object menu4) {
        this.menu4 = menu4;
    }

    public Object getMenu5() {
        return menu5;
    }

    public void setMenu5(Object menu5) {
        this.menu5 = menu5;
    }

    public Object getParkFlag() {
        return parkFlag;
    }

    public void setParkFlag(Object parkFlag) {
        this.parkFlag = parkFlag;
    }

    public Object getTwFlag() {
        return twFlag;
    }

    public void setTwFlag(Object twFlag) {
        this.twFlag = twFlag;
    }

    public Object getYaFlag() {
        return yaFlag;
    }

    public void setYaFlag(Object yaFlag) {
        this.yaFlag = yaFlag;
    }

    public Object getHomepageURL() {
        return homepageURL;
    }

    public void setHomepageURL(Object homepageURL) {
        this.homepageURL = homepageURL;
    }

    public Object getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(Object routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Object getFacility() {
        return facility;
    }

    public void setFacility(Object facility) {
        this.facility = facility;
    }

    public Object getUpperLegalCode() {
        return upperLegalCode;
    }

    public void setUpperLegalCode(Object upperLegalCode) {
        this.upperLegalCode = upperLegalCode;
    }

    public Object getMiddleLegalCode() {
        return middleLegalCode;
    }

    public void setMiddleLegalCode(Object middleLegalCode) {
        this.middleLegalCode = middleLegalCode;
    }

    public Object getDetailLegalCode() {
        return detailLegalCode;
    }

    public void setDetailLegalCode(Object detailLegalCode) {
        this.detailLegalCode = detailLegalCode;
    }

    public Object getLowerLegalCode() {
        return lowerLegalCode;
    }

    public void setLowerLegalCode(Object lowerLegalCode) {
        this.lowerLegalCode = lowerLegalCode;
    }

    public Object getUpperAdminCode() {
        return upperAdminCode;
    }

    public void setUpperAdminCode(Object upperAdminCode) {
        this.upperAdminCode = upperAdminCode;
    }

    public Object getMiddleAdminCode() {
        return middleAdminCode;
    }

    public void setMiddleAdminCode(Object middleAdminCode) {
        this.middleAdminCode = middleAdminCode;
    }

    public Object getLowerAdminCode() {
        return lowerAdminCode;
    }

    public void setLowerAdminCode(Object lowerAdminCode) {
        this.lowerAdminCode = lowerAdminCode;
    }

    public Object getUpperCode() {
        return upperCode;
    }

    public void setUpperCode(Object upperCode) {
        this.upperCode = upperCode;
    }

    public Object getMiddleColde() {
        return middleColde;
    }

    public void setMiddleColde(Object middleColde) {
        this.middleColde = middleColde;
    }

    public Object getLowerCode() {
        return lowerCode;
    }

    public void setLowerCode(Object lowerCode) {
        this.lowerCode = lowerCode;
    }

    public Object getParticipant() {
        return participant;
    }

    public void setParticipant(Object participant) {
        this.participant = participant;
    }

    public Object getPoint() {
        return point;
    }

    public void setPoint(Object point) {
        this.point = point;
    }

    public Object getMerchaFlag() {
        return merchaFlag;
    }

    public void setMerchaFlag(Object merchaFlag) {
        this.merchaFlag = merchaFlag;
    }

    public Object getMerchantFlag() {
        return merchantFlag;
    }

    public void setMerchantFlag(Object merchantFlag) {
        this.merchantFlag = merchantFlag;
    }

    public Object getMerchantDispType() {
        return merchantDispType;
    }

    public void setMerchantDispType(Object merchantDispType) {
        this.merchantDispType = merchantDispType;
    }

    public Object getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Object additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }
}
