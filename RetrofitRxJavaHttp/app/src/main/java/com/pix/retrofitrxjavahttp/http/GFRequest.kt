package com.pix.retrofitrxjavahttp.http

import com.pix.httplibrary.BaseBack
import com.pix.httplibrary.BaseModel
import com.pix.httplibrary.Rest
import com.pix.httplibrary.RxSubscribe
import com.pix.retrofitrxjavahttp.bean.User

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * Created by pixboly on  2018/10/18
 *
 * @version 2.0.0
 * @description
 * @modify
 */
object GFRequest {

    fun getUser(): BaseBack<BaseModel<User>> {
        return Rest.getRestApi<GFApi>(GFApi::class.java, GFApi.DOMAN).getUser()
    }

    /**
     * 取得用户信息
     * @param subscribe
     */
    fun getUserInfo(uid: String, subscribe: RxSubscribe<Map<String,User>>) {
        Rest.getRestApi<GFApi>(GFApi::class.java, GFApi.DOMAN).getRxUser(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe)
    }

}
