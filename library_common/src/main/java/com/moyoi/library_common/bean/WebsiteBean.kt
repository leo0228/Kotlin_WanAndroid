package com.moyoi.library_common.bean

import com.moyoi.library_common.http.HttpResponse

/**
 * @Description 网址bean
 * @Author Lu
 * @Date 2021/6/28 10:44
 * @Version: 1.0
 */
data class WebsiteListData(
    val data: List<WebsiteBean>? = null
) : HttpResponse()

data class WebsiteBean @JvmOverloads constructor(
    val category: String = "",
    val icon: String = "",
    val id: String = "",
    val link: String = "",
    val name: String = "",
    val order: String = "",
    val visible: String = "",
    val desc: String = "",
    val userId: String = "",
    var collect: Boolean = false,
)
