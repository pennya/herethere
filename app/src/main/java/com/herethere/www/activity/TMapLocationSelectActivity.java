package com.herethere.www.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herethere.www.R;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.util.BaseTMapActivity;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

public class TMapLocationSelectActivity extends BaseTMapActivity implements TMapView.OnEnableScrollWithZoomLevelCallback,
                                                                                View.OnClickListener,
                                                                                TMapData.ConvertGPSToAddressListenerCallback{

    private RelativeLayout mRootLayout;
    private Button mCompleteButton;
    private Button mCloseButton;
    private TextView mLocationInfo;

    private TMapMarkerItem mCenterMarkerItem;
    private TMapData mTMapData;

    private AutoCompleteItem mAutoCompleteItem;
    private int mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap_location_select);

        actionBarInit();
        layoutInit();
        TMapInit();
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
    public void onEnableScrollWithZoomLevelEvent(float v, TMapPoint tMapPoint) {
        mCenterMarkerItem.setTMapPoint(tMapPoint);
        mTMapData.convertGpsToAddress(tMapPoint.getLatitude(), tMapPoint.getLongitude(), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tmap_location_select_complete:
                if(mAutoCompleteItem == null) {
                    Toast.makeText(this, "로딩중입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AutoCompleteItem Item = new AutoCompleteItem()
                                            .setTitle(mAutoCompleteItem.getTitle())
                                            .setLatitude(mAutoCompleteItem.getLatitude())
                                            .setLongitude(mAutoCompleteItem.getLongitude());

                Intent intent = new Intent();
                intent.putExtra("item", Item);
                intent.putExtra("key", mKey);
                setResult(SearchActivity.SEARCH_ACTIVITY_REQEUST_CODE, intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btn_tmap_location_select_close:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onConvertToGPSToAddress(String strAddress) {
        mAutoCompleteItem = new AutoCompleteItem()
                                .setTitle(strAddress)
                                .setLatitude(mTMapView.getCenterPoint().getLatitude())
                                .setLongitude(mTMapView.getCenterPoint().getLongitude())
                                .build();

        mLocationInfo.setText(strAddress);
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    private void layoutInit() {
        Intent intent = getIntent();
        mKey = intent.getIntExtra("key", 0);

        mRootLayout = (RelativeLayout)findViewById(R.id.rl_tmap_view);
        mCompleteButton = (Button)findViewById(R.id.btn_tmap_location_select_complete);
        mCloseButton = (Button)findViewById(R.id.btn_tmap_location_select_close);
        mCompleteButton.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);
        mLocationInfo = (TextView)findViewById(R.id.tv_tmap_location_to_address);
    }

    private void TMapInit() {
        mRootLayout.addView(mTMapView);

        // 화면 중앙에 마커 표시
        mTMapView.setOnEnableScrollWithZoomLevelListener(this);
        mCenterMarkerItem = new TMapMarkerItem();
        mCenterMarkerItem.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_click));
        mCenterMarkerItem.setTMapPoint(mTMapView.getCenterPoint());
        mCenterMarkerItem.setVisible(mCenterMarkerItem.VISIBLE);
        mTMapView.addMarkerItem("centerMarkerItem", mCenterMarkerItem);

        mTMapData = new TMapData();
        mTMapData.convertGpsToAddress(mTMapView.getCenterPoint().getLatitude(),
                mTMapView.getCenterPoint().getLongitude(), this);
    }
}
