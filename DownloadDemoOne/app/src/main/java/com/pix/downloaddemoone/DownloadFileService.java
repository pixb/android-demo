package com.pix.downloaddemoone;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.pix.downloaddemoone.bean.DownloadEvent;
import com.pix.downloaddemoone.bean.DownloadInfo;
import com.pix.downloaddemoone.utils.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;


/**
 * 下载文件的服务类
 */

public class DownloadFileService extends Service {
//    private CompositeSubscription mRxManager = new CompositeSubscription();
    private static final String TAG = "DownloadFileService";
    private DownloadService servcie;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        EventBusManager.getInstance().register(this);
        startDownload();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        Log.d(TAG,"onStartCommand()");
        String url = "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/2010082909434077.mp3";
//        mRxManager.add(Observable.just(url).observeOn(Schedulers.newThread()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String url) {
//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    File destination = Environment.getExternalStorageDirectory();
//                    try {
//                        servcie = new DownloadService(url, destination, 3, getApplicationContext());
//                        servcie.download(new DownloadListener() {
//                            @Override
//                            public void onDownload(int filesize,int downloaded_size) {
//                                Log.d(TAG,"onDownload(),downloaded_size:" + downloaded_size);
//                                EventBusManager.getInstance().post(new DownloadInfo(filesize,downloaded_size));
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "SD卡不存在或写保护!", Toast.LENGTH_LONG).show();
//                }
//            }
//        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDownloadEvent(DownloadEvent event) {
        Log.d(TAG,"onEventDownloadEvent(),event.cmd:" + event.cmd);
        switch (event.cmd) {
            case DownloadEvent.CMD_PAUSE:
                servcie.isPause = true;
                break;
            case DownloadEvent.CMD_RESUME:
                servcie.isPause = false;
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
        EventBusManager.getInstance().unregister(this);
    }
}
