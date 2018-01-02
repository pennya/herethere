package com.herethere.www.autosearch;

import java.io.Serializable;

/**
 * Created by KJH on 2017-09-06.
 */

public class AutoCompleteItem implements Serializable{

    private static final long serialVersionUID = 5340329595934350485L;

    private String title;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isRoundSearch;

    public AutoCompleteItem() {
        title = "";
        address = "";
        latitude = 0.0;
        longitude = 0.0;
        isRoundSearch = false;
    }

    public AutoCompleteItem build() {
        /* 다른 초기화 작업이 있다면 여기서*/
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AutoCompleteItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AutoCompleteItem setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public AutoCompleteItem setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public AutoCompleteItem setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public boolean isRoundSearch() {
        return isRoundSearch;
    }

    public AutoCompleteItem setRoundSearch(boolean roundSearch) {
        isRoundSearch = roundSearch;
        return this;
    }
}
