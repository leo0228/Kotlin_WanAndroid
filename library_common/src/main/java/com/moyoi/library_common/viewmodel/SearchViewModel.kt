package com.moyoi.library_common.viewmodel

import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.http.KotlinSuspendApi

/**
 * @Description SearchViewModel
 * @Author Lu
 * @Date 2021/6/10 13:20
 * @Version: 1.0
 */
class SearchViewModel : BaseViewModel<List<ArticleBean>>() {

    fun searchArticleListByK(isRefresh: Boolean, k: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page <= pageCount) {
                val data = RetrofitManager.create<KotlinSuspendApi>().searchListByKAsync(page, k)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    fun searchArticleListByAuthor(isRefresh: Boolean, author: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<KotlinSuspendApi>().searchListByAuthorAsync(page, author)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }
}