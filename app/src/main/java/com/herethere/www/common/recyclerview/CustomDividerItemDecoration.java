package com.herethere.www.common.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.herethere.www.R;

/**
 * Created by KJH on 2017-10-23.
 */

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {
    private final int LEFT_MARGIN = 32;
    private final int RIGHT_MARGIN = 32;
    private Drawable mDivider;

    public CustomDividerItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = LEFT_MARGIN;
        int right = parent.getWidth() - RIGHT_MARGIN;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
