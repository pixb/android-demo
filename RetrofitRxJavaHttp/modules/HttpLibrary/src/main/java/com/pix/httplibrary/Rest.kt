package com.pix.httplibrary

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


/**
 * Created by pixboly on  2018/10/18
 * Retrofit封装
 * @version 2.0.0
 * @description
 * @modify
 */
object Rest {
    /**
     * 请求集合
     */
    private val restMap = HashMap<String,Any>()
    private val client = OKHttpManager.okHttpClient;

    fun <T> getRestApi(service: Class<T>, baseUri: String): T {
        if (null == restMap[baseUri]) {
            val restAPI = Retrofit.Builder()
                    .baseUrl(baseUri)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
                    .create(service)
            restMap.put(baseUri, restAPI!!)
        }
        return restMap[baseUri] as T
    }
}
