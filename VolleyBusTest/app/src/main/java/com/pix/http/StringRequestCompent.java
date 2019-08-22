package com.pix.http;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringRequestCompent extends StringRequest{

    Map<String,String> mParams;

    Map<String,String> mHeaders;

    NetworkResponse mNetworkResponse;

    private StringRequestCompent(int method, String url, Map<String,String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.mParams=params;
        setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(mHeaders!=null){
            return mHeaders;
        }
        return super.getHeaders();
    }

    public void setHeaders(Map<String,String> headers){
        mHeaders=headers;
    }
    public void addHeader(String key,String value){
        if(mHeaders==null){
            mHeaders=new HashMap<>();
        }
        mHeaders.put(key,value);
    }
    public void addHeaders(Map<String,String> headers){
        if(mHeaders==null){
            mHeaders=new HashMap<>();
        }
        mHeaders.putAll(headers);
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        this.mNetworkResponse=response;
        return super.parseNetworkResponse(response);
    }
    public NetworkResponse getNetWorkResponse(){
        return this.mNetworkResponse;
    }


    /**
     *  Create a String's Request!
     * @param method  Request's type,usage Method.POST and Method.GET by volley
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @return
     */
    public static StringRequestCompent newStringRequest(int method, String url, Map<String,String> params, Response.Listener<String> listener, Response.ErrorListener errorListener){
        switch (method){
            case Method.POST:
                return new StringRequestCompent(method,url,params,listener,errorListener);
            case Method.GET:
            default:
                //connection url and parameters
                if(!url.endsWith("?")){
                    url=url.concat("?");
                }
                if (params != null) {
                    StringBuilder stringBuilder=new StringBuilder(100);
                    stringBuilder.append(url);
                    Set<Map.Entry<String,String>> set= params.entrySet();
                    for(Map.Entry<String,String> entry:set){
                        String key=entry.getKey();
                        String value=entry.getValue();
                        if(TextUtils.isEmpty(key)||TextUtils.isEmpty(value))continue;
                        stringBuilder.append(key).append("=").append(value).append("&");
                    }
                    url=stringBuilder.substring(0,stringBuilder.length()-1);
                }
                return new StringRequestCompent(method,url,null,listener,errorListener);


        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(getMethod()==Method.POST){
            return this.mParams;
        }
        return super.getParams();
    }

}
