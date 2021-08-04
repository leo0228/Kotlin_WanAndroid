package com.moyoi.module_message.bean

import android.os.Parcelable
import com.moyoi.library_common.http.HttpResponse
import kotlinx.android.parcel.Parcelize

/**
 * @Description 站内消息bean
 * @Author Lu
 * @Date 2021/7/7 11:13
 * @Version: 1.0
 */

data class UnReadMessageNumData(
    val data: String = ""
) : HttpResponse()

data class UnOrReadMessageListData(
    val data: MessageData
) : HttpResponse()

data class MessageData(
    val curPage: String = "",
    val datas: List<MessageBean>? = null,
    val offset: String = "",
    val over: Boolean = false,
    val pageCount: String = "",
    val size: String = "",
    val total: String = ""
)

@Parcelize
data class MessageBean(
    val category: String = "",
    val date: String = "",
    val fromUser: String = "",
    val fromUserId: String = "",
    val fullLink: String = "",
    val id: String = "",
    val isRead: String = "",
    val link: String = "",
    val message: String = "",
    val niceDate: String = "",
    val tag: String = "",
    val title: String = "",
    val userId: String = ""
) : Parcelable