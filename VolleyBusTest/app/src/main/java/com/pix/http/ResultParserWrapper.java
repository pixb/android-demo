package com.pix.http;


import com.pix.utils.ProtocolParser;

import org.json.JSONObject;

public class ResultParserWrapper extends ResultParser<JSONObject> {
    private BaseServerParser mInnerParser;

    public ResultParserWrapper(BaseServerParser innerParser) {
        this.mInnerParser = innerParser;
    }

    @Override
    public Object resolve(JSONObject result) throws Exception {
        int state = ProtocolParser.getJsonInt(result, "state");
        if (state != 0 || !result.has("content")) {
            if (result.has("message")) {
                mInnerParser.setErrorObject(ErrorObject.build(state, ProtocolParser.getJsonStr(result, "message")));
            } else {
                mInnerParser.setErrorObject(ErrorObject.build(state));
            }
            return mInnerParser;
        }
        result = ProtocolParser.getJsonObj(result, "content");
        mInnerParser.parse(result);
        return mInnerParser;
    }

}
