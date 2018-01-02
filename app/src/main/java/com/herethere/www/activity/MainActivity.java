package com.herethere.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.herethere.www.R;
import com.herethere.www.autosearch.AutoCompleteItem;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    private ImageView mImageViewMain;

    private ImageButton mBtnFood;
    private ImageButton mBtnTour;
    private ImageButton mBtnOther;

    private TextView mEdtStart;
    private TextView mEdtEnd;
    private Button mBtnSearch;

    private int categoryNo = 0;
    private AutoCompleteItem mStartItem;
    private AutoCompleteItem mEndItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 버튼을 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchActivity.SEARCH_ACTIVITY_REQEUST_CODE && data != null) {
            int key = data.getIntExtra("key", 0);
            AutoCompleteItem item = (AutoCompleteItem)data.getSerializableExtra("item");
            if(item != null && key == R.id.edt_start) {
                mStartItem = item;
                mEdtStart.setText(item.getTitle());
            } else {
                mEndItem = item;
                mEdtEnd.setText(item.getTitle());
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_food :
                if (categoryNo != 1) {
                    categoryNo = 1;
                    Glide.with(this).load(R.mipmap.main_bg_food).into(mImageViewMain);
                    mBtnFood.setBackgroundResource(R.mipmap.main_food_on);
                    mBtnTour.setBackgroundResource(R.mipmap.main_tour_off);
                    mBtnOther.setBackgroundResource(R.mipmap.main_other_off);
                } else {
                     selectAll();
                }

                break;
            case R.id.btn_tour :
                if (categoryNo != 2) {
                    categoryNo = 2;
                    Glide.with(this).load(R.mipmap.main_bg_tour).into(mImageViewMain);
                    mBtnFood.setBackgroundResource(R.mipmap.main_food_off);
                    mBtnTour.setBackgroundResource(R.mipmap.main_tour_on);
                    mBtnOther.setBackgroundResource(R.mipmap.main_other_off);
                } else {
                    selectAll();
                }
                break;
            case R.id.btn_other :
                if (categoryNo != 3) {
                    categoryNo = 3;
                    Glide.with(this).load(R.mipmap.main_bg_other).into(mImageViewMain);
                    mBtnFood.setBackgroundResource(R.mipmap.main_food_off);
                    mBtnTour.setBackgroundResource(R.mipmap.main_tour_off);
                    mBtnOther.setBackgroundResource(R.mipmap.main_other_on);
                } else {
                    selectAll();
                }
                break;
            case R.id.edt_start :
                intent = new Intent(this, SearchActivity.class);
                intent.putExtra("key", R.id.edt_start);
                startActivityForResult(intent, SearchActivity.SEARCH_ACTIVITY_REQEUST_CODE);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.edt_end :
                intent = new Intent(this, SearchActivity.class);
                intent.putExtra("key", R.id.edt_end);
                startActivityForResult(intent, SearchActivity.SEARCH_ACTIVITY_REQEUST_CODE);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btn_search :
                if(categoryNo == 0 || mStartItem == null || mEndItem == null) {
                    Toast.makeText(this, "카테고리, 출발지, 도착지를 설정해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mEndItem.isRoundSearch()) {
                    mEndItem.setLatitude(mStartItem.getLatitude());
                    mEndItem.setLongitude(mStartItem.getLongitude());
                }

                intent = new Intent(this, TMapDetailActivity.class);
                intent.putExtra("category", categoryNo);
                intent.putExtra("startItem", mStartItem);
                intent.putExtra("endItem", mEndItem);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    private void setLayout() {

        mImageViewMain = (ImageView)findViewById(R.id.imageView_main);

        mBtnFood = (ImageButton)findViewById(R.id.btn_food);
        mBtnTour = (ImageButton)findViewById(R.id.btn_tour);
        mBtnOther = (ImageButton)findViewById(R.id.btn_other);

        mEdtStart = (TextView)findViewById(R.id.edt_start);
        mEdtEnd = (TextView)findViewById(R.id.edt_end);
        mBtnSearch = (Button)findViewById(R.id.btn_search);

        mBtnFood.setOnClickListener(this);
        mBtnTour.setOnClickListener(this);
        mBtnOther.setOnClickListener(this);


        mEdtStart.setOnClickListener(this);
        mEdtEnd.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    private void selectAll() {
        categoryNo = 0;
        Glide.with(this).load(R.mipmap.main_bg).into(mImageViewMain);
        mBtnFood.setBackgroundResource(R.mipmap.main_food_off);
        mBtnTour.setBackgroundResource(R.mipmap.main_tour_off);
        mBtnOther.setBackgroundResource(R.mipmap.main_other_off);
    }
}
