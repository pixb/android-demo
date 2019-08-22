package com.pix.http;

import com.android.volley.Request;
import com.pix.eventbus.EventBusManager;

import java.util.HashMap;
import java.util.Map;

public class BaseRequest {

    private Object object;

    public BaseRequest(Object object){
        this.object=object;
    }
    public BaseRequest(){
    }


    protected int request(String url,int method,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser,int retryNumber){
        HttpRequester httpRequester=HttpRequester.newInstance();
        httpRequester.setmUrl(url);
        httpRequester.setMethod(method);
        httpRequester.setHeaders(headers);
        httpRequester.addHeaders(NetManager.getInstance().getHttpConfig().getHeader());
        if (params == null) {
            params = new HashMap<>();
        }
        httpRequester.setParams(params);
        postEvent(HttpRequestMode.build(httpRequester,iResultParser,retryNumber));
        return httpRequester.getRequestID();
    }
    protected int request(String url,int method,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser){
       return request(url,method,params,headers,iResultParser,HttpRequestMode.DEFAULT_RETRY_NUMBER);
    }

    protected  int get(String url,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser,int retryNumber){
        return request(url, Request.Method.GET, params, headers,iResultParser,retryNumber);
    }
    protected int post(String url,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser,int retryNumber){
        return request(url, Request.Method.POST,params,headers,iResultParser,retryNumber);
    }

    protected  int get(String url,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser){
        return request(url, Request.Method.GET, params, headers,iResultParser);
    }
    protected int post(String url,Map<String,String> params,Map<String,String> headers,ResultParser iResultParser){
        return request(url, Request.Method.POST,params,headers,iResultParser);
    }

    private void postEvent(HttpRequestMode httpRequestMode){
        EventBusManager.getInstance().post(httpRequestMode);
    }
}
