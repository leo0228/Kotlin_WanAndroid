package com.moyoi.library_base.base

/**
 * @Description WanAndroid
 * @Author Lu
 * @Date 2021/7/29 13:02
 * @Version: 1.0
 */
interface BaseLoadListener<DATA> {
    fun onLoadSuccess(data: DATA)
    fun onLoadFailed(errorMessage: String)
}