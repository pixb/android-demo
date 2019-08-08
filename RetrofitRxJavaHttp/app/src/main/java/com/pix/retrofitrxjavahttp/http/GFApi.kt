package com.pix.retrofitrxjavahttp.http

import com.pix.httplibrary.BaseBack
import com.pix.httplibrary.BaseModel
import com.pix.retrofitrxjavahttp.bean.User

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by pixboly on  2018/10/18
 *
 * @version 2.0.0
 * @description
 * @modify
 */
interface GFApi {
    /**
     * 获取用户信息
     * @return
     */
    @GET("cgi/user/getUserInfo")
    fun getRxUser(@Query("targetUid")uid:String):Observable<BaseModel<Map<String, User>>>

    @GET("/userinfo")
    fun getUser():BaseBack<BaseModel<User>>

    companion object {
        const val DOMAN: String = "https://m.guagualive.com/"
    }

}
