package com.pix.eventbus;


import android.util.Log;

import com.pix.http.HttpDefaultHandler;
import com.pix.http.HttpRequestMode;
import com.pix.http.IHttpHandler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Http订阅者类
 * 这个类用于处理Http Response的信息
 * 包含IHttpHandler处理者接口的对象
 */
public class HttpSubscriber {

    public static final String TAG=HttpSubscriber.class.getSimpleName();

    private IHttpHandler mHttpHandler;


    public HttpSubscriber(){
        mHttpHandler= HttpDefaultHandler.newDefaultHandler();
    }

    /**
     * 接受http数据网络请求event
     * @param httpRequestMode
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventHttpRequest(HttpRequestMode httpRequestMode){
        mHttpHandler.handle(httpRequestMode);
    }

    public static void log(String message){
        Log.d(TAG,message);
    }
    public static void log(String message,Object... args){
        Log.d(TAG, String.format(message, args));
    }
    public static void log(String message,Throwable e){
        Log.d(TAG,message,e);
    }

}
