package com.moyoi.library_common.viewmodel

import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.http.RetrofitManager
import com.moyoi.library_base.utils.ioLaunch
import com.moyoi.library_common.bean.ShareArticleData
import com.moyoi.library_common.bean.UserShareData
import com.moyoi.library_common.http.KotlinSuspendApi

/**
 * @Description SquareUserShareViewModel
 * @Author Lu
 * @Date 2021/7/8 11:22
 * @Version: 1.0
 */
class UserShareViewModel : BaseViewModel<UserShareData>() {

    fun getUserShareArticleList(isRefresh: Boolean, userId: String) {
        this.isRefresh = isRefresh
        viewModelScope.ioLaunch {
            if (isRefresh) page = 1 else page++
            RetrofitManager.create<KotlinSuspendApi>().getUserShareArticleAsync(userId, page).run {
                when (errorCode) {
                    "0" -> {
                        onLoadSuccess(this.data)
                        this.data.shareArticles.curPage.toInt()
                    }
                    else -> onLoadFailed(errorMsg)
                }
            }
        }
    }
}