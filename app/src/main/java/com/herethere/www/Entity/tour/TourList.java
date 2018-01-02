package com.herethere.www.Entity.tour;

import java.io.Serializable;

/**
 * Created by KIM on 2017-10-10.
 */

public class TourList implements Serializable {

    private static final long serialVersionUID = 1174538249679227708L;

    private String resAPIKind;
    private String contentid;
    private String contenttypeid;
    private String resAddress;
    private String resAddress2;
    private String resDistance;
    private String firstimage;
    private String firstimage2;
    private double resLat;
    private double resLon;
    private String resCategory;
    private String resSubCate;
    private long modifiedtime;
    private int readcount;
    private String resTel;
    private String resName;
    private String cat1;
    private String cat2;
    private String cat3;


    public String getResAPIKind() {
        return resAPIKind;
    }

    public void setResAPIKind(String resAPIKind) {
        this.resAPIKind = resAPIKind;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(String contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public String getResAddress() {
        return resAddress;
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

    public String getResDistance() {
        return resDistance;
    }

    public void setResDistance(String resDistance) {
        this.resDistance = resDistance;
    }

    public String getFirstimage() {
        return firstimage;
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

    public String getResCategory() {
        return resCategory;
    }

    public void setResCategory(String resCategory) {
        this.resCategory = resCategory;
    }

    public String getResSubCate() {
        return resSubCate;
    }

    public void setResSubCate(String resSubCate) {
        this.resSubCate = resSubCate;
    }

    public long getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    public String getResTel() {
        return resTel;
    }

    public void setResTel(String resTel) {
        this.resTel = resTel;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }
}
