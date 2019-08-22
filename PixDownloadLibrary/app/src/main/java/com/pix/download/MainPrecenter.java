package com.pix.download;

import android.text.TextUtils;
import android.util.Log;

import com.pix.download.bean.DownloadBean;
import com.pix.downloadlib.PixDownload;
import com.pix.downloadlib.bean.LoadInfo;
import com.pix.downloadlib.utils.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pix on 2017/2/21.
 */
public class MainPrecenter {
    private static final String TAG = "MainPrecenter";
    private IMainView mView;
    // 固定下载的资源路径，这里可以设置网络上的地址
    private static final String URL = "http://download.haozip.com/";
    // 固定存放下载的音乐的路径：SD卡目录下
    private static final String SD_PATH = "/mnt/sdcard/";
    private static final String FILE_NAME = "haozip_v3.1.exe";
    private List<DownloadBean> downloadList = new ArrayList<>();


    public MainPrecenter(IMainView view) {
        mView = view;
    }


    public void init() {
        EventBusManager.getInstance().register(this);
        downloadList.add(new DownloadBean("haozip_v3.1.exe"));
        downloadList.add(new DownloadBean("haozip_v3.1_hj.exe"));
        downloadList.add(new DownloadBean("haozip_v2.8_x64_tiny.exe"));
        downloadList.add(new DownloadBean("haozip_v2.8_tiny.exe"));
        mView.updateList(downloadList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLoadInfo(LoadInfo info) {
        Log.d(TAG,"onEventLoadInfo()," + info.toString());
        DownloadBean bean = null;
        // 数据存在更新数据
        for (DownloadBean o : downloadList) {
            if(o == null || TextUtils.isEmpty(o.fileName)) {
                continue;
            }
            if(o.fileName.equals(info.fileName)) {
                o.totalSize = info.getFileSize();
                o.completedSize = info.getComplete();
                bean = o;
            }
        }
        if(bean == null) {
            bean = new DownloadBean(info.fileName,info.getFileSize(),info.getComplete());
            downloadList.add(bean);
        }
        if(info.percent % 5 == 0) {
            mView.updateList(downloadList);
        }
    }

    public void downLoadFile(String fileName) {
        Log.d(TAG,"downLoadFile(),fileName:" + fileName);
        PixDownload.download(URL + fileName,SD_PATH,fileName,1);
    }

    public void pauseDownload(String fileName) {
        Log.d(TAG,"pauseDownload(),fileName:" + fileName);
        PixDownload.pause(URL + fileName);
    }
}
