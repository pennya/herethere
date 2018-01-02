package com.herethere.www.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.skp.Tmap.TMapView;

/**
 * Created by KJH on 2017-09-25.
 */

public abstract class BaseTMapActivity extends AppCompatActivity {
    public static final String TMAP_API_KEY = "7d54b976-ee11-3f11-a5d8-0846567726ef";

    protected TMapView mTMapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTMapView = new TMapView(this);
        mTMapView.setSKPMapApiKey(TMAP_API_KEY);
        mTMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        mTMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        mTMapView.setTileType(TMapView.TILETYPE_HDTILE);
    }
}
