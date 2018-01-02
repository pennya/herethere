package com.herethere.www.dialog;

import com.herethere.www.Entity.tour.TourDetail;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.CustomDialogPresenter;
import com.herethere.www.common.CustomDialogPresenterCallback;

/**
 * Created by KIM on 2017-10-27.
 */

public class CustomDialogPresenterImpl implements CustomDialogPresenter, CustomDialogPresenterCallback{

    private CustomDialogPresenter.View mView;
    private CustomDialogModel mModel;

    public CustomDialogPresenterImpl(CustomDialogPresenter.View view) {
        mView = view;
        mModel = new CustomDialogModel(this);
    }

    @Override
    public void getData(TourDetail data) {
        mView.getData(data);
    }

    public void loadData(TourList tourList, AutoCompleteItem startItem) {
        mModel.loadData(Integer.parseInt(tourList.getContentid()), Integer.parseInt(tourList.getContenttypeid()),
                Integer.parseInt(tourList.getResAPIKind()), startItem.getLatitude(), startItem.getLongitude());
    }

}
