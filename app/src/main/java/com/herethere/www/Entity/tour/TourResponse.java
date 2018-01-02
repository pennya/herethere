package com.herethere.www.Entity.tour;

import java.util.List;

/**
 * Created by KIM on 2017-10-10.
 */

public class TourResponse {
    private String result;
    private List<TourList> item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TourList> getItem() {
        return item;
    }

    public void setItem(List<TourList> item) {
        this.item = item;
    }
}
