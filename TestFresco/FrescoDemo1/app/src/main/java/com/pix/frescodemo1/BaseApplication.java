package com.pix.frescodemo1;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 *
 * @author tpx   2017/4/3
 * @version 1.0.0
 * @description
 * @modify
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(this);
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }
}
