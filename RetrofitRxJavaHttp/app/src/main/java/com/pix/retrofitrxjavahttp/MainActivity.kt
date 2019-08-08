package com.pix.retrofitrxjavahttp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pix.httplibrary.OKHttpManager
import com.pix.httplibrary.RxSubscribe
import com.pix.retrofitrxjavahttp.bean.User
import com.pix.retrofitrxjavahttp.http.GFRequest

class MainActivity : AppCompatActivity() {
    val TAG:String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        var map = HashMap<String,String>()
        OKHttpManager.setHeaders(map)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GFRequest.getUserInfo("500000385", object : RxSubscribe<Map<String, User>>() {
            override fun onSuccess(t: Map<String, User>?) {
                Log.d(TAG,"onSuccess()")
                for((k,v) in t!!) {
                    Log.d(TAG,"onCreate(),uid:${v.toString()}")
                }
            }
            override fun onError(t: Throwable) {
                super.onError(t)
            }

        })
    }
}
