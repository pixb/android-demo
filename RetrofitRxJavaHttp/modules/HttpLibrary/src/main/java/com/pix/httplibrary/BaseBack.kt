package com.pix.httplibrary

import java.net.ConnectException

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


/**
 * Created by pixboly on  2018/10/18
 * 基础返回retrofit形式
 * @version 2.0.0
 * @description
 * @modify
 */
abstract class BaseBack<T> : Callback<BaseModel<T>> {
    protected abstract fun onSuccess(t: T?)

    protected fun onFailed(code: Int, msg: String?) {}
    override fun onResponse(call: Call<BaseModel<T>>, response: Response<BaseModel<T>>) {
        val baseModel = response.body()
        if (response.isSuccessful && baseModel != null) {
            if (baseModel.state == 0) {
                onSuccess(baseModel.content)
            } else {
                onFailed(baseModel.state, baseModel.message)
            }
        } else {
            onFailed(response.code(), response.message())
        }
    }

    override fun onFailure(call: Call<BaseModel<T>>, t: Throwable) {
        if (t is ConnectException) {
            //网络连接失败
            onFailed(403, t.message)
        } else if (t is HttpException) {
            onFailed(t.code(), t.message())
        } else {
            onFailed(405, t.message)
        }
    }
}
