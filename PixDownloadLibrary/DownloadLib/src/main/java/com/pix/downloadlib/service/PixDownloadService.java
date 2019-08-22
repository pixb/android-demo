package com.pix.downloadlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pix.downloadlib.bean.DownloadState;
import com.pix.downloadlib.bean.LoadInfo;
import com.pix.downloadlib.download.Downloader;
import com.pix.downloadlib.utils.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pix on 2017/2/20.
 */

public class PixDownloadService extends Service {
    private static final String TAG = "PixDownloadService";
    /** 下载的网络地址 */
    public static final String TAG_DOWNLOAD_URL = "download_url";
    /** 下载保存的文件路径 */
    public static final String TAG_FILE_PATH = "file_path";
    /** 下载的文件名 */
    public static final String TAG_FILE_NAME = "file_name";
    /** 下载时的线程个数 */
    public static final String TAG_THREAD_COUNT = "thread_count";
    /** 命令key */
    public static final String TAG_CMD = "download_cmd";
    /** 下载命令 */
    public static final int CMD_DOWNLOAD = 1;
    /** 暂停命令 */
    public static final int CMD_PAUSE = 2;
    /** 默认的线程个数 */
    public static final int THREAD_COUNT_DEFAULT = 1;
    /** rxjava订阅管理 */
    private CompositeSubscription mRxManager = new CompositeSubscription();

    /** 下载器字典 */
    private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();


    @Override
    public void onCreate() {
        super.onCreate();
        EventBusManager.getInstance().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int cmd = intent.getIntExtra(TAG_CMD,CMD_DOWNLOAD);
        final String url = intent.getStringExtra(TAG_DOWNLOAD_URL);
        switch (cmd) {
            case CMD_DOWNLOAD:
                final String filePath = intent.getStringExtra(TAG_FILE_PATH);
                final int threadCount = intent.getIntExtra(TAG_THREAD_COUNT,THREAD_COUNT_DEFAULT);
                final String fileName = intent.getStringExtra(TAG_FILE_NAME);
                Log.d(TAG,"onStartCommand(),url:" + url );
                Log.d(TAG,"onStartCommand(),filePath:" + filePath);
                Log.d(TAG,"onStartCommand(),fileName:" + fileName);
                Log.d(TAG,"onStartCommand(),threadCount:" + threadCount);
                mRxManager.add( Observable
                        .just(url)
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                //创建下载器
                                // 初始化一个downloader下载器
                                Downloader downloader = downloaders.get(url);
                                if (downloader == null) {
                                    downloader = new Downloader(url,filePath,fileName,threadCount,PixDownloadService.this);
                                    downloaders.put(url, downloader);
                                }
                                if (downloader.isdownloading())
                                    return ;
                                // 得到下载信息类的个数组成集合
                                LoadInfo loadInfo = downloader.getDownloaderInfors();
                                EventBusManager.getInstance().post(loadInfo);
                                // 调用方法开始下载
                                downloader.download();
                            }
                        }));
                break;
            case CMD_PAUSE:
                Downloader downloader = downloaders.get(url);
                if(downloader != null) {
                    downloader.pause();
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDownloadState(DownloadState state) {
        Downloader downloader = downloaders.get(state.url);
        if(downloader == null) {
            return ;
        }
        LoadInfo info = downloader.getDownloaderInfors();
        // 避免事件太多，堵塞
        if((info.getComplete() * 100 / info.fileSize) % 5 == 0) {
            EventBusManager.getInstance().post(info);
        }
        if(info.fileSize == info.getComplete()) {   // 下载完成
            downloaders.get(info.getUrlstring()).delete(info.getUrlstring());
            downloaders.get(info.getUrlstring()).reset();
            downloaders.remove(info.getUrlstring());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxManager.unsubscribe();
        EventBusManager.getInstance().unregister(this);
    }
}
