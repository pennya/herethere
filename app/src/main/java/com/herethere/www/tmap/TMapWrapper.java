package com.herethere.www.tmap;

import android.app.Activity;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapView;

/**
 * Created by KJH on 2017-09-25.
 */

public class TMapWrapper {
    protected final String TMAP_API_KEY = "7d54b976-ee11-3f11-a5d8-0846567726ef";

    private TMapData tMapData;
    private TMapView mTMapView;

    /**
     * 반드시 초기화해줘야함.
     * @param activity
     * @return
     */
    public TMapWrapper TMapInit(Activity activity) {
        if(mTMapView == null && mTMapView == null) {
            mTMapView = new TMapView(activity);
            mTMapView.setSKPMapApiKey(TMAP_API_KEY);
            mTMapView.setZoomLevel(20);
            mTMapView.setMapType(TMapView.MAPTYPE_STANDARD);
            mTMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
            mTMapView.setTrackingMode(true);

            tMapData = new TMapData();
        }
        return this;
    }

    public void convertGpsToAddress(double latitude, double longitude, TMapData.ConvertGPSToAddressListenerCallback convertGPSToAddressListenerCallback) {
        if(convertGPSToAddressListenerCallback != null)
            tMapData.convertGpsToAddress(latitude, longitude, convertGPSToAddressListenerCallback);
    }
}
