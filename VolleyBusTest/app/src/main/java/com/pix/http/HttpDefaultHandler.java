package com.pix.http;


import com.pix.eventbus.EventBusDelivery;
import com.pix.eventbus.EventBusManager;

/**
 * Http响应处理Handler接口实现类
 */
public class HttpDefaultHandler implements IHttpHandler {

    //用于发送请求
    private INetwork mNetwork;

    //用于处理请求
    private IDelivery mDelivery;

    public HttpDefaultHandler(INetwork network, IDelivery delivery) {
        mDelivery = delivery;
        mNetwork = network;
    }

    @Override
    public void handle(final HttpRequestMode httpRequestMode) {
        final ResultAnalysis resultAnalysis = new ResultAnalysis(mDelivery, httpRequestMode.mParser);
        mNetwork.performRequest(httpRequestMode.mHttpRequester, resultAnalysis);
    }

    public static IHttpHandler newDefaultHandler() {
        return new HttpDefaultHandler(new VolleyNetwork(), new EventBusDelivery(EventBusManager.getInstance()));
    }

}
