package com.pix.httplibrary


import android.util.Log
import okhttp3.*
import java.util.concurrent.TimeUnit

import java.io.IOException


object OKHttpManager {
    private val TAG = "OKHttpManager"

    var okHttpClient: OkHttpClient? = null
    var clientBuild = OkHttpClient.Builder()

    /**
     * 设置请求公共头
     */
    fun setHeaders(headers:Map<String,String>) {
        clientBuild.addInterceptor(AddHeaderAndParamsInterceptor(headers))
        okHttpClient = clientBuild.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
        .readTimeout(60 * 1000,TimeUnit.MILLISECONDS)
        .build()
    }

    class AddHeaderAndParamsInterceptor(var headers:Map<String,String>):Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var oldRequest = chain?.request()

            // 签名
            var params = HashMap<String,String>()
            var paramsNames = oldRequest?.url()?.queryParameterNames()
            for (pName in paramsNames!!) {
                params[pName] = oldRequest?.url()?.queryParameter(pName)!!
                Log.d(TAG,"requestParams:key=$pName,value:${params[pName]}")
            }
            // 添加公共参数
            var signUrlBuilder
                    = oldRequest?.url()?.newBuilder()?.scheme(oldRequest?.url()?.scheme())
                    ?.host(oldRequest?.url().host())
                    ?.addQueryParameter("pk1","pv1")
                    ?.addQueryParameter("pk2","pv2")

            var newRequest = oldRequest?.newBuilder()?.method(oldRequest?.method(),oldRequest?.body())
                    ?.url(signUrlBuilder?.build())
            // 增加公共头
            for((k,v) in headers) {
                newRequest?.addHeader(k,v)
            }
            return chain?.proceed(newRequest?.build())!!
        }
    }

    fun cancleRequest() {
        okHttpClient?.dispatcher()?.cancelAll()
    }
}
