package com.moyoi.library_common.bean

import android.os.Parcelable
import com.moyoi.library_common.http.HttpResponse
import kotlinx.android.parcel.Parcelize

/**
 * @Description 问答评论bean
 * @Author Lu
 * @Date 2021/7/8 10:44
 * @Version: 1.0
 */

data class WendaCommentsListData(
    val data: WendaCommentsData
) : HttpResponse()

data class WendaCommentsData(
    val curPage: String = "",
    val datas: List<WendaCommentsBean>? = null,
    val offset: String = "",
    val over: Boolean = false,
    val pageCount: String = "",
    val size: String = "",
    val total: String = ""
)

@Parcelize
data class WendaCommentsBean(
    val anonymous: String = "",
    val appendForContent: String = "",
    val articleId: String = "",
    val canEdit: Boolean = false,
    val content: String = "",
    val contentMd: String = "",
    val id: String = "",
    val niceDate: String = "",
    val publishDate: String = "",
    val replyCommentId: String = "",
    val replyComments: List<WendaCommentsBean>? = null,
    val rootCommentId: String = "",
    val status: String = "",
    val toUserId: String = "",
    val toUserName: String = "",
    val userId: String = "",
    val userName: String = "",
    val zan: String = ""
) : Parcelable