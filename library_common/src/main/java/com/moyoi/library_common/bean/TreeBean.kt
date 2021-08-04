package com.moyoi.library_common.bean

import android.os.Parcelable
import com.moyoi.library_common.http.HttpResponse
import kotlinx.android.parcel.Parcelize

/**
 * @Description 体系相关bean
 * @Author Lu
 * @Date 2021/6/28 10:37
 * @Version: 1.0
 */

@Parcelize
data class TreeListData(
    val data: List<TreeBean>? = null,
) : HttpResponse(), Parcelable

@Parcelize
data class TreeBean(
    val children: List<TreeBean>? = null,
    var childrenSelectPosition: Int = 0,
    val courseId: String = "",
    val id: String = "",
    val name: String = "",
    val order: String = "",
    val parentChapterId: String = "",
    val userControlSetTop: String = "",
    val visible: String = ""
) : Parcelable