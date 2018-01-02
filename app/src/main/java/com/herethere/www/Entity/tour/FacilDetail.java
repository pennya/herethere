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

public class FacilDetail implements TourDetail {
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

    /*facil*/
    private Object Chkbabycarriageshopping;
    private Object Chkcreditcardshopping;
    private Object chkpetshopping;
    private Object culturecenter;
    private Object fairday;
    private Object infocentershopping;
    private Object opendateshopping;
    private Object opentime;
    private Object parkingshopping;
    private Object restdateshopping;
    private Object restroom;
    private Object saleitem;
    private Object saleitemcost;
    private Object scaleshopping;
    private Object shopguide;

    /*tmap facil*/
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

        FacilDetail facilDetail = (FacilDetail)tourDetail;

        if(checkValidString(String.valueOf(facilDetail.getOpentime())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_time, "영업시간", String.valueOf(facilDetail.getOpentime()), callback));
        if(checkValidString(String.valueOf(facilDetail.getChkbabycarriageshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "유모차 대여 여부", String.valueOf(facilDetail.getChkbabycarriageshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getChkcreditcardshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "신용카드 가능 여부", String.valueOf(facilDetail.getChkcreditcardshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getChkpetshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "애완동물 가능 여부", String.valueOf(facilDetail.getChkpetshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getCulturecenter())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "문화센터 바로가기", String.valueOf(facilDetail.getCulturecenter()), callback));
        if(checkValidString(String.valueOf(facilDetail.getFairday())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "장서는 날", String.valueOf(facilDetail.getFairday()), callback));
        if(checkValidString(String.valueOf(facilDetail.getInfocentershopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "문의 및 안내", String.valueOf(facilDetail.getInfocentershopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getParkingshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "주차시설", String.valueOf(facilDetail.getParkingshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getRestdateshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "쉬는날", String.valueOf(facilDetail.getRestdateshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getRestroom())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "화장실 설명", String.valueOf(facilDetail.getRestroom()), callback));
        if(checkValidString(String.valueOf(facilDetail.getSaleitem())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "판매 품목", String.valueOf(facilDetail.getSaleitem()), callback));
        if(checkValidString(String.valueOf(facilDetail.getSaleitemcost())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "판매 품목별 가격", String.valueOf(facilDetail.getSaleitemcost()), callback));
        if(checkValidString(String.valueOf(facilDetail.getScaleshopping())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "규모", String.valueOf(facilDetail.getScaleshopping()), callback));
        if(checkValidString(String.valueOf(facilDetail.getShopguide())))
            textLists.add(TextViewWithIconHelper.newTextViewWithIcon(context, R.mipmap.icon_information_etc, "매장안내", String.valueOf(facilDetail.getShopguide()), callback));

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

    public Object getChkbabycarriageshopping() {
        return Chkbabycarriageshopping;
    }

    public void setChkbabycarriageshopping(Object chkbabycarriageshopping) {
        Chkbabycarriageshopping = chkbabycarriageshopping;
    }

    public Object getChkcreditcardshopping() {
        return Chkcreditcardshopping;
    }

    public void setChkcreditcardshopping(Object chkcreditcardshopping) {
        Chkcreditcardshopping = chkcreditcardshopping;
    }

    public Object getChkpetshopping() {
        return chkpetshopping;
    }

    public void setChkpetshopping(Object chkpetshopping) {
        this.chkpetshopping = chkpetshopping;
    }

    public Object getCulturecenter() {
        return culturecenter;
    }

    public void setCulturecenter(Object culturecenter) {
        this.culturecenter = culturecenter;
    }

    public Object getFairday() {
        return fairday;
    }

    public void setFairday(Object fairday) {
        this.fairday = fairday;
    }

    public Object getInfocentershopping() {
        return infocentershopping;
    }

    public void setInfocentershopping(Object infocentershopping) {
        this.infocentershopping = infocentershopping;
    }

    public Object getOpendateshopping() {
        return opendateshopping;
    }

    public void setOpendateshopping(Object opendateshopping) {
        this.opendateshopping = opendateshopping;
    }

    public Object getOpentime() {
        return opentime;
    }

    public void setOpentime(Object opentime) {
        this.opentime = opentime;
    }

    public Object getParkingshopping() {
        return parkingshopping;
    }

    public void setParkingshopping(Object parkingshopping) {
        this.parkingshopping = parkingshopping;
    }

    public Object getRestdateshopping() {
        return restdateshopping;
    }

    public void setRestdateshopping(Object restdateshopping) {
        this.restdateshopping = restdateshopping;
    }

    public Object getRestroom() {
        return restroom;
    }

    public void setRestroom(Object restroom) {
        this.restroom = restroom;
    }

    public Object getSaleitem() {
        return saleitem;
    }

    public void setSaleitem(Object saleitem) {
        this.saleitem = saleitem;
    }

    public Object getSaleitemcost() {
        return saleitemcost;
    }

    public void setSaleitemcost(Object saleitemcost) {
        this.saleitemcost = saleitemcost;
    }

    public Object getScaleshopping() {
        return scaleshopping;
    }

    public void setScaleshopping(Object scaleshopping) {
        this.scaleshopping = scaleshopping;
    }

    public Object getShopguide() {
        return shopguide;
    }

    public void setShopguide(Object shopguide) {
        this.shopguide = shopguide;
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
