package com.oyf.skin_lib;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 10:33
 * @描述
 **/
public class SkinManager {
    private static SkinManager instance;
    private Application mContext;
    private ApplicationActivityLifecycle activityLifecycle;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (null == instance) {
            instance = new SkinManager();
        }
        return instance;
    }

    public void init(Application context) {
        this.mContext = context;
        //初始化SP
        SkinPreference.init(mContext);

        // 资源管理类 用于从 app/皮肤 中加载资源
        SkinResources.init(mContext);
        activityLifecycle = new ApplicationActivityLifecycle();

        context.registerActivityLifecycleCallbacks(activityLifecycle);

        // 就是为了，加载上传使用的图片， 就是上次保存的图片
        loadSkinPackage(SkinPreference.getInstance().getSkin());
    }

    /**
     * 根据路径加载皮肤包
     *
     * @param skin
     */
    public void loadSkinPackage(String skin) {
        if (TextUtils.isEmpty(skin)) {
            SkinPreference.getInstance().reset();
            SkinResources.getInstance().resetSkinAction();
            activityLifecycle.changeSkin();
        } else {
            try {
                File file = new File(skin);
                if (!file.exists()) {
                    return;
                }
                Resources resources = mContext.getResources();
                //获取assetsManager的addAssetPathMethod方法
                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
                addAssetPathMethod.setAccessible(true);
                addAssetPathMethod.invoke(assetManager, file.getAbsolutePath());
                //创建新的皮肤包的resource
                Resources skinResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
                PackageManager packageManager = mContext.getPackageManager();
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
                String packName = packageArchiveInfo.packageName;

                SkinResources.getInstance().applySkinAction(skinResource, packName);
                //存储选中的皮肤包资源
                SkinPreference.getInstance().setSkin(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            activityLifecycle.changeSkin();
        }
    }
}
