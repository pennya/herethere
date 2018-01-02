package com.herethere.www.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.Entity.tour.TourResponse;
import com.herethere.www.R;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.ListFragmentCallback;
import com.herethere.www.common.TMapDetailPresenter;
import com.herethere.www.fragment.ListFragment;
import com.herethere.www.tmap.TMapDetailPresenterImpl;
import com.herethere.www.util.BaseTMapActivity;
import com.skp.Tmap.TMapInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TMapDetailActivity extends BaseTMapActivity implements View.OnClickListener,
                                                            TMapView.OnClickListenerCallback ,
                                                            TMapDetailPresenter.View,
                                                            ListFragmentCallback {

    private ConstraintLayout mRootConstraintLayout;
    private RelativeLayout mRootLayout;
    private RelativeLayout mBottomInfoRootLayout;
    private RelativeLayout mBottomBriefRootLayout;
    private RelativeLayout mBottomListRootLayout; /* 목록보기 */
    private ImageButton mBtnCategory1;
    private ImageButton mBtnCategory2;
    private ImageButton mBtnCategory3;
    private ImageButton mBtnCategory4;
    private ImageButton mBtnCategory5;
    private ImageView mSelectedItemImg;
    private TextView mSelectedItemTitle;
    private TextView mSelectedItemAddress;
    private TextView mBottomBriefTitle;
    private Guideline mGuideline;
    private RotateLoading mRotateLoding;

    private TMapDetailPresenter mPresenter;
    private int mCategoryNo;
    private AutoCompleteItem mStartItem;
    private AutoCompleteItem mEndItem;
    private TMapMarkerItem mPrevItem; /* 클릭,해제시 마커이미지 변경*/
    private HashMap<String, TourList> mItemHash;
    private List<TourList> items; /* 소 카테고리 나누기용*/
    private ListFragment mListFragment;
    private int mTotalCount; /* ex 음식점 전체카테고리 개수 */
    private int NETWORK_CONNECT_TRYING_COUNT = 0; /* if network connect failed or server died and when it comes over 1, finish activity*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap_detail);

        actionBarInit();
        layoutInit();
        dynamicLayoutInit();
        TMapInit();
        setDefaultSetting();
        LoadData();
        categoryInit();
    }

    @Override
    public void getData(TourResponse tourResponses) {
        mRotateLoding.stop();

        /* 네트워크 실패시 동작 */
        if(tourResponses == null) {
            if(NETWORK_CONNECT_TRYING_COUNT > 0) {
                Toast.makeText(this, "네트워크가 불안정하거나 서버가 죽어있음.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            }
            LoadData();
            NETWORK_CONNECT_TRYING_COUNT += 1;
            return;
        }

        items = tourResponses.getItem();
        int totalCount = 0;

        if(items == null || items.size() == 0) {
            Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return;
        } else {
            totalCount = items.size();
            mTotalCount = totalCount;
        }

        clickIconCategory1();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_category_1 :
                clickIconCategory1();
                break;

            case R.id.btn_category_2 :
                clickIconCategory2();
                break;

            case R.id.btn_category_3 :
                clickIconCategory3();
                break;

            case R.id.btn_category_4 :
                clickIconCategory4();
                break;

            case R.id.btn_category_5 :
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                //clickIconCategory5();
                break;

            case R.id.iv_tmap_info_rootview:
                TourList item = mItemHash.get(mPrevItem.getID());

                Intent intent = new Intent(this, TourDetailActivity.class);
                intent.putExtra("tourList", item);
                intent.putExtra("startItem", mStartItem);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.iv_tmap_brief_rootview:
                TranslateAnimation animate = new TranslateAnimation(0, 0, mRootLayout.getHeight(), 0);
                animate.setDuration(500);
                mBottomListRootLayout.startAnimation(animate);
                mBottomListRootLayout.setVisibility(View.VISIBLE);
                mBottomBriefRootLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void back() {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, mRootLayout.getHeight());
        animate.setDuration(500);
        mBottomListRootLayout.startAnimation(animate);
        mBottomListRootLayout.setVisibility(View.GONE);
        mBottomBriefRootLayout.setVisibility(View.VISIBLE);
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
    public void onBackPressed() {
        if(mBottomListRootLayout.getVisibility() == View.VISIBLE) {
            mListFragment.showSortGroup();
            back(); /* 리스트 뷰일때 back 버튼 누르면 MainAcitivity가 아닌 지도에서 보기*/
        } else {
            super.onBackPressed(); /* 지도에서 보기일때는 뒤로가기*/
        }
    }

    @Override
    public boolean onPressEvent(ArrayList<TMapMarkerItem> markerList, ArrayList<TMapPOIItem> poiList, TMapPoint tMapPoint, PointF pointF) {
        if(markerList.size() != 0 && (!markerList.get(0).getID().equals("startPoint") && !markerList.get(0).getID().equals("endPoint"))) {
            if(mPrevItem != null)
                setSwitchMarkerIcon(mPrevItem, false);

            mPrevItem = markerList.get(0);
            setSwitchMarkerIcon(mPrevItem, true);

            setBottomInfoLayout();
            TourList item = mItemHash.get(markerList.get(0).getID());

            RequestOptions requestOptions = new RequestOptions()
                    .fitCenter()
                    .dontAnimate()
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.image_loding);

            Glide.with(this)
                    .load(item.getFirstimage())
                    .apply(requestOptions)
                    .into(mSelectedItemImg);

            mSelectedItemTitle.setText(item.getResName());
            mSelectedItemAddress.setText(item.getResAddress());

        } else {
            if(mBottomInfoRootLayout.getVisibility() == View.GONE &&
                    mBottomBriefRootLayout.getVisibility() == View.VISIBLE)
                return false;

            if(mPrevItem != null) {
                setSwitchMarkerIcon(mPrevItem, false);
                mPrevItem = null;
            }

            setBottomBriefLayout();
        }

        return false;
    }

    @Override
    public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
        return false;
    }

    private void updateMarkerAndList(List<TourList> tourLists) {
        /* 리스트 업데이트*/
        ArrayList<TourList> newLists = new ArrayList<>();
        newLists.addAll(tourLists);
        mListFragment.setList(newLists);

        /* 마커 업데이트*/
        mTMapView.removeAllMarkerItem();

        showStartAndEndMarker();

        TMapInfo info = null;
        ArrayList<TMapPoint> pointLists = new ArrayList<>(); /* 마커 지점 리스트*/

        for(TourList item : tourLists) {
            mItemHash.put(item.getContentid(), item);

            double latitude = item.getResLat();
            double longitude = item.getResLon();

            pointLists.add(new TMapPoint(latitude, longitude));

            TMapMarkerItem markerItem = new TMapMarkerItem();
            markerItem.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_unclick));
            markerItem.setTMapPoint(new TMapPoint(latitude,longitude));
            markerItem.setName(item.getResName());
            markerItem.setVisible(markerItem.VISIBLE);
            mTMapView.addMarkerItem(item.getContentid(), markerItem);

        }

        /* 화면에 최적화된 상태로 보일수있는 ZoomLevel과 중심점 설정 */
        info = mTMapView.getDisplayTMapInfo(pointLists);
        mTMapView.setZoomLevel(info.getTMapZoomLevel());
        mTMapView.setLocationPoint(info.getTMapPoint().getLongitude(), info.getTMapPoint().getLatitude());
        mTMapView.setCenterPoint(info.getTMapPoint().getLongitude(), info.getTMapPoint().getLatitude());
    }

    private void setSwitchMarkerIcon(TMapMarkerItem item, boolean value) {
        if(value) {
            item.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_click));
        } else {
            item.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_unclick));
        }
    }

    /**
     * 마커를 클릭했을때 이미지와 타이틀이 보이는 레이아웃 활성화
     */
    private void setBottomInfoLayout() {
        mBottomInfoRootLayout.setVisibility(View.VISIBLE);
        mBottomBriefRootLayout.setVisibility(View.GONE);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mGuideline.getLayoutParams();
        params.guidePercent = 0.80f;
        mGuideline.setLayoutParams(params);
    }

    /**
     * 검색 결과물의 갯수를 보여주는 레이아웃 활성화
     */
    private void setBottomBriefLayout() {
        mBottomInfoRootLayout.setVisibility(View.GONE);
        mBottomBriefRootLayout.setVisibility(View.VISIBLE);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mGuideline.getLayoutParams();
        params.guidePercent = 0.90f;
        mGuideline.setLayoutParams(params);
    }

    private void setDefaultSetting() {
        /* VISIBLE */
        mBottomInfoRootLayout.setVisibility(View.GONE);
        mBottomBriefRootLayout.setVisibility(View.VISIBLE);
        mBottomListRootLayout.setVisibility(View.GONE);

        /* 마커와 실데이터를 연결시켜줄 HashMap */
        mItemHash = new HashMap<>();

        /* ListView 생성 */
        mListFragment = new ListFragment();
        mListFragment.setStartItem(mStartItem);
        mListFragment.setCallback(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(mBottomListRootLayout.getId(), mListFragment);
        fragmentTransaction.commit();
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    private void layoutInit() {
        mRootConstraintLayout = (ConstraintLayout)findViewById(R.id.cl_root_constraint);
        mRootLayout = (RelativeLayout)findViewById(R.id.rl_tmap_view_detail);
        mBottomInfoRootLayout = (RelativeLayout)findViewById(R.id.iv_tmap_info_rootview);
        mBottomBriefRootLayout = (RelativeLayout)findViewById(R.id.iv_tmap_brief_rootview);
        mBtnCategory1 = (ImageButton)findViewById(R.id.btn_category_1);
        mBtnCategory2 = (ImageButton)findViewById(R.id.btn_category_2);
        mBtnCategory3 = (ImageButton)findViewById(R.id.btn_category_3);
        mBtnCategory4 = (ImageButton)findViewById(R.id.btn_category_4);
        mBtnCategory5 = (ImageButton)findViewById(R.id.btn_category_5);
        mSelectedItemImg = (ImageView)findViewById(R.id.iv_tmap_info_image);
        mSelectedItemTitle = (TextView)findViewById(R.id.tv_tmap_info_title);
        mSelectedItemAddress = (TextView)findViewById(R.id.tv_tmap_info_address);
        mBottomBriefTitle = (TextView)findViewById(R.id.tv_tmap_brief_title);
        mGuideline = (Guideline)findViewById(R.id.activity_tmap_detail_guideline_horizontal);
        mRotateLoding = (RotateLoading)findViewById(R.id.rotateloading);

        mBtnCategory1.setOnClickListener(this);
        mBtnCategory2.setOnClickListener(this);
        mBtnCategory3.setOnClickListener(this);
        mBtnCategory4.setOnClickListener(this);
        mBtnCategory5.setOnClickListener(this);
        mBottomInfoRootLayout.setOnClickListener(this);
        mBottomBriefRootLayout.setOnClickListener(this);

        Intent intent = getIntent();
        mCategoryNo = intent.getIntExtra("category", -1);
        mStartItem = (AutoCompleteItem)intent.getSerializableExtra("startItem");
        mEndItem = (AutoCompleteItem)intent.getSerializableExtra("endItem");
    }

    private void dynamicLayoutInit() {
        mBottomListRootLayout = new RelativeLayout(this);
        mBottomListRootLayout.setId(View.generateViewId());
        mBottomListRootLayout.setBackgroundColor(Color.WHITE);

        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        newParams.setMargins(30, 30, 30, 30);

        mRootConstraintLayout.addView(mBottomListRootLayout, newParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mRootConstraintLayout);

        constraintSet.connect(mBottomListRootLayout.getId(), ConstraintSet.TOP, mRootConstraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(mBottomListRootLayout.getId(), ConstraintSet.BOTTOM, mRootConstraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mBottomListRootLayout.getId(), ConstraintSet.LEFT, mRootConstraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(mBottomListRootLayout.getId(), ConstraintSet.RIGHT, mRootConstraintLayout.getId(), ConstraintSet.RIGHT);

        constraintSet.applyTo(mRootConstraintLayout);
    }

    private void categoryInit() {
        switch (mCategoryNo) {
            case 1:
                mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
                mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
                mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
                mBtnCategory5.setVisibility(View.GONE);
                break;

            case 2:
                mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_unclick);
                mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_unclick);
                mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_unclick);
                mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
                break;

            case 3:
                mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_unclick);
                mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_unclick);
                mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_unclick);
                mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
                break;

            default:
                mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
                mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
                mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
                mBtnCategory5.setVisibility(View.GONE);
                break;
        }
    }

    private void TMapInit() {
        mRootLayout.addView(mTMapView);
        mTMapView.setOnClickListenerCallBack(this);
    }

    private void LoadData() {
        mRotateLoding.start();
        mPresenter = new TMapDetailPresenterImpl(this);
        mPresenter.loadData(mCategoryNo, mStartItem, mEndItem);
    }

    private void clickIconCategory1() {
        List<TourList> lists = items;
        updateMarkerAndList(lists);

        if(mCategoryNo == 1) {
            mBottomBriefTitle.setText("총 (" + mTotalCount + ") 건의 ( 음식점 ) 정보 목록보기 ");
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_click);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
            mBtnCategory5.setVisibility(View.GONE);

        } else if(mCategoryNo == 2) {
            mBottomBriefTitle.setText("총 (" + mTotalCount + ") 건의 ( 관광지 ) 정보 목록보기 ");
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_click);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        } else if(mCategoryNo == 3) {
            mBottomBriefTitle.setText("총 (" + mTotalCount + ") 건의 ( 편의시설 ) 정보 목록보기 ");
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_click);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
        }
    }

    private void clickIconCategory2() {
        List<TourList> lists = items;

        if(mCategoryNo == 1) {
            List<TourList> temp = new ArrayList<>();

            for(TourList item : lists) {
                if(!item.getCat3().equals("A05020900") &&
                        !item.getCat3().equals("A05021000"))
                    temp.add(item);
            }

            updateMarkerAndList(temp);
            mBottomBriefTitle.setText("총 (" + temp.size() + ") 건의 ( 맛집 ) 정보 목록보기 ");

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_click);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
            mBtnCategory5.setVisibility(View.GONE);

        } else if(mCategoryNo == 2) {
            List<TourList> temp = new ArrayList<>();

            for(TourList item : lists) {
                if(item.getCat1().equals("A01"))
                    temp.add(item);
            }

            updateMarkerAndList(temp);
            mBottomBriefTitle.setText("총 (" + temp.size() + ") 건의 ( 자연 ) 정보 목록보기 ");

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_click);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        } else if(mCategoryNo == 3) {
            if(mCategoryNo == 3) {
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                return;
            }

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_click);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        }
    }

    private void clickIconCategory3() {
        List<TourList> lists = items;

        if(mCategoryNo == 1) {
            List<TourList> temp = new ArrayList<>();

            for(TourList item : lists) {
                if(item.getCat3().equals("A05020900") ||
                        item.getCat3().equals("A05021000"))
                    temp.add(item);
            }

            updateMarkerAndList(temp);
            mBottomBriefTitle.setText("총 (" + temp.size() + ") 건의 ( 카페 ) 정보 목록보기 ");

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_click);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
            mBtnCategory5.setVisibility(View.GONE);

        } else if(mCategoryNo == 2) {
            List<TourList> temp = new ArrayList<>();

            for(TourList item : lists) {
                if(item.getCat1().equals("A02"))
                    temp.add(item);
            }

            updateMarkerAndList(temp);
            mBottomBriefTitle.setText("총 (" + temp.size() + ") 건의 ( 인문 ) 정보 목록보기 ");

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_click);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        } else if(mCategoryNo == 3) {
            if(mCategoryNo == 3) {
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                return;
            }

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_click);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        }
    }

    private void clickIconCategory4() {
        List<TourList> lists = items;

        if(mCategoryNo == 1) {
            if(mCategoryNo == 1) {
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                return;
            }

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_click);
            mBtnCategory5.setVisibility(View.GONE);

        } else if(mCategoryNo == 2) {
            if(mCategoryNo == 2) {
                Toast.makeText(this, "준비중...", Toast.LENGTH_SHORT).show();
                return;
            }

            // CAT1 A3 로 분류

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_click);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        } else if(mCategoryNo == 3) {
            if(mCategoryNo == 3) {
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                return;
            }

            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_click);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_unclick);

        }
    }

    private void clickIconCategory5() {
        List<TourList> lists = items;
        updateMarkerAndList(lists);
        mBottomBriefTitle.setText("총 (" + mTotalCount + ") 건의 ( " + lists.size() + " ) 정보 목록보기 ");

        if(mCategoryNo == 1) {
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
            mBtnCategory5.setVisibility(View.GONE);

        } else if(mCategoryNo == 2) {
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_nature_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_humanities_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_leport_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_click);

        } else if(mCategoryNo == 3) {
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_mart_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_hospital_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_atm_unclick);
            mBtnCategory5.setBackgroundResource(R.mipmap.icon_category_etc_click);

        } else {
            mBtnCategory1.setBackgroundResource(R.mipmap.icon_category_all_unclick);
            mBtnCategory2.setBackgroundResource(R.mipmap.icon_category_restaurant_unclick);
            mBtnCategory3.setBackgroundResource(R.mipmap.icon_category_cafe_unclick);
            mBtnCategory4.setBackgroundResource(R.mipmap.icon_category_etc_unclick);
            mBtnCategory5.setVisibility(View.GONE);
        }
    }

    private void showStartAndEndMarker() {
        if(mStartItem == null || mEndItem == null)
            return;

        TMapMarkerItem start = new TMapMarkerItem();
        start.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_here_line));
        start.setTMapPoint(new TMapPoint(mStartItem.getLatitude(), mStartItem.getLongitude()));
        start.setVisible(start.VISIBLE);
        mTMapView.addMarkerItem("startPoint", start);

        TMapMarkerItem end = new TMapMarkerItem();
        end.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_there_line));
        end.setTMapPoint(new TMapPoint(mEndItem.getLatitude(), mEndItem.getLongitude()));
        end.setVisible(end.VISIBLE);
        mTMapView.addMarkerItem("endPoint", end);
    }
}
