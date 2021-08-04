package com.moyoi.module_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.bean.HotKeyBean
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.module_home.api.HomeApi
import com.moyoi.module_home.bean.BannerBean

/**
 * @Description GameBox
 * @Author Lu
 * @Date 2021/6/20 15:08
 * @Version: 1.0
 */
class HomeViewModel : BaseViewModel<List<ArticleBean>>() {

    val bannerResult = MutableLiveData<List<BannerBean>>()
    val topArticleListResult = MutableLiveData<List<ArticleBean>>()
    val hotKeyResult = MutableLiveData<List<HotKeyBean>>()

    fun getBanner() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<HomeApi>().getBannerList()
            when (data.errorCode) {
                "0" -> {
                    data.data?.let {
                        bannerResult.postValue(it)
                    }
                }
                else -> bannerResult.postValue(null)
            }
        }
    }

    fun getHomeArticleList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<HomeApi>().getHomeArticleList(page)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it)
                        }
                        pageCount = data.data.pageCount.toInt()
                        getTopArticleList()
                    }
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    private fun getTopArticleList() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<HomeApi>().getTopArticleList()
            when (data.errorCode) {
                "0" -> {
                    data.data?.let {
                        topArticleListResult.postValue(it)
                    }
                }
                else -> topArticleListResult.postValue(null)
            }
        }
    }

    fun getHotKey() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<HomeApi>().getHotkeyList()
            when (data.errorCode) {
                "0" -> {
                    data.data?.let {
                        hotKeyResult.postValue(it)
                        DataHelper.setHotkey(it)
                    }
                }
                else -> topArticleListResult.postValue(null)
            }
        }
    }


    fun getTree() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<HomeApi>().getTreeList()
            data.data?.apply {
                DataHelper.setTreeList(this)
            }
        }
    }
}