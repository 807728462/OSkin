package com.oyf.skin;

import android.app.Application;

import com.oyf.skin_lib.SkinManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this);
    }
}
