package com.herethere.www.network;

import com.herethere.www.Entity.naver.NaverBlogResponse;
import com.herethere.www.Entity.naver.NaverImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by KIM on 2017-10-11.
 */

public interface NaverSearchService {

    @GET("/v1/search/image")
    Call<NaverImageResponse> naverImageCall(@Header("Accept")String Accept, @Header("X-Naver-Client-Id") String naverClientId,
                                            @Header("X-Naver-Client-Secret") String naverClientSecret,
                                            @Query("display") int display, @Query("start") int start,
                                            @Query("sort") String sort, @Query("filter") String filter,
                                            @Query("query") String query);

    @GET("/v1/search/blog")
    Call<NaverBlogResponse> naverBlogCall(@Header("Accept")String Accept, @Header("X-Naver-Client-Id") String naverClientId,
                                          @Header("X-Naver-Client-Secret") String naverClientSecret,
                                          @Query("display") int display, @Query("start") int start,
                                          @Query("sort") String sort, @Query("filter") String filter,
                                          @Query("query") String query);
}
