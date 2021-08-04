package com.moyoi.library_base.base

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyoi.library_base.utils.showToast
import kotlinx.coroutines.cancel

/**
 * @Description BaseViewModel
 * @Author Lu
 * @Date 2021/6/20 15:08
 * @Version: 1.0
 */
open class BaseViewModel<DATA> : ViewModel(), BaseLoadListener<DATA> {
    var page = 0
    var pageCount = 1
    var isRefresh = true

    val dataResult = MutableLiveData<DATA>()
    val statusResult = MutableLiveData<ViewStatus>()

    override fun onLoadSuccess(data: DATA) {
        if (data is List<*>) {
            if (data.isEmpty()) {
                statusResult.postValue(ViewStatus.EMPTY)
            } else {
                statusResult.postValue(ViewStatus.SHOW_CONTENT)
                dataResult.postValue(data)
            }
        }

        if (data is Parcelable) {
            statusResult.postValue(ViewStatus.SHOW_CONTENT)
            dataResult.postValue(data)
        }

    }

    override fun onLoadFailed(errorMessage: String) {
        dataResult.postValue(null)
        statusResult.postValue(ViewStatus.ERROR)
        errorMessage.showToast()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
