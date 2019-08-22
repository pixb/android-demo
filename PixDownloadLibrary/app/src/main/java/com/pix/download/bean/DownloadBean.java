package com.pix.download.bean;

/**
 * Created by pix on 2017/2/21.
 */

public class DownloadBean {
    public String fileName;
    public int totalSize = 100;
    public int completedSize = 0;

    public DownloadBean(String fileName) {
        this.fileName = fileName;
    }
    public DownloadBean(String fileName,int totalSize,int completedSize) {
        this.fileName = fileName;
        this.totalSize = totalSize;
        this.completedSize = completedSize;
    }
}
