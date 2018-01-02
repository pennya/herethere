package com.herethere.www.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.herethere.www.R;
import com.herethere.www.util.BaseTMapActivity;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapTapi;

public class MapViewActivity extends BaseTMapActivity implements View.OnClickListener{

    private RelativeLayout mRootLayout;
    private ImageButton mBtnWayToGo;

    private String title;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        actionBarInit();
        layoutInit();
        TMapInit();
        setDefaultSetting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_find_way_to_go_in_map_view :
                TMapTapi tmaptapi = new TMapTapi(this);
                tmaptapi.setSKPMapAuthentication (BaseTMapActivity.TMAP_API_KEY);
                boolean isTmapApp = tmaptapi.isTmapApplicationInstalled();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                if(!isTmapApp) {
                    try {
                        intent.setData(Uri.parse("market://details?id=com.skt.tmap.ku"));
                        startActivity(intent);
                    }catch (Exception e) {
                        Toast.makeText(this, "google play store를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                boolean result = tmaptapi.invokeRoute(title , (float)lon, (float)lat);
                if(!result)
                    Toast.makeText(this, "TMap 연결 불가능", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    private void layoutInit() {
        mRootLayout = (RelativeLayout)findViewById(R.id.tmap_activity_map_view);
        mBtnWayToGo = (ImageButton)findViewById(R.id.btn_find_way_to_go_in_map_view);

        mBtnWayToGo.setOnClickListener(this);
    }

    private void TMapInit() {
        mTMapView.setZoomLevel(18); /* 한개니까 가깝게 */
        mRootLayout.addView(mTMapView);
    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        lat = intent.getDoubleExtra("lat", 0.0);
        lon = intent.getDoubleExtra("lon", 0.0);

        TMapMarkerItem spot = new TMapMarkerItem();
        spot.setName(title);
        spot.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_click));
        spot.setTMapPoint(new TMapPoint(lat, lon));
        spot.setVisible(spot.VISIBLE);
        mTMapView.addMarkerItem(title, spot);

        mTMapView.setLocationPoint(lon, lat);
        mTMapView.setCenterPoint(lon, lat);
    }
}
