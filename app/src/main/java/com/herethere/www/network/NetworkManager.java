package com.herethere.www.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KJH on 2017-10-06.
 */

public class NetworkManager {
    private static final NetworkManager networkManager = new NetworkManager();
    private final int MAX_CONNECT_TIMEOUT = 2000;
    private final String END_POINT = "http://jh3.pantople.com:8081";

    private NetworkManager() {
    }

    public static NetworkManager getIntance() {
        return networkManager;
    }

    public <T> T getRetrofit(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(service);
    }

    public <T> T getRetrofit(Class<T> service, String endPoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(service);
    }

}
