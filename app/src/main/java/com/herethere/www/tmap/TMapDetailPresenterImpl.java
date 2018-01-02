package com.herethere.www.tmap;

import com.herethere.www.Entity.tour.TourResponse;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.TMapDetailPresenter;
import com.herethere.www.common.TMapDetailPresenterCallback;

/**
 * Created by KIM on 2017-09-26.
 */

public class TMapDetailPresenterImpl implements TMapDetailPresenter, TMapDetailPresenterCallback {

    private TMapDetailPresenter.View mView;
    private TMapDetailModel mModel;

    public TMapDetailPresenterImpl(View view) {
        mView = view;
        mModel = new TMapDetailModel(this);
    }

    @Override
    public void loadData(int categoryNo, AutoCompleteItem startItem, AutoCompleteItem endItem) {
        mModel.loadData(categoryNo, startItem, endItem);
    }

    @Override
    public void getData(TourResponse tourResponses) {
        mView.getData(tourResponses);
    }
}
