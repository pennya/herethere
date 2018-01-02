package com.herethere.www.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.herethere.www.Entity.naver.NaverImage;
import com.herethere.www.R;

import java.util.ArrayList;

/**
 * Created by KIM on 2017-10-10.
 */

public class TourDetailImagesAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<NaverImage> mImageList;

    public TourDetailImagesAdapter(Context context, ArrayList<NaverImage> imageList) {
        this.mContext = context;
        mImageList = imageList;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.content_tour_detail_images, container, false);
        ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.image_loding);

        String imagePath = mImageList.get(position).getLink();
        Glide.with(mContext)
                .load(imagePath)
                .apply(requestOptions)
                .into(imageViewPreview);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setImageList(ArrayList<NaverImage> imageList) {
        mImageList = imageList;
    }
}
