package com.herethere.www.common.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by KIM on 2017-10-12.
 */

public class TextViewWithIcon extends AppCompatTextView {
    private int mIConId;

    public TextViewWithIcon(Context context) {
        super(context);
    }

    public TextViewWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextViewWithIcon setIconRes(int id) {
        mIConId = id;
        return this;
    }

    public TextViewWithIcon Build() {
        Drawable innerDrawable = ContextCompat.getDrawable(getContext(), mIConId);
        GravityCompoundDrawable gravityDrawable = new GravityCompoundDrawable(innerDrawable);
        innerDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        gravityDrawable.setBounds(0, 0, innerDrawable.getIntrinsicWidth(), innerDrawable.getIntrinsicHeight());
        setCompoundDrawables(gravityDrawable, null, null, null);

        setId(View.generateViewId());
        setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f); /* 줄내림(\n) 간격 5dp */
        setCompoundDrawablePadding(20);
        return this;
    }

    public TextViewWithIcon setTitle(String title) {
        setText(title);
        return this;
    }

    public TextViewWithIcon setTitle(String title, String desc) {
        desc = desc.replace("<br>", " ").replace("<br/>", " ").replace("<br />", " ");

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(title + "\n" + desc);
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        setText(builder);
        return this;
    }

    public TextViewWithIcon setTitleUnderline(String title, String desc) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String text = getText().toString();
        int start = text.indexOf(desc);

        builder.append(text);

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View textView) {
                // handle click event
            }
        };

        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        builder.setSpan(clickSpan, start, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(builder);
        return this;
    }
}
