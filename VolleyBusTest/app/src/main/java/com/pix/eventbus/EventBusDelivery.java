package com.pix.eventbus;


import com.pix.http.HttpRequester;
import com.pix.http.HttpResponse;
import com.pix.http.IDelivery;

/**
 * Http的响应后处理过的数据处理类
 * 将处理后的数据发送给用户
 */
public class EventBusDelivery implements IDelivery {

    private EventBusManager busManager;

    public EventBusDelivery(EventBusManager busManager) {
        this.busManager = busManager;
    }


    @Override
    public void delivery(HttpRequester httpRequester, HttpResponse httpResponse, Object deleveryObject) {
        if(deleveryObject!=null){
            busManager.post(deleveryObject);
        }
    }
}
