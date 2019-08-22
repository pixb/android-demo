package com.pix.testgreendao;

import android.app.Application;

/**
 * Created by Administrator on 2017/3/28.
 */

public class BaseApplication extends Application {
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApplication getInstance() {
        return  sInstance;
    }
}
