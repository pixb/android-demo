package com.pix.http;

/**
 * 定义网络请求
 * 定义内部接口，请求回调接口，响应成功方法，响应失败的方法
 */

public interface INetwork {
    /**
     * 请求方法
     * @param httpRequester
     * @param onResponseListener
     */
    public void performRequest(HttpRequester httpRequester, OnResponseListener onResponseListener);

    /**
     * 响应监听器
     */
    public static interface OnResponseListener{
        /**
         * 响应成功
         * @param httpRequester
         * @param httpResponse
         * @param object
         */
        public void onResponseSuccess(HttpRequester httpRequester, HttpResponse httpResponse, Object object);

        /**
         * 响应失败
         * @param httpRequester
         * @param httpResponse
         */
        public void onResponseError(HttpRequester httpRequester, HttpResponse httpResponse);
    }
}
