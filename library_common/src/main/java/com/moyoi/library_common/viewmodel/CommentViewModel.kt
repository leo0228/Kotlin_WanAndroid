package com.moyoi.library_common.viewmodel

import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.bean.WendaCommentsBean
import com.moyoi.library_common.http.KotlinSuspendApi

/**
 * @Description CommentViewModel
 * @Author Lu
 * @Date 2021/7/9 15:13
 * @Version: 1.0
 */
class CommentViewModel : BaseViewModel<List<WendaCommentsBean>>() {

    fun getWendaComments(id: String) {
        viewModelScope.ioLaunch {
            val data = RetrofitManager.create<KotlinSuspendApi>().getWendaCommentsAsync(id)
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