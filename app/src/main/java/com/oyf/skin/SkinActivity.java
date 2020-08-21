package com.oyf.skin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import com.oyf.skin_lib.SkinManager;

import java.io.File;

/**
 * @创建者 oyf
 * @创建时间 2020/8/21 14:29
 * @描述
 **/
public class SkinActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_skin);
    }

    // 用户触发换肤动作
    public void change(View view) {
        File externalFilesDir = new File(Environment.getExternalStorageDirectory(), "skin_package-debug.apk");

        SkinManager.getInstance().loadSkinPackage(externalFilesDir.getAbsolutePath());
    }

    // 用户触发恢复换肤动作
    public void restore(View view) {
        SkinManager.getInstance().loadSkinPackage(null);
    }
}
