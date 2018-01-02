package com.herethere.www.Entity.tour;

import android.content.Context;

import com.herethere.www.common.TextViewWithIconCallback;
import com.herethere.www.common.textview.TextViewWithIcon;

import java.util.ArrayList;

/**
 * Created by KIM on 2017-10-20.
 */

public interface TourDetail {

    String getResName();

    int getResAPIKind();

    String getResAddress();

    String getZipcode();

    String getOverview();

    String getFirstimage();

    ArrayList<TextViewWithIcon> makeTextViewWithTourApi(Context context, TourDetail tourDetail, TourList tourList, TextViewWithIconCallback callback);

    ArrayList<TextViewWithIcon> makeTextViewWithTMapApi(Context context, TourDetail tourDetail, TourList tourList, TextViewWithIconCallback callback);
}
