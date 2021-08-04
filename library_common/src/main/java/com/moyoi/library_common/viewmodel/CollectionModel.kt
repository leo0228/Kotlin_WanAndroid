package com.moyoi.library_common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.base.ViewStatus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.http.KotlinSuspendApi
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch

/**
 * @Description CollectionModel
 * @Author Lu
 * @Date 2021/6/8 14:01
 * @Version: 1.0
 */
class CollectionModel<DATA> : BaseViewModel<DATA>() {

    fun getCollectArticleList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 0 else page++
            if (page <= pageCount) {
                val data =
                    RetrofitManager.create<KotlinSuspendApi>().getCollectArticleListAsync(page)
                when (data.errorCode) {
                    "0" -> {
                        data.data.datas?.let {
                            onLoadSuccess(it as DATA)
                        }
                        pageCount = data.data.pageCount.toInt()
                    }
                    "-1001" -> statusResult.postValue(ViewStatus.NO_LOGIN)
                    else -> onLoadFailed(data.errorMsg)
                }
            }
        }
    }

    fun getCollectWebsiteList() {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().getCollectWebsiteList()
            when (data.errorCode) {
                "0" -> {
                    data.data?.let {
                        onLoadSuccess(it as DATA)
                    }
                }
                "-1001" -> statusResult.postValue(ViewStatus.NO_LOGIN)
                else -> onLoadFailed(data.errorMsg)
            }
        }
    }


    val addCollectResult = MutableLiveData<Boolean>()
    fun addCollectArticle(id: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().addCollectArticleAsync(id)
            when (data.errorCode) {
                "0" -> addCollectResult.postValue(true)
                "-1001" -> ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                else -> addCollectResult.postValue(false)
            }
        }
    }

    fun addCollectOutsideArticle(title: String, author: String, link: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>()
                    .addCollectOutsideArticleAsync(title, author, link)
            when (data.errorCode) {
                "0" -> addCollectResult.postValue(true)
                "-1001" -> ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                else -> addCollectResult.postValue(false)
            }
        }
    }

    fun addCollectWebsite(name: String, link: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().addCollectWebsite(name, link)
            when (data.errorCode) {
                "0" -> addCollectResult.postValue(true)
                "-1001" -> ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                else -> addCollectResult.postValue(false)
            }
        }
    }

    val removeCollectResult = MutableLiveData<Boolean>()
    fun removeCollectArticle(id: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().removeCollectArticleAsync(id)
            when (data.errorCode) {
                "0" -> removeCollectResult.postValue(true)
                "-1001" -> ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                else -> removeCollectResult.postValue(false)
            }
        }
    }

    fun removeCollectPage(id: String, originId: String = "-1") {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().removeCollectPageAsync(id, originId)
            when (data.errorCode) {
                "0" -> removeCollectResult.postValue(true)
                else -> removeCollectResult.postValue(false)
            }
        }
    }

    fun removeCollectWebsite(id: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().removeCollectWebsite(id)
            when (data.errorCode) {
                "0" -> removeCollectResult.postValue(true)
                else -> removeCollectResult.postValue(false)
            }
        }
    }

    val updateCollectResult = MutableLiveData<Boolean>()
    fun updateCollectWebsite(id: String, name: String, link: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<KotlinSuspendApi>().updateCollectWebsite(id, name, link)
            when (data.errorCode) {
                "0" -> updateCollectResult.postValue(true)
                else -> updateCollectResult.postValue(false)
            }
        }
    }
}