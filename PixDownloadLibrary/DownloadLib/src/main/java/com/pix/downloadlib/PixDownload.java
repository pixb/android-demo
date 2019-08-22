package com.pix.downloadlib;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.pix.downloadlib.service.PixDownloadService;

/**
 * 下载库入口
 * Created by pix on 2017/2/20.
 */

public class PixDownload {
    private static final String TAG = "PixDownload";
    private static Context sContext;

    /**
     * 使用前初始化，由于Context是静态保存的，所以要传Application Context
     * @param ctx
     */
    public static void init(Context ctx) {
        sContext = ctx;
    }

    /**
     *  下载文件
     * @param url   下载的文件的网络地址
     * @param filePath 文件保存的地址
     * @return
     */
    public static void download(String url,String filePath,String fileName) {
        if(sContext == null) {
            throw new IllegalStateException(TAG + ",download(),sContext == null!");
        }
        if(TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException(TAG + ",download(),url or filePath or fileName is Empty!");
        }
        Intent downloadIntent = new Intent(sContext,PixDownloadService.class);
        downloadIntent.putExtra(PixDownloadService.TAG_DOWNLOAD_URL,url);
        downloadIntent.putExtra(PixDownloadService.TAG_FILE_PATH,filePath + fileName);
        sContext.startService(downloadIntent);
    }

    /**
     *  下载文件
     * @param url
     * @param filePath
     * @return
     */
    public static void download(String url,String filePath,String fileName,int threadCount) {
        if(sContext == null) {
            throw new IllegalStateException(TAG + ",download(),sContext == null!");
        }
        if(TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException(TAG + ",download(),url or filePath or fileName is Empty!");
        }
        if(threadCount <= 0) {
            throw new IllegalStateException(TAG + ",download(),thread count must greater than 0!");
        }
        Intent downloadIntent = new Intent(sContext,PixDownloadService.class);
        downloadIntent.putExtra(PixDownloadService.TAG_CMD,PixDownloadService.CMD_DOWNLOAD);
        downloadIntent.putExtra(PixDownloadService.TAG_DOWNLOAD_URL,url);
        downloadIntent.putExtra(PixDownloadService.TAG_FILE_PATH,filePath + fileName);
        downloadIntent.putExtra(PixDownloadService.TAG_THREAD_COUNT,threadCount);
        downloadIntent.putExtra(PixDownloadService.TAG_FILE_NAME,fileName);
        sContext.startService(downloadIntent);
    }

    public static void pause(String url) {
        if(TextUtils.isEmpty(url)) {
            throw new IllegalStateException(TAG + ",download(),url is Empty!");
        }
        Intent pauseIntent = new Intent(sContext,PixDownloadService.class);
        pauseIntent.putExtra(PixDownloadService.TAG_CMD,PixDownloadService.CMD_PAUSE);
        pauseIntent.putExtra(PixDownloadService.TAG_DOWNLOAD_URL,url);
        sContext.startService(pauseIntent);
    }

}
