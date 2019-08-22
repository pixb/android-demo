package com.pix.http;



import com.pix.eventbus.HttpSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @param <T>
 */
public class ResultAnalysis<T> implements INetwork.OnResponseListener {

    private IDelivery mDelivery;
    private ResultParser<T> resultParser;

    public ResultAnalysis(IDelivery delivery, ResultParser<T> iResultParser) {
        this.mDelivery = delivery;
        this.resultParser = iResultParser;
    }

    @Override
    public void onResponseSuccess(HttpRequester httpRequester, HttpResponse httpResponse, Object object) {
        HttpSubscriber.log(httpRequester.toString()+"=="+httpResponse.toString());
        httpResponse.setResult(object);
        if (resultParser == null) {
            return;
        }
        String result= (String) object;//默认做string处理
        Object ret=null;
        try {
            if (httpRequester.resultType == HttpRequester.RESULT_JSON_TYPE) {
                object = new JSONObject(result);
            } else if (httpRequester.resultType == HttpRequester.RESULT_STRING_TYPE) {
                //ingore
            }
            resultParser.resolve((T) object);
        } catch(JSONException je){
                HttpSubscriber.log("数据解析错误",je);
            resultParser.setErrorObject(ErrorObject.build(ErrorObject.ERROR_DATA_PARSE));
        } catch (Exception e) {
            HttpSubscriber.log("数据解析错误",e);
            resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_DATA_RESOLVE));
        }finally{
            mDelivery.delivery(httpRequester,httpResponse,resultParser);
        }

    }

    @Override
    public void onResponseError(HttpRequester httpRequester, HttpResponse httpResponse) {
        HttpSubscriber.log(httpRequester.toString()+"=="+httpResponse.toString());
        if (resultParser == null) {
            return;
        }
        switch (httpResponse.status) {
            case HttpResponse.STATUS_ERROR_NET:
                resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_NET_FAIL));
                break;
            case HttpResponse.STATUS_ERROR_SERVRE:
                resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_SERVER_ERROR));
                break;
        }
        mDelivery.delivery(httpRequester,httpResponse,resultParser);
    }
}
