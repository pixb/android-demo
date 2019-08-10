package com.pix.testandroid;

import android.app.Application;

/**
 * Created by Administrator on 2016/11/1.
 */

public class BaseApplication extends Application{
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Application  getInstance() {
        return sInstance;
    }
}
