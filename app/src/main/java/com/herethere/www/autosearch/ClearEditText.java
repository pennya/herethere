package com.herethere.www.autosearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.herethere.www.R;

/**
 * Created by KIM on 2017-09-07.
 */

public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener{

    private Drawable mLogoDrawable;
    private Drawable mClearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;
    private AutoCompleteAdapter dataAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper() /*UI thread*/);
    private Runnable mWorkRunnable;
    private final long DELAY = 500; // milliseconds

    public void setListViewAdpater(AutoCompleteAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
    }

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_cancle);
        mClearDrawable = DrawableCompat.wrap(tempDrawable);
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        setClearIconVisible(false);

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private void setSearchIconVisible() {
        mLogoDrawable.setVisible(true, false);
        setCompoundDrawables(mLogoDrawable, null, null, null);
    }

    private void setClearIconVisible(boolean visible) {
        mClearDrawable.setVisible(visible, false);
        setCompoundDrawables(mLogoDrawable , null, visible ? mClearDrawable : null, null);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // not use
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        final String keyword = s.toString();

        mHandler.removeCallbacks(mWorkRunnable);
        mWorkRunnable = new Runnable() {
            @Override
            public void run() {
                dataAdapter.filter(keyword);
            }
        };
        mHandler.postDelayed(mWorkRunnable, DELAY);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }

        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int x = (int)event.getX();
        if(mClearDrawable.isVisible() && x > getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText(null);
            }
            return true;
        }

        if(onTouchListener != null) {
            return onTouchListener.onTouch(v, event);
        } else {
            return false;
        }
    }

    public void setResource(int resource) {
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), resource);
        mLogoDrawable = DrawableCompat.wrap(tempDrawable);
        mLogoDrawable.setBounds(0, 0, mLogoDrawable.getIntrinsicWidth(), mLogoDrawable.getIntrinsicHeight());
        setSearchIconVisible();
    }
}
