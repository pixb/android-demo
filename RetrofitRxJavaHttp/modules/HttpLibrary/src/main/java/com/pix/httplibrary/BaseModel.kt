package com.pix.httplibrary


/**
 * Created by pixboly on  2018/10/18
 * 聚范http请求返回模板类
 * @version 2.0.0
 * @description
 * @modify
 */
class BaseModel<T> {
    var state: Int = 0
    var message: String? = null
    var content: T? = null
}
