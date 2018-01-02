package com.herethere.www.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.R;
import com.herethere.www.activity.TourDetailActivity;
import com.herethere.www.adapter.ListAdapter;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.CustomDialogCallback;
import com.herethere.www.common.ListAdapterCallback;
import com.herethere.www.common.ListFragmentCallback;
import com.herethere.www.dialog.CustomDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ListFragment extends Fragment implements View.OnClickListener,
                                                    CustomDialogCallback,
                                                    ListAdapterCallback{

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private ArrayList<TourList> mArrayList;
    private Button mBtnMapCheck;
    private Button mBtnSelect;
    private TextView mDataText;
    private TextView mRightSort;
    private TextView mSortText;
    private TextView mLeftSort;
    private CheckBox mCheckBoxAll;
    private TextView mCheckBoxText;
    private ListFragmentCallback mCallback;

    private CustomDialog mCustomDialog;

    private ArrayList<Integer> checkedArrayList;
    private boolean isVisibleForCheckView = false;
    private AutoCompleteItem mStartItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_map_list, container, false);

        layoutInit(view);
        setDefaultSetting(view);

        return view;
    }

    @Override
    public void selectAgain() {
        int random = (new Random()).nextInt(checkedArrayList.size());
        mCustomDialog.setData(mArrayList.get(checkedArrayList.get(random)));
    }

    @Override
    public void moveDetailView(TourList tourList) {
        Intent intent = new Intent(getActivity(), TourDetailActivity.class);
        intent.putExtra("tourList", tourList);
        intent.putExtra("startItem", mStartItem);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void moveDetailView(int position) {
        Intent intent = new Intent(getActivity(), TourDetailActivity.class);
        intent.putExtra("tourList", mArrayList.get(position));
        intent.putExtra("startItem", mStartItem);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void ready() {
        int random = (new Random()).nextInt(checkedArrayList.size());
        mCustomDialog.setData(mArrayList.get(checkedArrayList.get(random)), mStartItem);
    }

    @Override
    public void clear() {
        checkedArrayList.clear();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_map_select :
                showRandom();
                break;

            case R.id.btn_map_check :
                showSortGroup();

                if(!isVisibleForCheckView)
                    mCallback.back();

                isVisibleForCheckView = false;
                break;

            case R.id.cb_map_all :
                mListAdapter.setAllChecked(mCheckBoxAll.isChecked());
                mListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void setDefaultSetting(View view) {
        checkedArrayList = new ArrayList<>();

        setCheckBoxGroupVisible(View.GONE);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_activity_map_list_recyclerview);
        mArrayList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mListAdapter = new ListAdapter(getActivity(), this);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void layoutInit(View view) {
        mBtnSelect = (Button)view.findViewById(R.id.btn_map_select);
        mBtnMapCheck = (Button)view.findViewById(R.id.btn_map_check);
        mDataText = (TextView)view.findViewById(R.id.tv_map_data);
        mRightSort = (TextView)view.findViewById(R.id.tv_map_rightsort);
        mSortText = (TextView)view.findViewById(R.id.tv_map_sort);
        mLeftSort = (TextView)view.findViewById(R.id.tv_map_leftsort);
        mCheckBoxAll = (CheckBox)view.findViewById(R.id.cb_map_all);
        mCheckBoxText = (TextView)view.findViewById(R.id.tv_map_all);

        mBtnSelect.setOnClickListener(this);
        mBtnMapCheck.setOnClickListener(this);
        mCheckBoxAll.setOnClickListener(this);
    }

    public void setList(ArrayList<TourList> list) {
        /* 페이지 별로 받을 것이므로 그 전 데이터는 없애고 다시 받는다.*/
        mArrayList.clear();

        /* 추천순 정렬 */
        Comparator<TourList> comparator = new Comparator<TourList>() {
            @Override
            public int compare(TourList o1, TourList o2) {
                return o1.getReadcount() > o2.getReadcount() ? -1 : o1.getReadcount() < o2.getReadcount() ? 1 : 0;
            }
        };
        Collections.sort(list, comparator);

        mArrayList = list;
        mListAdapter.setList(mArrayList);
        mListAdapter.notifyDataSetChanged();

        mDataText.setText(mArrayList.size() + "");
    }

    private void setCheckBoxGroupVisible(int value) {
        mCheckBoxAll.setVisibility(value);
        mCheckBoxText.setVisibility(value);
    }

    private void setSortGroupVisible(int value) {
        mRightSort.setVisibility(value);
        mSortText.setVisibility(value);
        mLeftSort.setVisibility(value);
    }

    private void showCheckBoxGroup() {
        mBtnSelect.setText(R.string.random);
        mBtnMapCheck.setText(R.string.cancel);
        setCheckBoxGroupVisible(View.VISIBLE);
        setSortGroupVisible(View.GONE);
        mListAdapter.setVisible(true);
        mListAdapter.notifyDataSetChanged();
    }

    public void showSortGroup() {
        mBtnSelect.setText(R.string.select);
        mBtnMapCheck.setText(R.string.checkmap);
        setCheckBoxGroupVisible(View.GONE);
        setSortGroupVisible(View.VISIBLE);
        mListAdapter.setVisible(false);
        mListAdapter.notifyDataSetChanged();
    }

    private void showRandom() {
        boolean checkedList[] = mListAdapter.getIsCheckedConfirm();
        for(int num = 0; num < checkedList.length; num++) {
            if(checkedList[num])
                checkedArrayList.add(num);
        }

        if(checkedArrayList.size() != 0 && isVisibleForCheckView) {
            mCustomDialog = new CustomDialog();
            mCustomDialog.setCallback(this);
            mCustomDialog.show(getFragmentManager(),"dialog");
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else {
            if(isVisibleForCheckView) {
                showSortGroup();
            } else {
                showCheckBoxGroup();
            }

            isVisibleForCheckView = !isVisibleForCheckView;
        }
    }

    public void setStartItem(AutoCompleteItem startItem) {
        mStartItem = startItem;
    }

    public void setCallback(ListFragmentCallback callback) {
        mCallback = callback;
    }
}
