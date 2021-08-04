package com.moyoi.library_common.bean
import com.moyoi.library_common.http.HttpResponse

/**
 * @Description 搜索热词bean
 * @Author Lu
 * @Date 2021/6/28 10:35
 * @Version: 1.0
 */

data class HotkeyListData(
    val data: List<HotKeyBean>? = null
) : HttpResponse()

data class HotKeyBean @JvmOverloads constructor(
    val id: String = "",
    val link: String = "",
    val name: String = "",
    val order: String = "",
    val visible: String = ""
)