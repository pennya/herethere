package com.herethere.www.tour;

import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.Entity.naver.NaverBlogResponse;
import com.herethere.www.Entity.naver.NaverImage;
import com.herethere.www.Entity.naver.NaverImageResponse;
import com.herethere.www.Entity.tour.AttractionDetailResponse;
import com.herethere.www.Entity.tour.FacilDetailResponse;
import com.herethere.www.Entity.tour.FoodDetailResponse;
import com.herethere.www.common.TourDetailPresenterCallback;
import com.herethere.www.network.NaverSearchService;
import com.herethere.www.network.NetworkManager;
import com.herethere.www.network.TourService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KIM on 2017-10-10.
 */

public class TourDetailModel{
    private final String NAVER_OPEN_API_URL = "https://openapi.naver.com";
    private final String NAVER_OPEN_API_ACCEPT = "*/*";
    private final String NAVER_OPEN_API_CLIENT_ID = "OzH2fEjvDcanOaAwe4Ky";
    private final String NAVER_OPEN_API_CLIENT_SECRET = "2Ij44MmNKR";
    private final int NAVER_OPEN_API_IMAGE_DISPLAY_COUNT = 20;
    private final int NAVER_OPEN_API_BLOG_DISPLAY_COUNT = 100;
    private TourDetailPresenterCallback mCallback;

    public TourDetailModel(TourDetailPresenterCallback callback) {
        mCallback = callback;
    }

    public void loadImageData(String title) {
        NaverSearchService naverSearchService = NetworkManager.getIntance().getRetrofit(NaverSearchService.class, NAVER_OPEN_API_URL);
        Call<NaverImageResponse> naverImageResponseCall = naverSearchService.naverImageCall(NAVER_OPEN_API_ACCEPT, NAVER_OPEN_API_CLIENT_ID, NAVER_OPEN_API_CLIENT_SECRET,
                NAVER_OPEN_API_IMAGE_DISPLAY_COUNT, 1, "sim", "all", title);
        naverImageResponseCall.enqueue(new Callback<NaverImageResponse>() {
            @Override
            public void onResponse(Call<NaverImageResponse> call, Response<NaverImageResponse> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    NaverImageResponse ImageResponse = response.body();

                    List<NaverImage> items = ImageResponse.getItems();
                    mCallback.getImages(items);
                }
            }

            @Override
            public void onFailure(Call<NaverImageResponse> call, Throwable t) {
                mCallback.getImages(null);
            }
        });
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

    public void loadBlogData(String title) {
        NaverSearchService naverSearchService = NetworkManager.getIntance().getRetrofit(NaverSearchService.class, NAVER_OPEN_API_URL);
        Call<NaverBlogResponse> naverBlogResponseCall = naverSearchService.naverBlogCall(NAVER_OPEN_API_ACCEPT, NAVER_OPEN_API_CLIENT_ID, NAVER_OPEN_API_CLIENT_SECRET,
                NAVER_OPEN_API_BLOG_DISPLAY_COUNT, 1, "sim", "all", title);
        naverBlogResponseCall.enqueue(new Callback<NaverBlogResponse>() {
            @Override
            public void onResponse(Call<NaverBlogResponse> call, Response<NaverBlogResponse> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    NaverBlogResponse ImageResponse = response.body();

                    List<NaverBlog> items = ImageResponse.getItems();
                    mCallback.getBlogData(items);
                }
            }

            @Override
            public void onFailure(Call<NaverBlogResponse> call, Throwable t) {
                mCallback.getBlogData(null);
            }
        });
    }
}
