package com.herethere.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.R;
import com.herethere.www.adapter.NaverBlogAdapterEx;
import com.herethere.www.common.NaverBlogAdapterCallback;
import com.herethere.www.common.recyclerview.CustomDividerItemDecoration;

import java.util.ArrayList;

public class BlogListActivity extends AppCompatActivity implements NaverBlogAdapterCallback {

    private RecyclerView mRecyclerView;
    ArrayList<NaverBlog> mBlogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);

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
    public void sendUrl(String link) {
        Intent intent = new Intent(this, BlogDetailActivity.class);
        intent.putExtra("url", link);
        startActivity(intent);
    }

    @Override
    public void showMore() {
        /* 사용안함 */
    }

    private void actionBarInit() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(200);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.titlebar));
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
    }

    private void layoutInit() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_activity_blog_list_recyclerview);
    }

    private void setDefaultSetting() {
        Intent intent = getIntent();
        mBlogList = (ArrayList<NaverBlog>)intent.getSerializableExtra("blogList");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        NaverBlogAdapterEx adapter = new NaverBlogAdapterEx(this, mBlogList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new CustomDividerItemDecoration(this));
    }
}
