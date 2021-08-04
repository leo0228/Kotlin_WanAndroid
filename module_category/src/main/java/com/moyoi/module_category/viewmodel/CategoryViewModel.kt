package com.moyoi.module_category.viewmodel

import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.module_category.api.CategoryApi

/**
 * @Description CategoryViewModel
 * @Author Lu
 * @Date 2021/7/6 9:50
 * @Version: 1.0
 */
class CategoryViewModel<DATA> : BaseViewModel<DATA>() {

    //======================================各体系文章列表======================================
    fun getArticleList(isRefresh: Boolean, cid: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<CategoryApi>().getArticleList(page, cid)
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

    //======================================导航======================================
    fun getNaviList() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<CategoryApi>().getNaviList()
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

    //======================================项目======================================
    fun getProjectTreeList() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<CategoryApi>().getProjectTreeList()
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

    fun getProjectList(isRefresh: Boolean, cid: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<CategoryApi>().getProjectArticleList(page, cid)
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

    //======================================常用网址======================================
    fun getWebsiteList() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<CategoryApi>().getWebsiteList()
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

}