package com.moyoi.module_home.bean

import com.moyoi.library_common.http.HttpResponse

/**
 * @Description banner bean
 * @Author Lu
 * @Date 2021/6/28 10:16
 * @Version: 1.0
 */

data class BannerListData(
    val data: List<BannerBean>? = null
) : HttpResponse()

data class BannerBean(
    val desc: String = "",
    val id: String = "",
    val imagePath: String = "",
    val isVisible: String = "",
    val order: String = "",
    val title: String = "",
    val type: String = "",
    val url: String = ""
)