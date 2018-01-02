package com.herethere.www.util;

import android.content.Context;
import android.view.View;

import com.herethere.www.R;
import com.herethere.www.common.TextViewWithIconCallback;
import com.herethere.www.common.textview.TextViewWithIcon;

/**
 * Created by KIM on 2017-10-31.
 */

public class TextViewWithIconHelper {

    public static TextViewWithIcon newTextViewWithIcon(Context context, int id, String msg) {
        if(msg == null)
            return null;

        return new TextViewWithIcon(context)
                .setIconRes(id)
                .setTitle(msg)
                .Build();
    }

    public static TextViewWithIcon newTextViewWithIcon(Context context, int id, String title, String desc, TextViewWithIconCallback callback) {
        if(desc == null || desc.equals("null"))
            return null;

        TextViewWithIcon textview = new TextViewWithIcon(context)
                .setIconRes(id)
                .setTitle(title, desc)
                .Build();

        /* 전화 링크 추가 , 전화 퍼미션 추가, 전화 걸기 */
        if(id == R.mipmap.icon_information_call) {
            textview = textview.setTitleUnderline(title, desc);

            final TextViewWithIconCallback fCallback = callback;
            final String tel = "tel:" + desc;
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fCallback.callAction(tel);
                }
            });
        }

        return textview;
    }
}
