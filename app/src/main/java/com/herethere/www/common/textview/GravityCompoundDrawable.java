package com.herethere.www.common.textview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

/**
 * Created by KJH on 2017-10-22.
 */

public class GravityCompoundDrawable extends Drawable {
    private final Drawable mDrawable;

    public GravityCompoundDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        int halfCanvas = canvas.getHeight() / 2;
        int halfDrawable = mDrawable.getIntrinsicHeight() / 2;

        canvas.save(); /* 변형하기전 회전, 원점이동등의 환경을 저장한다. 권장하는 방식*/
        canvas.translate(0, -halfCanvas + halfDrawable); /*변형시키고*/
        mDrawable.draw(canvas); /*다시그린다*/
        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
