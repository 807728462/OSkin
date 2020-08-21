package com.oyf.skin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.oyf.skin.R;
import com.oyf.skin_lib.SkinResources;
import com.oyf.skin_lib.Interface.SkinViewSupport;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 10:32
 * @描述
 **/
public class CustomTableLayout extends TabLayout implements SkinViewSupport {
    int tabIndicatorColorResId;
    int tabTextColorResId;

    public CustomTableLayout(Context context) {
        this(context, null, 0);
    }

    public CustomTableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,
                defStyleAttr, 0);
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
        a.recycle();
    }


    @Override
    public void changeSkin() {
        if (tabIndicatorColorResId != 0) {
            int tabIndicatorColor = SkinResources.getInstance().getColor(tabIndicatorColorResId);
            setSelectedTabIndicatorColor(tabIndicatorColor);
        }

        if (tabTextColorResId != 0) {
            ColorStateList tabTextColor = SkinResources.getInstance().getColorStateList(tabTextColorResId);
            setTabTextColors(tabTextColor);
        }
    }
}
