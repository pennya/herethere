package com.herethere.www.common;

import com.herethere.www.Entity.tour.TourList;

/**
 * Created by KIM on 2017-10-27.
 */

public interface CustomDialogCallback {

    void ready();

    void selectAgain();

    void moveDetailView(TourList tourList);

    void clear();
}
