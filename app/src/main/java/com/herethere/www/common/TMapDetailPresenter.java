package com.herethere.www.common;

import com.herethere.www.Entity.tour.TourResponse;
import com.herethere.www.autosearch.AutoCompleteItem;

/**
 * Created by KIM on 2017-09-26.
 */

public interface TMapDetailPresenter {

    void loadData(int categoryNo, AutoCompleteItem startItem, AutoCompleteItem endItem);

    interface View {

        void getData(TourResponse tourResponses);

    }

}
