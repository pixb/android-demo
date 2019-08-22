package com.pix.downloadlib.bean;
 /** 
  *自定义的一个记载下载器详细信息的类  
  */  
 public class LoadInfo {  
     public int fileSize;//文件大小  
     private int complete;//完成度  
     private String urlstring;//下载器标识
     public String fileName; // 文件名称
     public int percent;
     public LoadInfo(int fileSize, int complete, String urlstring,String fileName) {
         this.fileSize = fileSize;  
         this.complete = complete;  
         this.urlstring = urlstring;
         this.fileName = fileName;
         percent = complete * 100 / fileSize;
     }  
     public LoadInfo() {  
     }  
     public int getFileSize() {  
         return fileSize;  
     }  
     public void setFileSize(int fileSize) {  
         this.fileSize = fileSize;  
     }  
     public int getComplete() {  
         return complete;  
     }  
     public void setComplete(int complete) {  
         this.complete = complete;  
     }  
     public String getUrlstring() {  
         return urlstring;  
     }  
     public void setUrlstring(String urlstring) {  
         this.urlstring = urlstring;  
     }  
     @Override  
     public String toString() {  
         return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete  
                 + ", urlstring=" + urlstring + "]";  
     }  
 }  