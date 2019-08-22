package com.pix.eventbus;

import android.util.Log;


import org.greenrobot.eventbus.EventBus;

/**
 * EventBus管理类，这个类封装了EventBus的常用操作
 * 注册Http订阅者类HttpSubscriber
 */
public class EventBusManager {
    public static final String TAG = "EventBusManager";
    private EventBus mEventBus;

    private static EventBusManager Instance;

    public EventBusManager() {
        mEventBus = EventBus.getDefault();
    }

    public void post(Object object) {
        if (object == null) {
            Log.d(TAG, "post null object");
            return;
        }
        mEventBus.post(object);
    }


    public void register(Object object) {
        if(object==null)return;
        try {
            if (!mEventBus.isRegistered(object)) {
                mEventBus.register(object);
            }
        } catch (Exception e) {

        }
    }

    public void unregister(Object object) {
        if(object==null)return;
        try {
            if (mEventBus.isRegistered(object)) {
                mEventBus.unregister(object);
            }
        } catch (Exception e) {

        }
    }


    public static EventBusManager getInstance() {
        if (Instance == null) {
            synchronized (EventBusManager.class) {
                if (Instance == null) {
                    Instance = new EventBusManager();
                }
            }
        }
        return Instance;
    }

    public void registerDefault() {
        register(new HttpSubscriber());
    }


}
