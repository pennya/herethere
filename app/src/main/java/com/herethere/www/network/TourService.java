package com.herethere.www.network;

import com.herethere.www.Entity.tour.AttractionDetailResponse;
import com.herethere.www.Entity.tour.FacilDetailResponse;
import com.herethere.www.Entity.tour.FoodDetailResponse;
import com.herethere.www.Entity.tour.TourResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by KJH on 2017-10-06.
 */

public interface TourService {
    @GET("/here/getList")
    Call<TourResponse> tourListCall(@Query("startLat") double startLat, @Query("startLon") double startLon,
                                    @Query("endLat") double endLat, @Query("endLon") double endLon,
                                    @Query("category") String category);

    @GET("/here/getDetail")
    Call<FoodDetailResponse> foodDetailCall(@Query("contentId") int contentId, @Query("category") String category,
                                            @Query("kind") int kind, @Query("startLat") double startLat, @Query("startLon") double startLon);

    @GET("/here/getDetail")
    Call<AttractionDetailResponse> attractionDetailCall(@Query("contentId") int contentId, @Query("category") String category,
                                                        @Query("kind") int kind, @Query("startLat") double startLat, @Query("startLon") double startLon);

    @GET("/here/getDetail")
    Call<FacilDetailResponse> facilDetailCall(@Query("contentId") int contentId, @Query("category") String category,
                                              @Query("kind") int kind, @Query("startLat") double startLat, @Query("startLon") double startLon);
}
