package com.pix.retrofitrxjavahttp.bean

/**
 * Created by pixboly on  2018/10/18
 * 用户信息类
 * @version 2.0.0
 * @description
 * @modify
 */
class User {
    var id: String? = null
    var nickname:String?=null
    var description:String?=null
    var gender:String?=null
    override fun toString(): String {
        return "User(id=$id, nickname=$nickname, description=$description, gender=$gender)"
    }
}
