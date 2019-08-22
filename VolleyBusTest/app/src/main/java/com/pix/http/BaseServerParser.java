package com.pix.http;

import org.json.JSONObject;

public abstract class BaseServerParser extends ResultParser<JSONObject> {


    public abstract void parse(JSONObject jsonObject) throws Exception;

    @Override
    public final Object resolve(JSONObject result) throws Exception {
        if (isParseCommon()) {
            ResultParserWrapper resultParserWrapper = new ResultParserWrapper(this);
            resultParserWrapper.resolve(result);
        } else {
            parse(result);
        }
        return this;
    }

    protected boolean isParseCommon() {
        return true;
    }
}
