package com.pix.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

/**
 * Velley Manager
 * 管理类完成如下功能
 * 初始化请求队列
 * 设定缓存目录
 * 封装请求的方法
 */

public class VolleyManager {
    private static final String TAG = "VolleyManager" ;
    private static VolleyManager sInstance;
    private final RequestQueue mRequestQueue;
    private boolean hasRelease;
    public VolleyManager(Context ctx) {
        mRequestQueue = initRequestQueue(ctx);
        hasRelease = false;
        mRequestQueue.start();
    }

    /**
     * 初始化请求队列
     * @param context
     * @return
     */
    private RequestQueue initRequestQueue(Context context) {
        Log.d(TAG,"initRequestQueue(),context.getCacheDir():" + context.getCacheDir());
        //创建缓存目录/data/data/com.pix.volleybustest/cache/cache
        File cacheDir = new File(context.getCacheDir(), "cache");
        String userAgent = "volley/1";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        HttpStack httpStack = new HurlStack();
        Network network = new BasicNetwork(httpStack);
        final HandlerThread liveHandlerThread = new HandlerThread("VolleyManager");
        liveHandlerThread.start();
        final Handler handler = new Handler(liveHandlerThread.getLooper());
        //创建了请求队列
        //线程池大小3
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, 3, new ExecutorDelivery(handler)) {
            @Override
            public void stop() {
                super.stop();
                if(hasRelease) {
                    liveHandlerThread.quit();
                }
            }
        };
        return queue;
    }

    private void release() {
        hasRelease=true;
        if (mRequestQueue != null) {
            mRequestQueue.stop();
        }
    }

    /**
     * 销毁的方法，用户停止网络请求的发送
     */
    public static void destory() {
        if (sInstance != null) {
            synchronized (VolleyManager.class) {
                if (sInstance != null) {
                    sInstance.release();
                    sInstance = null;
                }
            }
        }
    }

    /**
     * 发送网络请求
     * @param request
     */
    public void request(Request request) {
        mRequestQueue.add(request);
    }

    /**
     * 使用之前必须初始化，不出是化将抛出异常
     * @return
     */
    public static VolleyManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("volleyManager must be init");
        }
        return sInstance;
    }

    /**
     * 初始化这个Volley
     * @param context
     * @return
     */
    public static VolleyManager initialize(Context context) {
        if (sInstance == null) {
            synchronized (VolleyManager.class) {
                if (sInstance == null)
                    sInstance = new VolleyManager(context);
            }
        }
        return sInstance;
    }
}
