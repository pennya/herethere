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

public class NaverBlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private final int MAX_COUNT = 5;

    private NaverBlogAdapterCallback mCallback;
    private List<NaverBlog> mList;

    public NaverBlogAdapter(NaverBlogAdapterCallback mCallback) {
        this.mCallback = mCallback;
        mList = new ArrayList<>();
    }

    public NaverBlogAdapter(NaverBlogAdapterCallback mCallback, List<NaverBlog> mList) {
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

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public FooterViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.lv_blog_more_item_more);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_blog_item, parent, false);
            return new ViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_blog_more_item, parent, false);
            return new FooterViewHolder(view);
        }
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

        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder)holder;
            footerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.showMore();
                }
            });

            footerViewHolder.title.setText(mList.size() + "개 더보기");
        }

    }

    @Override
    public int getItemCount() {
        if(mList.size() >= MAX_COUNT )
            return MAX_COUNT + 1;
        else
            return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionFooter(position))
            return TYPE_FOOTER;

        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        if(mList.size() >= MAX_COUNT )
            return position == MAX_COUNT;
        else
            return position == mList.size();
    }
}
