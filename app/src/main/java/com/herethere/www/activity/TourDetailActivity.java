package com.herethere.www.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herethere.www.BuildConfig;
import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.Entity.naver.NaverImage;
import com.herethere.www.Entity.tour.TourDetail;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.R;
import com.herethere.www.adapter.NaverBlogAdapter;
import com.herethere.www.adapter.TourDetailImagesAdapter;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.NaverBlogAdapterCallback;
import com.herethere.www.common.TextViewWithIconCallback;
import com.herethere.www.common.TourDetailPresenter;
import com.herethere.www.common.recyclerview.CustomDividerItemDecoration;
import com.herethere.www.common.textview.TextViewWithIcon;
import com.herethere.www.tour.TourDetailPresenterImpl;
import com.herethere.www.util.BaseTMapActivity;
import com.skp.Tmap.TMapTapi;

import java.util.ArrayList;
import java.util.List;

public class TourDetailActivity extends AppCompatActivity implements View.OnClickListener,
                                                                        TourDetailPresenter.View,
                                                                        NaverBlogAdapterCallback,
                                                                        TextViewWithIconCallback {

    public static final int REQUEST_PERMISSIONS_REQUEST_CODE  = 99;

    private final int ALL_MARGIN = 32;
    private final int TOP_MARGIN = 32;
    private final int BOTTOM_MARGIN = 32;
    private final int LEFT_MARGIN = 64;
    private final int RIGHT_MARGIN = 64;

    private TourDetailPresenterImpl mTourDetailPresenter;
    private ViewPager mViewPager;
    private ConstraintLayout mRootConstraintLayout;
    private ImageButton mBtnWayToGo;
    private ImageButton mBtnViewMap;
    private ImageButton mImagesLeftBtn;
    private ImageButton mImagesRightBtn;
    private TextView mTitle;
    private ArrayList<NaverImage> mImageList;
    private View mPrev;

    private TourDetailImagesAdapter mTourDetailImagesAdapter;
    private TourList mTourList;
    private AutoCompleteItem mStartItem;
    private List<NaverBlog> mBlogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        actionBarInit();
        layoutInit();
        setDefaultSetting();
        loadImageData();
        loadData();
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
        Intent intent = null;

        switch (v.getId()) {
            case R.id.ib_images_left_btn:
                int currentItem = mViewPager.getCurrentItem();
                if(currentItem > 0)
                    mViewPager.setCurrentItem(--currentItem, false);
                break;

            case R.id.ib_images_right_btn:
                currentItem = mViewPager.getCurrentItem();
                if(currentItem < mImageList.size() -1 )
                    mViewPager.setCurrentItem(++currentItem, false);
                break;

            case R.id.btn_find_way_to_go:
                TMapTapi tmaptapi = new TMapTapi(this);
                tmaptapi.setSKPMapAuthentication (BaseTMapActivity.TMAP_API_KEY);
                boolean isTmapApp = tmaptapi.isTmapApplicationInstalled();

                intent = new Intent(Intent.ACTION_VIEW);
                if(!isTmapApp) {
                    try {
                        intent.setData(Uri.parse("market://details?id=com.skt.tmap.ku"));
                        startActivity(intent);
                    }catch (Exception e) {
                        Toast.makeText(this, "google play store를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                boolean result = tmaptapi.invokeRoute(mTitle.getText().toString() , (float)mTourList.getResLon(), (float)mTourList.getResLat());
                if(!result)
                    Toast.makeText(this, "TMap 연결 불가능", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_view_map:
                intent = new Intent(this, MapViewActivity.class);
                intent.putExtra("title", mTitle.getText());
                intent.putExtra("lat", mTourList.getResLat());
                intent.putExtra("lon", mTourList.getResLon());
                startActivity(intent);
                break;

        }
    }

    @Override
    public void getImages(List<NaverImage> imageList) {
        if(imageList == null)
            return;

        mImageList.addAll(imageList);
        mTourDetailImagesAdapter.setImageList(mImageList);
        mTourDetailImagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData(TourDetail data) {
        if(data == null)
            return;

        drawDetailData(data);
        loadBlogData();
    }

    @Override
    public void getBlogData(List<NaverBlog> blogList) {
        if(blogList == null)
            return;

        drawBlogData(blogList);
    }

    @Override
    public void sendUrl(String link) {
        Intent intent = new Intent(this, BlogDetailActivity.class);
        intent.putExtra("url", link);
        startActivity(intent);
    }

    @Override
    public void showMore() {
        ArrayList<NaverBlog> naverBlogLists = new ArrayList<>();
        naverBlogLists.addAll(mBlogList);

        Intent intent = new Intent(this, BlogListActivity.class);
        intent.putExtra("blogList", naverBlogLists);
        startActivity(intent);
    }

    @Override
    public void callAction(String tel) {
        if(!checkCallPhonePermissions() && Build.VERSION.SDK_INT >= 23)
            requestPermissions();
        else
            startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
    }

    private void loadImageData() {
        mTourDetailPresenter.loadImageData(mTourList.getResName());
    }

    private void loadData() {
        mTourDetailPresenter.loadData(mTourList, mStartItem);
    }

    private void loadBlogData() {
        mTourDetailPresenter.loadBlogData(mTourList.getResName());
    }

    private void drawDetailData(TourDetail data) {

        View line = new View(this);
        line.setId(View.generateViewId());
        line.setBackgroundColor(Color.DKGRAY);
        addViewBelowForLine(mRootConstraintLayout, line, mTitle, ALL_MARGIN);

        ArrayList<TextViewWithIcon> textLists = null;
        if(data.getResAPIKind() == 10) {
            textLists = data.makeTextViewWithTourApi(this, data, mTourList, this);
        } else {
            textLists = data.makeTextViewWithTMapApi(this, data, mTourList, this);
        }

        View detailView = addNewRelativeLayoutView(textLists);
        addViewBelow(mRootConstraintLayout, detailView, line, ALL_MARGIN);

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        /* 리뷰 타이틀 나중에 디자인 수정하기*/
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(View.generateViewId());

        String reviewTitleStr = "리뷰";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(reviewTitleStr);
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, reviewTitleStr.length(), 0);

        TextView reviewTitle = new TextView(this);
        reviewTitle.setId(View.generateViewId());
        reviewTitle.setTextSize(15);
        reviewTitle.setText(builder);

        addViewAlignParentTop(relativeLayout, reviewTitle, ALL_MARGIN, ALL_MARGIN, 0, 0);
        addViewBelow(mRootConstraintLayout, relativeLayout, detailView, ALL_MARGIN);
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        mPrev = relativeLayout;
    }

    private void drawBlogData(List<NaverBlog> blogList) {
        if(blogList == null)
            return;

        mBlogList = blogList;
        
        View line = new View(this);
        line.setId(View.generateViewId());
        line.setBackgroundColor(Color.DKGRAY);
        addViewBelowForLine(mRootConstraintLayout, line, mPrev, ALL_MARGIN);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        NaverBlogAdapter adapter = new NaverBlogAdapter(this, blogList);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setId(View.generateViewId());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomDividerItemDecoration(this));

        addViewBelow(mRootConstraintLayout, recyclerView, line, ALL_MARGIN);
        mPrev = recyclerView;
    }

    private View addNewRelativeLayoutView(ArrayList<TextViewWithIcon> TextViewWithIcons) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(View.generateViewId());

        TextViewWithIcon preTextView = null;

        for(TextViewWithIcon textViewWithIcon : TextViewWithIcons) {
            if(textViewWithIcon == null)
                continue;

            if(preTextView == null) {
                addViewAlignParentTop(relativeLayout, textViewWithIcon);
                preTextView = textViewWithIcon;
            } else {
                addViewBelow(relativeLayout, textViewWithIcon, preTextView.getId());
                preTextView= textViewWithIcon;
            }
        }

        return relativeLayout;
    }

    /**
     * targetOfCriterion View 아래에 targetOfAdding View를 추가
     * ConstraintLayout 전용
     * @param parent
     * @param targetOfAdding            추가할 뷰
     * @param targetOfCriterion         기준이 되는 뷰.
     * @param margin
     */
    private void addViewBelow(ConstraintLayout parent, View targetOfAdding, View targetOfCriterion, int margin) {
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        parent.addView(targetOfAdding, newParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parent);

        constraintSet.connect(targetOfAdding.getId(), ConstraintSet.TOP, targetOfCriterion.getId(), ConstraintSet.BOTTOM, margin);
        constraintSet.applyTo(parent);
    }

    /**
     * targetOfCriterion View 아래에 line View를 추가
     * ConstraintLayout 전용
     * @param parent
     * @param targetOfAdding            추가할 뷰
     * @param targetOfCriterion         기준이 되는 뷰.
     * @param margin
     */
    private void addViewBelowForLine(ConstraintLayout parent, View targetOfAdding, View targetOfCriterion, int margin) {
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 1);

        newParams.setMargins(margin, margin, margin, margin);
        parent.addView(targetOfAdding, newParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parent);
        constraintSet.connect(targetOfAdding.getId(), ConstraintSet.TOP, targetOfCriterion.getId(), ConstraintSet.BOTTOM);
        constraintSet.applyTo(parent);
    }

    /**
     * RelativeLayout 기준 View 아래에 추가
     * RelativeLayout 전용
     * @param view
     * @param targetView          추가할 뷰
     * @param id                 기준이 되는 뷰의 id
     */
    private void addViewBelow(RelativeLayout view, View targetView, int id) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, id);
        params.setMargins(LEFT_MARGIN, TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN);

        view.addView(targetView, params);
    }

    /**
     * RelativeLayout 뷰 상단에 추가
     * RelativeLayout 전용
     * @param view
     * @param targetView            추가할 뷰
     */
    private void addViewAlignParentTop(RelativeLayout view, View targetView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(LEFT_MARGIN, 0, RIGHT_MARGIN, BOTTOM_MARGIN);

        view.addView(targetView, params);
    }

    /**
     * RelativeLayout 뷰 상단에 추가 + margin
     * RelativeLayout 전용
     */
    private void addViewAlignParentTop(RelativeLayout view, View targetView, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        view.addView(targetView, params);
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    private void layoutInit() {
        mViewPager = (ViewPager)findViewById(R.id.vp_tour_images);
        mRootConstraintLayout = (ConstraintLayout)findViewById(R.id.cl_activity_tour_detail_root_constraint);
        mImagesLeftBtn = (ImageButton)findViewById(R.id.ib_images_left_btn);
        mImagesRightBtn = (ImageButton)findViewById(R.id.ib_images_right_btn);
        mBtnWayToGo = (ImageButton)findViewById(R.id.btn_find_way_to_go);
        mBtnViewMap = (ImageButton)findViewById(R.id.btn_view_map);
        mTitle = (TextView)findViewById(R.id.tv_activity_tour_detail_title);

        mImagesLeftBtn.setOnClickListener(this);
        mImagesRightBtn.setOnClickListener(this);
        mBtnWayToGo.setOnClickListener(this);
        mBtnViewMap.setOnClickListener(this);
    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        mTourList = (TourList)intent.getSerializableExtra("tourList");
        mStartItem = (AutoCompleteItem)intent.getSerializableExtra("startItem");
        mTitle.setText(mTourList.getResName());

        mTourDetailPresenter = new TourDetailPresenterImpl(this);

        mImageList = new ArrayList<>();
        mTourDetailImagesAdapter = new TourDetailImagesAdapter(this, mImageList);
        mViewPager.setAdapter(mTourDetailImagesAdapter);
    }

    private boolean checkCallPhonePermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startCallPhonePermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE);
        if (shouldProvideRationale) {
            //사용자가 한번 권한을 거절한 경우 또는 '다시묻지않음'을 체크하여 거절한 경우
            new AlertDialog.Builder(this)
                    .setTitle("전화 퍼미션이 필요합니다.")
                    .setMessage("이 앱은 전화 퍼미션이 필요합니다, 전화 사용을 수락해주세요.")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startCallPhonePermissionRequest();
                        }
                    })
                    .create()
                    .show();
        } else {
            startCallPhonePermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                    // 사용자가 취소를 누른 경우
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    // 사용자가 '다시묻지않음' 체크를 누른 경우 환경설정에서 수동 변경
                    new AlertDialog.Builder(this)
                            .setTitle("전화 퍼미션이 필요합니다.")
                            .setMessage("이 앱은 전화 퍼미션이 필요합니다, 전화 사용을 수락해주세요.")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .create()
                            .show();
                }
            }
        }
    }

    private void callPhone() {
        /* 한번에 통화 가도록 수정하기 */
    }
}
