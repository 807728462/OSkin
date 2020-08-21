package com.oyf.skin_lib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 10:37
 * @描述
 **/
public class SkinResources {

    private String mSkinPackageName; // 皮肤包中的 包名，  null==不合格的皮肤包，否则就是合格的皮肤包，就可以换肤了
    private boolean isDefaultSkin = true; // 默认情况下，可以恢复默认的皮肤

    // app壳本身原生的 Resources
    private Resources mAppResources;

    // 皮肤包外界加载专用的 Resources
    private Resources mSkinResources;
    // 单例模式
    private volatile static SkinResources instance;

    public SkinResources(Context context) {
        this.mAppResources = context.getResources();
    }


    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    /**
     * 重置皮肤，使用默认皮肤
     */
    public void resetSkinAction() {
        this.mSkinResources = null;
        this.mSkinPackageName = "";
        isDefaultSkin = true;
    }

    /**
     * 更改皮肤包资源
     *
     * @param skinResource
     * @param packName
     */
    public void applySkinAction(Resources skinResource, String packName) {
        this.mSkinResources = skinResource;
        this.mSkinPackageName = packName;

        isDefaultSkin = TextUtils.isEmpty(packName) || mSkinResources == null;
    }

    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPackageName);
        return skinId;
    }

    /**
     * 输入主APP的ID，到皮肤APK文件中去找到对应ID的颜色值
     *
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int color = getIdentifier(resId);
        if (color == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(color);
    }


    /**
     * 获取color
     *
     * @param resId
     * @return
     */
    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int color = getIdentifier(resId);
        if (color == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(color);
    }

    /**
     * 获取drawable
     *
     * @param resId
     * @return
     */
    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int color = getIdentifier(resId);
        if (color == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(color);
    }


    /**
     * 可能是Color 也可能是drawable
     * 通过
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceEntryName = mAppResources.getResourceTypeName(resId);
        switch (resourceEntryName) {
            case "color":
                return getColor(resId);
            case "mipmap":
            case "drawable":
                return getDrawable(resId);
        }
        return null;
    }
}
