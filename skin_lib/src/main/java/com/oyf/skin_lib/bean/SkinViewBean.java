package com.oyf.skin_lib.bean;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.oyf.skin_lib.SkinResources;
import com.oyf.skin_lib.Interface.SkinViewSupport;

import java.util.List;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 11:46
 * @描述 需要换肤得view，及属性
 **/
public class SkinViewBean {
    View view;//换肤得view

    List<SkinPairBean> skinPairs;//换肤view得属性集合，可能需要换多个属性

    public SkinViewBean(View view, List<SkinPairBean> skinPairs) {
        this.view = view;
        this.skinPairs = skinPairs;
    }

    public void changeSkin() {
        chagneSkinAction();
        changeAttribute();
    }

    /**
     * 自定义实现了SkinViewSupport得view更换属性
     */
    public void chagneSkinAction() {
        if (null == view) return;
        if (view instanceof SkinViewSupport) {
            SkinViewSupport skinViewSupport = (SkinViewSupport) view;
            skinViewSupport.changeSkin();
        }
    }

    private void changeAttribute() {
        if (null == skinPairs) return;
        for (SkinPairBean skinPair : skinPairs) {
            switch (skinPair.attributeName) {
                case "background":
                    Object background = SkinResources.getInstance().getBackground(skinPair.resId);
                    if (background instanceof Integer) {
                        view.setBackgroundColor((int) background);
                    } else {
                        ViewCompat.setBackground(view, (Drawable) background);
                    }
                    break;
                case "src":
                    background = SkinResources.getInstance().getBackground(skinPair
                            .resId);
                    if (background instanceof Integer) {
                        ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                background));
                    } else {
                        ((ImageView) view).setImageDrawable((Drawable) background);
                    }
                    break;
                case "textColor":
                    ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList
                            (skinPair.resId));
                    break;
                case "drawableLeft":
                    break;
                case "drawableTop":
                    break;
                case "drawableRight":
                    break;
                case "drawableBottom":
                    break;
                default:
                    break;
            }
        }
    }

}
