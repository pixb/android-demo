package com.pix.volleybustest;

import android.app.Application;

import com.pix.eventbus.EventBusManager;
import com.pix.http.NetManager;
import com.pix.http.VolleyManager;
import com.pix.request.TestHttpConfig;

public class BaseApplication extends Application {
    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        VolleyManager.initialize(this);
        NetManager.getInstance().setHttpConfig(new TestHttpConfig());
        EventBusManager.getInstance().registerDefault();
    }

    public static Application getInstance() {
        return mInstance;
    }
}
