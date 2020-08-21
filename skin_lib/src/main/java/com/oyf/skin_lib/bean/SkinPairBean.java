package com.oyf.skin_lib.bean;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 11:47
 * @描述 需要换肤得view得属性得name跟value
 **/
public class SkinPairBean {

    // 属性名
    String attributeName;

    // 属性ID
    int resId;

    public SkinPairBean(String attributeName, int resId) {
        this.attributeName = attributeName;
        this.resId = resId;
    }
}
