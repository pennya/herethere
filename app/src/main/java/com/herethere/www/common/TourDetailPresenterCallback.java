package com.herethere.www.common;

import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.Entity.naver.NaverImage;
import com.herethere.www.Entity.tour.TourDetail;

import java.util.List;

/**
 * Created by KIM on 2017-10-10.
 */

public interface TourDetailPresenterCallback {
    void getImages(List<NaverImage> imageList);

    void getData(TourDetail data);

    void getBlogData(List<NaverBlog> blogList);
}
