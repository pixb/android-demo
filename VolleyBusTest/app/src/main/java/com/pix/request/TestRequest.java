package com.pix.request;

import com.pix.bean.ResponseResult;
import com.pix.http.BaseRequest;

import java.util.HashMap;
import java.util.Map;

public class TestRequest extends BaseRequest {
    public static final String DOMAIN = "http://api.k780.com:88/";
    public String URL = DOMAIN + "";
    public void reqData() {
        Map<String,String> params = new HashMap<>();
        params.put("app","weather.today");
        params.put("weaid","1");
        params.put("appkey","10003");
        params.put("sign","b59bc3ef6191eb9f747dd4e83c99f2a4");
        params.put("format","json");
        get(URL,params,null,new ResponseResult());
    }
}
