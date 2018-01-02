package com.herethere.www.Entity.tour;

import android.content.Context;

import com.herethere.www.R;
import com.herethere.www.common.TextViewWithIconCallback;
import com.herethere.www.common.textview.TextViewWithIcon;
import com.herethere.www.util.TextViewWithIconHelper;

import java.util.ArrayList;

/**
 * Created by KIM on 2017-10-19.
 */

public class FoodDetail implements TourDetail{
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

    /*restourant*/
    private Object chkcreditcardfood;
    private Object discountinfofood;
    private Object firstmenu;
    private Object infocenterfood;
    private Object kidsfacility;
    private Object opendatefood;
    private Object opentimefood;
    private Object packing;
    private Object parkingfood;
    private Object reservationfood;
    private Object restdatefood;
    private Object scalefood;
    private Object seat;
    private Object smoking;
    private Object treatmenu;

    private Object booktour;
    private Object mlevel;

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

        FoodDetail foodDetail = (FoodDetail)tourDetail;

        if(checkValidString(String.valueOf(foodDetail.getOpentimefood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_time, "영업시간", String.valueOf(foodDetail.getOpentimefood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getFirstmenu())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "대표메뉴", String.valueOf(foodDetail.getFirstmenu()), callback));
        if(checkValidString(String.valueOf(foodDetail.getTreatmenu())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "취급 메뉴", String.valueOf(foodDetail.getTreatmenu()), callback));
        if(checkValidString(String.valueOf(foodDetail.getInfocenterfood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "문의 및 안내", String.valueOf(foodDetail.getInfocenterfood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getChkcreditcardfood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "신용카드 가능 여부", String.valueOf(foodDetail.getChkcreditcardfood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getDiscountinfofood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "할인정보", String.valueOf(foodDetail.getDiscountinfofood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getKidsfacility())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "어린이 놀이방", String.valueOf(foodDetail.getKidsfacility()), callback));
        if(checkValidString(String.valueOf(foodDetail.getOpendatefood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "개업일", String.valueOf(foodDetail.getOpendatefood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getPacking())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "포장 가능 여부", String.valueOf(foodDetail.getPacking()), callback));
        if(checkValidString(String.valueOf(foodDetail.getParkingfood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "주차시설", String.valueOf(foodDetail.getParkingfood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getReservationfood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "예약안내", String.valueOf(foodDetail.getReservationfood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getRestdatefood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "쉬는날", String.valueOf(foodDetail.getRestdatefood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getScalefood())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "규모", String.valueOf(foodDetail.getScalefood()), callback));
        if(checkValidString(String.valueOf(foodDetail.getSeat())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "좌석수", String.valueOf(foodDetail.getSeat()), callback));
        if(checkValidString(String.valueOf(foodDetail.getSmoking())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "금연/흡연 여부", String.valueOf(foodDetail.getSmoking()), callback));

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

    public Object getChkcreditcardfood() {
        return chkcreditcardfood;
    }

    public void setChkcreditcardfood(Object chkcreditcardfood) {
        this.chkcreditcardfood = chkcreditcardfood;
    }

    public Object getDiscountinfofood() {
        return discountinfofood;
    }

    public void setDiscountinfofood(Object discountinfofood) {
        this.discountinfofood = discountinfofood;
    }

    public Object getFirstmenu() {
        return firstmenu;
    }

    public void setFirstmenu(Object firstmenu) {
        this.firstmenu = firstmenu;
    }

    public Object getInfocenterfood() {
        return infocenterfood;
    }

    public void setInfocenterfood(Object infocenterfood) {
        this.infocenterfood = infocenterfood;
    }

    public Object getKidsfacility() {
        return kidsfacility;
    }

    public void setKidsfacility(Object kidsfacility) {
        this.kidsfacility = kidsfacility;
    }

    public Object getOpendatefood() {
        return opendatefood;
    }

    public void setOpendatefood(Object opendatefood) {
        this.opendatefood = opendatefood;
    }

    public Object getOpentimefood() {
        return opentimefood;
    }

    public void setOpentimefood(Object opentimefood) {
        this.opentimefood = opentimefood;
    }

    public Object getPacking() {
        return packing;
    }

    public void setPacking(Object packing) {
        this.packing = packing;
    }

    public Object getParkingfood() {
        return parkingfood;
    }

    public void setParkingfood(Object parkingfood) {
        this.parkingfood = parkingfood;
    }

    public Object getReservationfood() {
        return reservationfood;
    }

    public void setReservationfood(Object reservationfood) {
        this.reservationfood = reservationfood;
    }

    public Object getRestdatefood() {
        return restdatefood;
    }

    public void setRestdatefood(Object restdatefood) {
        this.restdatefood = restdatefood;
    }

    public Object getScalefood() {
        return scalefood;
    }

    public void setScalefood(Object scalefood) {
        this.scalefood = scalefood;
    }

    public Object getSeat() {
        return seat;
    }

    public void setSeat(Object seat) {
        this.seat = seat;
    }

    public Object getSmoking() {
        return smoking;
    }

    public void setSmoking(Object smoking) {
        this.smoking = smoking;
    }

    public Object getTreatmenu() {
        return treatmenu;
    }

    public void setTreatmenu(Object treatmenu) {
        this.treatmenu = treatmenu;
    }

    public Object getBooktour() {
        return booktour;
    }

    public void setBooktour(Object booktour) {
        this.booktour = booktour;
    }

    public Object getMlevel() {
        return mlevel;
    }

    public void setMlevel(Object mlevel) {
        this.mlevel = mlevel;
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
