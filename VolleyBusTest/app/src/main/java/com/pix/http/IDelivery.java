package com.pix.http;

/**
 * 交付的接口，用于将请求的数据交付给处理层
 */
public interface IDelivery {
    /**
     * 交付的方法，将网络请求结果交付处理
     * @param httpRequester
     * @param httpResponse
     * @param deliveryObject
     */
    public  void delivery(HttpRequester httpRequester, HttpResponse httpResponse, Object deliveryObject);
}
