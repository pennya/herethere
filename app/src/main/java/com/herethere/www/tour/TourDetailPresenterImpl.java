package com.herethere.www.tour;

import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.Entity.naver.NaverImage;
import com.herethere.www.Entity.tour.TourDetail;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.TourDetailPresenter;
import com.herethere.www.common.TourDetailPresenterCallback;

import java.util.List;

/**
 * Created by KIM on 2017-10-10.
 */

public class TourDetailPresenterImpl implements TourDetailPresenter, TourDetailPresenterCallback {
    private TourDetailPresenter.View mView;
    private TourDetailModel mModel;

    public TourDetailPresenterImpl(TourDetailPresenter.View view) {
        mView = view;
        mModel = new TourDetailModel(this);
    }

    public void loadImageData(String title) {
        mModel.loadImageData(title);
    }

    public void loadData(TourList tourList, AutoCompleteItem startItem) {
        mModel.loadData(Integer.parseInt(tourList.getContentid()), Integer.parseInt(tourList.getContenttypeid()),
                        Integer.parseInt(tourList.getResAPIKind()), startItem.getLatitude(), startItem.getLongitude());
    }

    public void loadBlogData(String title) {
        mModel.loadBlogData(title);
    }

    @Override
    public void getImages(List<NaverImage> imageList) {
        mView.getImages(imageList);
    }

    @Override
    public void getData(TourDetail data) {
        mView.getData(data);
    }

    @Override
    public void getBlogData(List<NaverBlog> blogList) {
        mView.getBlogData(blogList);
    }

}
