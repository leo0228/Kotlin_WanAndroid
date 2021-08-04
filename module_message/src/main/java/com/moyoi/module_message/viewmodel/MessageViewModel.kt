package com.moyoi.module_message.viewmodel

import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.base.ViewStatus
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.constant.Constants
import com.moyoi.module_message.api.MessageApi
import com.moyoi.module_message.bean.MessageBean

/**
 * @Description MessageViewModel
 * @Author Lu
 * @Date 2021/7/6 10:32
 * @Version: 1.0
 */
class MessageViewModel : BaseViewModel<List<MessageBean>>() {

    fun getReadMessageList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                RetrofitManager.create<MessageApi>().getReadMessageList(page).run {
                    when (errorCode) {
                        "0" -> {
                            this.data.datas?.let {
                                onLoadSuccess(it)
                            }
                            pageCount = this.data.pageCount.toInt()
                        }
                        "-1001" -> statusResult.postValue(ViewStatus.NO_LOGIN)
                        else -> onLoadFailed(errorMsg)
                    }
                }
            }
        }
    }

    fun getUnReadMessageList(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            if (page <= pageCount) {
                RetrofitManager.create<MessageApi>().getUnReadMessageList(page).run {
                    when (errorCode) {
                        "0" -> {
                            this.data.datas?.let {
                                onLoadSuccess(it)
                            }
                            pageCount = this.data.pageCount.toInt()
                        }
                        "-1001" -> statusResult.postValue(ViewStatus.NO_LOGIN)
                        else -> onLoadFailed(errorMsg)
                    }
                }
            }
        }
    }
}