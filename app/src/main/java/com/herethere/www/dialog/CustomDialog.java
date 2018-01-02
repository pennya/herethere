package com.herethere.www.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.herethere.www.Entity.tour.AttractionDetail;
import com.herethere.www.Entity.tour.FacilDetail;
import com.herethere.www.Entity.tour.FoodDetail;
import com.herethere.www.Entity.tour.TourDetail;
import com.herethere.www.Entity.tour.TourList;
import com.herethere.www.R;
import com.herethere.www.autosearch.AutoCompleteItem;
import com.herethere.www.common.CustomDialogCallback;
import com.herethere.www.common.CustomDialogPresenter;

/**
 * Created by jnshim on 2017-10-13.
 */

public class CustomDialog extends DialogFragment implements View.OnClickListener, CustomDialogPresenter.View {

    private TextView mTextView_Title;
    private TextView mTextView_Content;
    private ImageView mImageView;
    private Button mBtn_reselect;
    private Button mBtn_detail;
    private ImageButton mBtn_close;
    private CustomDialogCallback mCallback;
    private TourList mTourList;
    private AutoCompleteItem mStartItem;
    private CustomDialogPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        layoutInit(view);
        setDefaultSetting();

        mCallback.ready();

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void getData(TourDetail data) {

        if(data == null)
            return;

        showContent(data.getResName(), data.getResAddress(), data.getFirstimage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_close:
                mCallback.clear();
                dismissDialog();
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.dialog_btn_reselect:
                mCallback.selectAgain();
                break;

            case R.id.dialog_btn_detail:
                mCallback.moveDetailView(mTourList);
                break;

        }
    }

    private void layoutInit(View view) {
        mTextView_Title = (TextView)view.findViewById(R.id.dialog_tv_title);
        mTextView_Content = (TextView)view.findViewById(R.id.dialog_tv_content);
        mImageView = (ImageView)view.findViewById(R.id.dialog_iv);
        mBtn_reselect = (Button)view.findViewById(R.id.dialog_btn_reselect);
        mBtn_detail = (Button)view.findViewById(R.id.dialog_btn_detail);
        mBtn_close = (ImageButton)view.findViewById(R.id.dialog_btn_close);

        mBtn_reselect.setOnClickListener(this);
        mBtn_detail.setOnClickListener(this);
        mBtn_close.setOnClickListener(this);
    }

    private void setDefaultSetting() {
        mPresenter = new CustomDialogPresenterImpl(this);
    }

    public void setData(TourList tourList) {
        mTourList = tourList;
        mPresenter.loadData(mTourList, mStartItem);
    }

    public void setData(TourList tourList, AutoCompleteItem startItem) {
        mTourList = tourList;
        mStartItem = startItem;
        showContent(mTourList.getResName(), mTourList.getResAddress(), mTourList.getFirstimage()); /* 리스트에 있는 정보만으로 보여주기*/

        //mPresenter.loadData(mTourList, mStartItem); /* 지금 상세 정보 가져올게 없음 나중에 사용될듯.*/
    }

    private void showContent(String title, String content, String imageUrl) {
        mTextView_Title.setText(title);
        mTextView_Content.setText(content);

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.image_loding);

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(mImageView);
    }

    private void dismissDialog() {
        this.dismiss();
    }

    public void setCallback(CustomDialogCallback callback) {
        mCallback = callback;
    }
}
