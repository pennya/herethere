package com.herethere.www.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herethere.www.R;
import com.herethere.www.autosearch.AutoCompleteAdapter;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.autosearch.ClearEditText;
import com.herethere.www.common.SearchActivityCallback;
import com.herethere.www.tmap.TMapWrapper;
import com.herethere.www.util.BasePermissionActivity;
import com.skp.Tmap.TMapData;

public class SearchActivity extends BasePermissionActivity implements SearchActivityCallback,
        View.OnClickListener,
        TMapData.ConvertGPSToAddressListenerCallback {

    public static final int SEARCH_ACTIVITY_REQEUST_CODE = 1883;
    public static final int TMAP_ACTIVITY_REQEUST_CODE = 1884;

    private LinearLayout mOptionBar;
    private TextView mCurrentLocation;
    private TextView mSelectLocation;
    private ClearEditText mClearEditText;

    private int mKey;
    private double mLatitude;
    private double mLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        actionBarInit();
        layoutInit();
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
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스트에서 선택했을때 메인액티비티로 전달
     * @param item
     */
    @Override
    public void sendSearchedData(AutoCompleteItem item) {
        Intent intent = new Intent();
        intent.putExtra("item", item);
        intent.putExtra("key", mKey);
        returnData(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_current_location:
                if (!checkPermissions() && Build.VERSION.SDK_INT >= 23) {
                    requestPermissions();
                } else{
                    getLastLocation();
                }
                break;
            case R.id.tv_select_location:
                Intent intent = new Intent(this, TMapLocationSelectActivity.class);
                intent.putExtra("key", mKey);
                startActivityForResult(intent, SearchActivity.TMAP_ACTIVITY_REQEUST_CODE);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    protected void showToastFromBasePermissionActivity(String msg) {
        showToast(msg);
    }

    @Override
    protected void getLocationInfo(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;

        new TMapWrapper().TMapInit(this).convertGpsToAddress(mLatitude, mLongitude, this);
    }

    /**
     * 현재위치 클릭했을때 메인액티비티로 전달
     * @param address
     */
    @Override
    public void onConvertToGPSToAddress(String address) {
        AutoCompleteItem Item = null;

        if(mKey == R.id.edt_start) {
            Item = new AutoCompleteItem()
                    .setTitle("현재위치")
                    .setLatitude(mLatitude)
                    .setLongitude(mLongitude)
                    .build();
        } else {
            Item = new AutoCompleteItem()
                    .setTitle("반경 5km")
                    .setRoundSearch(true)
                    .build();
        }

        Intent intent = new Intent();
        intent.putExtra("item", Item);
        intent.putExtra("key", mKey);
        returnData(intent);
    }

    /**
     * 지도에서 위치 선택했을때 메인액티비티로 전달
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchActivity.TMAP_ACTIVITY_REQEUST_CODE && data != null) {
            returnData(data);
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
        mOptionBar = (LinearLayout)findViewById(R.id.ll_av_search_option_bar);
        mCurrentLocation = (TextView)findViewById(R.id.tv_current_location);
        mSelectLocation = (TextView)findViewById(R.id.tv_select_location);

        mCurrentLocation.setOnClickListener(this);
        mSelectLocation.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AutoCompleteAdapter dataAdapter = new AutoCompleteAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_search_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mClearEditText = (ClearEditText)findViewById(R.id.edt_auto_search);
        mClearEditText.setListViewAdpater(dataAdapter);
    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        mKey = intent.getIntExtra("key", 0);

        if(mKey == R.id.edt_start) {
            mOptionBar.setBackgroundResource(R.mipmap.map_here_optionbar);
            mClearEditText.setBackgroundResource(R.mipmap.icon_here_searchbar);
            mClearEditText.setResource(R.mipmap.icon_here);
            mCurrentLocation.setText("현재위치");
        } else {
            mOptionBar.setBackgroundResource(R.mipmap.map_there_optionbar);
            mClearEditText.setBackgroundResource(R.mipmap.icon_there_searchbar);
            mClearEditText.setResource(R.mipmap.icon_there);
            mCurrentLocation.setText("주변위치");
        }
    }

    private void returnData(Intent intent) {
        setResult(SearchActivity.SEARCH_ACTIVITY_REQEUST_CODE, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
