package com.oyf.skin_lib;

import android.util.AttributeSet;
import android.view.View;

import com.oyf.skin_lib.Interface.SkinViewSupport;
import com.oyf.skin_lib.bean.SkinPairBean;
import com.oyf.skin_lib.bean.SkinViewBean;
import com.oyf.skin_lib.utils.SkinUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 11:42
 * @描述 属性管理器，每个页面需要存储得换肤控件及属性
 **/
public class SkinAttribute {

    /**
     * 需要收集得属性
     */
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
    }

    // 把所有需要换肤的 View 和 属性信息集
    private List<SkinViewBean> mSkinViews = new ArrayList<>();

    public void collectViewInfo(View view, AttributeSet attributeSet) {
        List<SkinPairBean> skinPairBeanList = new ArrayList<>();
        int attributeCount = attributeSet.getAttributeCount();//一共有多少个属性
        for (int i = 0; i < attributeCount; i++) {
            //获取view里面的属性名称
            String attributeName = attributeSet.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {
                //获取view里面的属性得值
                String attributeValue = attributeSet.getAttributeValue(i);
                //校验值
                if (attributeValue.startsWith("#")) {
                    //输入color得时候  输入#000000 固定属性值无法换肤
                    continue;
                }
                int resId;
                if (attributeValue.startsWith("?")) {
                    //输入color得时候  输入?actionBar
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinUtil.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    // 正常情况下  @ 开头的
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
                SkinPairBean skinPair = new SkinPairBean(attributeName, resId);
                skinPairBeanList.add(skinPair);
            }
        }
        if (!skinPairBeanList.isEmpty() || view instanceof SkinViewSupport) {
            SkinViewBean skinViewBean = new SkinViewBean(view, skinPairBeanList);

            skinViewBean.changeSkin();
            mSkinViews.add(skinViewBean);
        }

    }

    /**
     * 更换皮肤
     */
    public void changeSkin() {
        for (SkinViewBean skinView : mSkinViews) {
            skinView.changeSkin();
        }
    }

    public void onDestroy() {
        mSkinViews.clear();
    }
}
