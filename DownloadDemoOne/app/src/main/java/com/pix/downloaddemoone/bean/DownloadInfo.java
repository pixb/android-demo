package com.pix.downloaddemoone.bean;

/**
 * Created by pix on 2017/2/16.
 */

public class DownloadInfo {
    public int downloadedSize;
    public int fileSize;

    public DownloadInfo(int fileSize,int dowloadSize) {
        this.fileSize = fileSize;
        downloadedSize = dowloadSize ;
    }
}
