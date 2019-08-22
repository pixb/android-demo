package com.pix.bean;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseResult extends BaseBean {

    @Override
    public void parse(JSONObject contentJson) throws Exception {
        super.parse(contentJson);
    }

    @Override
    public String toString() {
        return "message:" + getMessage();

    }
}
