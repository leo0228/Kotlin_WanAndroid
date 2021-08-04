package com.moyoi.library_common.http

/**
 * @Description 返回数据基类
 * @Author Lu
 * @Date 2021/6/28 9:35
 * @Version: 1.0
 */
open class HttpResponse @JvmOverloads constructor(
    var errorCode: String = "",
    var errorMsg: String = ""
)
