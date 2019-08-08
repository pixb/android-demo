package com.pix.httplibrary

import java.net.ConnectException

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import retrofit2.HttpException


/**
 * Created by pixboly on  2018/10/18
 * RxJava形式的返回
 * @version 2.0.0
 * @description
 * @modify
 */
abstract class RxSubscribe<T> : Observer<BaseModel<T>> {

    protected abstract fun onSuccess(t: T?)

    protected fun onFailed(code: Int, msg: String?) {}

    override fun onSubscribe(@NonNull d: Disposable) {
        // 比如显示加载中对话框
    }

    override fun onComplete() {
        // 比如隐藏加载中对话框
    }

    override fun onNext(baseModel: BaseModel<T>) {
        if (baseModel.state == 0) {
            onSuccess(baseModel.content)
        } else {
            onFailed(baseModel.state, baseModel.message)
        }
    }

    override fun onError(t: Throwable) {
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