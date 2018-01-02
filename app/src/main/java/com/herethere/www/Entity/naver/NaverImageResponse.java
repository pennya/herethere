package com.herethere.www.Entity.naver;

import java.util.List;

/**
 * Created by KIM on 2017-10-11.
 */

public class NaverImageResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverImage> items;

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public List<NaverImage> getItems() {
        return items;
    }

    public void setItems(List<NaverImage> items) {
        this.items = items;
    }
}
