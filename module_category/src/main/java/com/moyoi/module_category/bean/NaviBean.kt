package com.moyoi.module_category.bean

import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.http.HttpResponse

/**
 * @Description 导航bean
 * @Author Lu
 * @Date 2021/6/28 10:42
 * @Version: 1.0
 */
data class NaviListData(
    val data: List<NaviBean>? = null
) : HttpResponse()

data class NaviBean(
    val articles: List<ArticleBean>? = null,
    val cid: String = "",
    val name: String = "",
    var isSelected: Boolean = false
)