package com.herethere.www.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.R;
import com.herethere.www.common.ListAdapterCallback;

import java.util.ArrayList;

/**
 * Created by jnshim on 2017-09-19.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ListAdapterCallback callback;
    private Context context;
    private ArrayList<TourList> arrayList = new ArrayList<>();
    private boolean[] isCheckedConfirm;

    private boolean isVisible = false;

    public ListAdapter(Context context, ListAdapterCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int ItemPosition = position;

        if( holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder)holder;

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isVisible)
                        callback.moveDetailView(ItemPosition);
                }
            });

            RequestOptions requestOptions = new RequestOptions()
                    .fitCenter()
                    .dontAnimate()
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.image_loding);

            Glide.with(context)
                    .load(arrayList.get(ItemPosition).getFirstimage())
                    .apply(requestOptions)
                    .into(viewHolder.pic_imageView);

            viewHolder.title_textView.setText(arrayList.get(ItemPosition).getResName());
            viewHolder.content_textView.setText(arrayList.get(ItemPosition).getResAddress());

            if (isVisible) {
                viewHolder.random_checkBox.setVisibility(View.VISIBLE);
                viewHolder.random_checkBox.setChecked(isCheckedConfirm[ItemPosition]);
                viewHolder.random_checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = isCheckedConfirm[ItemPosition];
                        isCheckedConfirm[ItemPosition] = !isChecked;
                        ((CheckBox)v).setChecked(!isChecked);
                    }
                });
            }else {
                viewHolder.random_checkBox.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic_imageView;
        TextView title_textView;
        TextView content_textView;
        CheckBox random_checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            pic_imageView = (ImageView)itemView.findViewById(R.id.iv_list);
            title_textView = (TextView)itemView.findViewById(R.id.tv_list_title);
            content_textView = (TextView)itemView.findViewById(R.id.tv_list_content);
            random_checkBox = (CheckBox)itemView.findViewById(R.id.cb_list_select);
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    // CheckBox를 모두 선택하는 메서드
    public void setAllChecked(boolean isCheked) {
        int tempSize = isCheckedConfirm.length;
        for(int a=0 ; a<tempSize ; a++){
            isCheckedConfirm[a] = isCheked;
        }
    }

    public void setList(ArrayList<TourList> list) {
        arrayList = list;
        isCheckedConfirm = new boolean[arrayList.size()];
    }

    public boolean[] getIsCheckedConfirm() {
        return isCheckedConfirm;
    }
}
