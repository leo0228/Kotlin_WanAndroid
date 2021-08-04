package com.moyoi.module_square.viewmodel

import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.module_square.api.SquareApi

/**
 * @Description SquareViewModel
 * @Author Lu
 * @Date 2021/7/6 10:36
 * @Version: 1.0
 */
class SquareViewModel<DATA> : BaseViewModel<DATA>() {

    //======================================问答======================================
    fun getWenDaList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                val data = RetrofitManager.create<SquareApi>().getWendaListAsync(page)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it as DATA)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    //======================================广场======================================
    fun getSquareArticleList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page < pageCount) {
                val data = RetrofitManager.create<SquareApi>().getSquareArticleListAsync(page)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it as DATA)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    //======================================公众号======================================
    fun getWxPublicTree() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<SquareApi>().getWxPublicAsync()
            when (data.errorCode) {
                "0" -> {
                    data.data?.let {
                        onLoadSuccess(it as DATA)
                    }
                }
                else -> onLoadFailed(data.errorMsg)
            }
        }
    }

    fun getWxArticleList(isRefresh: Boolean, id: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                val data = RetrofitManager.create<SquareApi>().getWxArticleListAsync(id, page)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it as DATA)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    fun searchWxArticleList(isRefresh: Boolean, id: String, k: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<SquareApi>().searchWxArticleListAsync(id, page, k)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it as DATA)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }
}