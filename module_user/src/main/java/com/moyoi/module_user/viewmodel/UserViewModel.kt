package com.moyoi.module_user.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.base.ViewStatus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.bean.UserBean
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.module_user.api.UserApi

/**
 * @Description UserViewModel
 * @Author Lu
 * @Date 2021/6/20 16:35
 * @Version: 1.0
 */
@SuppressLint("CheckResult")
class UserViewModel<DATA> : BaseViewModel<DATA>() {

    //======================================积分======================================
    fun getUserCoin() {
        viewModelScope.ioLaunch {
            RetrofitManager.create<UserApi>().getCoinUser().run {
                when (errorCode) {
                    "0" -> {
                        DataHelper.setCoin(this.data)
                    }
                    else -> onLoadFailed(errorMsg)
                }
            }
        }
    }

    fun getCoinList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) {
                page = 1
                pageCount = 1
            } else page++
            if (page <= pageCount) {
                val data = RetrofitManager.create<UserApi>().getCoinList(page)
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

    fun getCoinRank(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) {
                page = 1
                pageCount = 1
            } else page++
            if (page <= pageCount) {
                val data = RetrofitManager.create<UserApi>().getCoinRank(page)
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

    //======================================分享======================================
    fun getPrivateArticleList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                RetrofitManager.create<UserApi>().getPrivateArticle(page).run {
                    when (errorCode) {
                        "0" -> {
                            this.data.shareArticles.datas?.let {
                                onLoadSuccess(it as DATA)
                            }
                            pageCount = this.data.shareArticles.curPage.toInt()
                        }
                        "-1001" -> statusResult.postValue(ViewStatus.NO_LOGIN)
                        else -> onLoadFailed(errorMsg)
                    }
                }
            }
        }
    }

    val addShareResult = MutableLiveData<Boolean>()
    fun addUserArticle(title: String, link: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<UserApi>().addUserArticle(title, link)
            when (data.errorCode) {
                "0" -> addShareResult.postValue(true)
                "-1001" -> ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                else -> addShareResult.postValue(false)
            }
        }
    }

    val delShareResult = MutableLiveData<Boolean>()
    fun delUserArticle(id: String) {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<UserApi>().delUserArticle(id)
            when (data.errorCode) {
                "0" -> delShareResult.postValue(true)
                else -> delShareResult.postValue(false)
            }
        }
    }

    //======================================登录======================================
    val userDataResult = MutableLiveData<UserBean>()
    fun login(username: String, password: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<UserApi>().login(username, password)
            when (data.errorCode) {
                "0" -> userDataResult.postValue(data.data)
                else -> userDataResult.postValue(null)
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.ioLaunch {
            val data =
                RetrofitManager.create<UserApi>().register(username, password, password)
            when (data.errorCode) {
                "0" -> userDataResult.postValue(data.data)
                else -> userDataResult.postValue(null)
            }
        }
    }

    val logoutResult = MutableLiveData<Boolean>()
    fun logout() {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<UserApi>().logout()
            when (data.errorCode) {
                "0" -> logoutResult.postValue(true)
                else -> logoutResult.postValue(false)
            }
        }
    }
}