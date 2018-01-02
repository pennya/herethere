package com.herethere.www.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herethere.www.Entity.naver.NaverBlog;
import com.herethere.www.R;
import com.herethere.www.common.NaverBlogAdapterCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KJH on 2017-10-23.
 */

public class NaverBlogAdapterEx extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_ITEM = 1;

    private NaverBlogAdapterCallback mCallback;
    private List<NaverBlog> mList;

    public NaverBlogAdapterEx(NaverBlogAdapterCallback mCallback) {
        this.mCallback = mCallback;
        mList = new ArrayList<>();
    }

    public NaverBlogAdapterEx(NaverBlogAdapterCallback mCallback, List<NaverBlog> mList) {
        this.mCallback = mCallback;
        this.mList = mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.lv_blog_item_title);
            author = (TextView)itemView.findViewById(R.id.lv_blog_item_author);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_blog_item, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int ItemPosition = position;

        if(holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.sendUrl(mList.get(ItemPosition).getLink());
                }
            });

            if(mList.size() != 0) {
                viewHolder.title.setText(mList.get(ItemPosition).getTitle().replace("<b>", "").replace("</b>", ""));
                viewHolder.author.setText(mList.get(ItemPosition).getBloggername());
            }

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }
}
