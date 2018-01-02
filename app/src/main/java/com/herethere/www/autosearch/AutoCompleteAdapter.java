package com.herethere.www.autosearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herethere.www.R;
import com.herethere.www.common.SearchActivityCallback;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by KJH on 2017-09-06.
 */

public class AutoCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SearchActivityCallback mActivityCallback;
    private ArrayList<AutoCompleteItem> mSuggestions;

    public AutoCompleteAdapter(SearchActivityCallback activityCallback) {
        mActivityCallback = activityCallback;
        mSuggestions = new ArrayList<AutoCompleteItem>();
    }

    public void setList(ArrayList<AutoCompleteItem> list) {
        mSuggestions = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tv_search_title);
            address = (TextView)itemView.findViewById(R.id.tv_search_address);
        }
    }

    public void filter(String charText) {
        mSuggestions.clear();

        if (charText.length() >= 2) {
            try {
                AutoCompleteParse acp = new AutoCompleteParse(this);
                mSuggestions.addAll(acp.execute(charText).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int ItemPosition = position;

        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCallback.sendSearchedData(mSuggestions.get(ItemPosition));
            }
        });

        if(mSuggestions.size() != 0) {
            viewHolder.title.setText(mSuggestions.get(ItemPosition).getTitle());
            viewHolder.address.setText(mSuggestions.get(ItemPosition).getAddress());
        }
    }

    @Override
    public int getItemCount() {
        if(mSuggestions != null)
            return mSuggestions.size();
        else
            return 0;
    }
}
