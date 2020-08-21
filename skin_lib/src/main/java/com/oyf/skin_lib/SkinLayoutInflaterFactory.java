package com.oyf.skin_lib;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oyf.skin_lib.utils.SkinUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 11:11
 * @描述
 **/
public class SkinLayoutInflaterFactory implements LayoutInflater.Factory2 {
    private Activity mActivity;

    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };

    //记录对应VIEW的构造函数
    private static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    //记录view对应得构造方法，相同的可以直接使用
    private static final HashMap<String, Constructor<? extends View>> mConstructorMap =
            new HashMap<String, Constructor<? extends View>>();
    // 页面属性管理器
    private SkinAttribute skinAttribute;

    public SkinLayoutInflaterFactory(Activity activity) {
        mActivity = activity;
        skinAttribute = new SkinAttribute();
    }

    /**
     * 可以忽略
     *
     * @param s
     * @param context
     * @param attributeSet
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull String s, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        //换肤就是在需要时候替换 View的属性(src、background等)
        //所以这里创建 View,从而修改View属性
        View view = createSDKView(name, context, attributeSet);
        if (null == view) {//代表是自定义view
            view = createView(name, context, attributeSet);
        }
        if (null != view) {
            //存储到我们需要换肤得view中
            skinAttribute.collectViewInfo(view, attributeSet);
        }
        return view;
    }

    /**
     * 创建系统view
     * 系统的view一般是mClassPrefixList集合里面的开头
     *
     * @param name
     * @param context
     * @param attributeSet
     * @return
     */
    private View createSDKView(String name, Context context, AttributeSet attributeSet) {
        if (name.contains(".")) {//带有点的表示是自定义view
            return null;
        }
        for (String classPrefix : mClassPrefixList) {
            View view = createView(classPrefix + name, context, attributeSet);
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    /**
     * 根据类名去反射创建view
     *
     * @param name
     * @param context
     * @param attributeSet
     * @return
     */
    private View createView(String name, Context context, AttributeSet attributeSet) {
        try {
            Constructor<? extends View> constructor = findConstructor(context, name);
            View view = constructor.newInstance(context, attributeSet);
            return view;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 使用一个map存储构造方法，每次先从map里面查找，没有在重新创建，并存入map中
     *
     * @param context
     * @param name
     * @return
     */
    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = mConstructorMap.get(name);
        if (null == constructor) {
            try {
                Class<? extends View> aClass = context.getClass().getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = aClass.getConstructor(mConstructorSignature);
                mConstructorMap.put(name, constructor);
            } catch (Exception e) {
            }
        }
        return constructor;
    }

    /**
     * 更换皮肤，修改控件里面的view得属性得值
     */
    public void changeSkin() {
        SkinUtil.updateStatusBarColor(mActivity);
        skinAttribute.changeSkin();
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        if (null != skinAttribute) {
            skinAttribute.onDestroy();
        }
        mConstructorMap.clear();
    }

}
