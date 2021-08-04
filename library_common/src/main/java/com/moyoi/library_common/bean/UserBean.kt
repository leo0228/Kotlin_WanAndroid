package com.moyoi.library_common.bean

import com.google.gson.Gson
import com.moyoi.library_common.http.HttpResponse

/**
 * @Description 用户登录注册相关bean
 * @Author Lu
 * @Date 2021/6/28 10:39
 * @Version: 1.0
 */

data class UserData(
    val data: UserBean
) : HttpResponse()

data class UserBean @JvmOverloads constructor(
    val admin: String = "",
    val chapterTops: List<Any>? = null,
    var coinCount: String = "",
    val collectIds: List<Int>? = null,
    val email: String = "",
    val icon: String = "",
    val id: String = "",
    val nickname: String = "",
    val password: String = "",
    val publicName: String = "",
    val token: String = "",
    val type: String = "",
    val username: String = ""
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

}