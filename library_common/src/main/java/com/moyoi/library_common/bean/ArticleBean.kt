package com.moyoi.library_common.bean

import android.os.Parcelable
import com.google.gson.Gson
import com.moyoi.library_common.http.HttpResponse
import kotlinx.android.parcel.Parcelize

/**
 * @Description 文章相关bean
 * @Author Lu
 * @Date 2021/6/28 9:37
 * @Version: 1.0
 */

@Parcelize
data class ArticleListData(
    val data: ArticleData
) : HttpResponse(), Parcelable

@Parcelize
data class TopArticleListData(
    val data: List<ArticleBean>? = null
) : HttpResponse(), Parcelable

@Parcelize
data class ShareArticleData(
    val data: UserShareData,
) : HttpResponse(), Parcelable

@Parcelize
data class UserShareData(
    val coinInfo: CoinBean,
    val shareArticles: ArticleData
) : Parcelable

@Parcelize
data class CoinBean @JvmOverloads constructor(
    val coinCount: String = "",
    val level: String = "",
    val nickname: String = "",
    val rank: String = "",
    val userId: String = "",
    val username: String = ""
) : Parcelable {

    fun toJson(): String {
        return Gson().toJson(this)
    }

}

@Parcelize
data class ArticleData(
    val curPage: String = "",
    val datas: List<ArticleBean>? = null,
    val offset: String = "",
    val over: Boolean = false,
    val pageCount: String = "",
    val size: String = "",
    val total: String = ""
) : Parcelable

@Parcelize
data class ArticleBean(
    val apkLink: String = "",
    val audit: String = "",
    val author: String = "",
    val canEdit: Boolean = false,
    val chapterId: String = "",
    val chapterName: String = "",
    var collect: Boolean? = null,
    val courseId: String = "",
    val desc: String = "",
    val descMd: String = "",
    val envelopePic: String = "",
    var top: Boolean = false,
    val fresh: Boolean = false,
    val host: String = "",
    val id: String = "",
    val link: String = "",
    val niceDate: String = "",
    val niceShareDate: String = "",
    val origin: String = "",
    val originId: String = "-1",
    val prefix: String = "",
    val projectLink: String = "",
    val publishTime: String = "",
    val realSuperChapterId: String = "",
    val selfVisible: String = "",
    val shareDate: String = "",
    val shareUser: String = "",
    val superChapterId: String = "",
    val superChapterName: String = "",
    val tags: List<Tag>? = null,
    val title: String = "",
    val type: String = "",
    val userId: String = "",
    val visible: String = "",
    val zan: String = ""
) : Parcelable

/**
 * "tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}]
 * "tags":[{"name":"项目","url":"/project/list/0?cid=294"}]
 * "tags": [{"name": "本站发布","url": "/article/list/0?cid=440"},{"name": "问答","url": "/wenda"}]
 * "tags": [{"name": "导航","url": "/navi#528"}]
 */
@Parcelize
data class Tag(
    val name: String = "",
    val url: String = ""
) : Parcelable
