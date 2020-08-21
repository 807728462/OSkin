package com.oyf.skin_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import com.oyf.skin_lib.utils.SkinUtil;

import java.lang.reflect.Field;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 10:38
 * @描述 用于监听activity的生命周期
 **/
public class ApplicationActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    //用于存储activity中的factroy，在每个activity的oncreate用自己的factroy去替换系统的
    ArrayMap<Activity, SkinLayoutInflaterFactory> mFactoryArrayMap = new ArrayMap<>();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        /**
         * 每次在activity的super.oncreate之后，
         * 在setContentView之前会触发此方法，
         * 所以我还还需要反射去修改的activity中的标志位mFactorySet，
         * 因为setFcatroy只允许设置一次
         * */
        // 更新状态栏
        SkinUtil.updateStatusBarColor(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        try {

            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 使用我们自己的 Factory2 设置 布局加载工厂了
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(activity);
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory);
        mFactoryArrayMap.put(activity, skinLayoutInflaterFactory);

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        // 释放操作...
        SkinLayoutInflaterFactory observe = mFactoryArrayMap.remove(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    /**
     * 更改皮肤
     */
    public void changeSkin() {
        for (SkinLayoutInflaterFactory value : mFactoryArrayMap.values()) {
            value.changeSkin();
        }
    }

}
