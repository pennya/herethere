package com.herethere.www.tmap;

import com.herethere.www.Entity.tour.TourResponse;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.TMapDetailPresenterCallback;
import com.herethere.www.network.NetworkManager;
import com.herethere.www.network.TourService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KIM on 2017-09-26.
 */

public class TMapDetailModel {

    private TMapDetailPresenterCallback mCallback;

    public TMapDetailModel(TMapDetailPresenterCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void loadData(int categoryNo, AutoCompleteItem startItem, AutoCompleteItem endItem) {
        TourService tourService = NetworkManager.getIntance().getRetrofit(TourService.class);
        Call<TourResponse> tourListCall = tourService.tourListCall(startItem.getLatitude(), startItem.getLongitude(),
                                                                    endItem.getLatitude(), endItem.getLongitude(), getCategoryString(categoryNo));
        tourListCall.enqueue(new Callback<TourResponse>() {
            @Override
            public void onResponse(Call<TourResponse> call, Response<TourResponse> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    TourResponse tourResponses = response.body();
                    mCallback.getData(tourResponses);
                }
            }

            @Override
            public void onFailure(Call<TourResponse> call, Throwable t) {
                mCallback.getData(null);
            }
        });
    }

    private String getCategoryString(int type) {
        if(type == 1)
            return "food";
        else if (type == 2)
            return "tour";
        else
            return "facil";
    }
}
