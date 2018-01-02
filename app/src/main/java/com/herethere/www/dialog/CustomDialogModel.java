package com.herethere.www.dialog;

import com.herethere.www.Entity.tour.AttractionDetailResponse;
import com.herethere.www.Entity.tour.FacilDetailResponse;
import com.herethere.www.Entity.tour.FoodDetailResponse;
import com.herethere.www.common.CustomDialogPresenterCallback;
import com.herethere.www.network.NetworkManager;
import com.herethere.www.network.TourService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KIM on 2017-10-27.
 */

public class CustomDialogModel {

    private CustomDialogPresenterCallback mCallback;

    public CustomDialogModel(CustomDialogPresenterCallback callback) {
        this.mCallback = callback;
    }

    public void loadData(int contentId, int contentTypeId, int kind, double startLat, double startLon) {
        TourService tourService = NetworkManager.getIntance().getRetrofit(TourService.class);

        switch(contentTypeId) {
            case 39:
                Call<FoodDetailResponse> foodDetailResponseCall = tourService.foodDetailCall(contentId, "food", kind, startLat, startLon);
                foodDetailResponseCall.enqueue(new Callback<FoodDetailResponse>() {
                    @Override
                    public void onResponse(Call<FoodDetailResponse> call, Response<FoodDetailResponse> response) {
                        if(response.isSuccessful() && response.code() == 200) {
                            FoodDetailResponse detailResponse = response.body();
                            mCallback.getData(detailResponse.getItem());
                        }
                    }

                    @Override
                    public void onFailure(Call<FoodDetailResponse> call, Throwable t) {
                        mCallback.getData(null);
                    }
                });
                break;

            case 12:
                Call<AttractionDetailResponse> attractionDetailCall = tourService.attractionDetailCall(contentId, "tour", kind, startLat, startLon);
                attractionDetailCall.enqueue(new Callback<AttractionDetailResponse>() {
                    @Override
                    public void onResponse(Call<AttractionDetailResponse> call, Response<AttractionDetailResponse> response) {
                        if(response.isSuccessful() && response.code() == 200) {
                            AttractionDetailResponse detailResponse = response.body();
                            mCallback.getData(detailResponse.getItem());
                        }
                    }

                    @Override
                    public void onFailure(Call<AttractionDetailResponse> call, Throwable t) {
                        mCallback.getData(null);
                    }
                });
                break;

            case 38:
                Call<FacilDetailResponse> facilDetailCall = tourService.facilDetailCall(contentId, "facil", kind, startLat, startLon);
                facilDetailCall.enqueue(new Callback<FacilDetailResponse>() {
                    @Override
                    public void onResponse(Call<FacilDetailResponse> call, Response<FacilDetailResponse> response) {
                        if(response.isSuccessful() && response.code() == 200) {
                            FacilDetailResponse detailResponse = response.body();
                            mCallback.getData(detailResponse.getItem());
                        }
                    }

                    @Override
                    public void onFailure(Call<FacilDetailResponse> call, Throwable t) {
                        mCallback.getData(null);
                    }
                });
                break;
        }
    }
}
