package com.pix.http;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求类的定义
 * 相关参数
 	请求的方式GET或POST
 	请求的UR
 	请求的ID表示请求唯一识别
 	返回值类型：字符串or Json
 	超时时间：默认10s
 	重试次数
 	请求参数
 	请求头信息
 	TAG：请求时不用的信息，但回调要用的信息

 */
public class HttpRequester {
    private static volatile int syncID = 1000;
    public static final int RESULT_JSON_TYPE=1;
    public static final int RESULT_STRING_TYPE=2;
    public static final int DEFAULT_RETRY_NUMBER=0;
    public static final int DEFAULT_TIMEOUT=10000;
    int mMethod;
    int requestID;
    String mUrl;
    Map<String, String> params;
    Map<String, String> mHeaders;
    int resultType;
    int timeOut;
    int retryNumber;
    public Object tag;//请求时不需要的信息，但是回调会用到的信息。

    public HttpRequester() {
        mMethod = Request.Method.GET;
        init();
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(int retryNumber) {
        this.retryNumber = retryNumber;
    }



    private void init() {
        requestID = crateID();
        resultType=RESULT_JSON_TYPE;
        timeOut=DEFAULT_TIMEOUT;
        retryNumber=DEFAULT_RETRY_NUMBER;
    }

    private static synchronized int crateID() {
        if (syncID == Integer.MAX_VALUE) {
            syncID = 0;
        }
        return syncID++;
    }

    public int getRequestID() {
        return requestID;
    }

    public static HttpRequester newInstance() {
        return new HttpRequester();
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setMethod(int method) {
        mMethod = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public void addHeader(String key, String value) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.putAll(headers);
    }

    @Override
    public String toString() {
        return "HttpRequester{" +
                "params=" + params +
                ", mMethod=" + mMethod +
                ", requestID=" + requestID +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
