package com.pix.http;

public class NetManager {
    private IHttpConfig mIHttpConfig;

    private static NetManager instance = new NetManager();

    public static NetManager getInstance() {
        return instance;
    }

    private NetManager() {

    }

    public IHttpConfig getHttpConfig() {
        return mIHttpConfig;
    }

    public void setHttpConfig(IHttpConfig IHttpConfig) {
        this.mIHttpConfig = IHttpConfig;
    }
}
