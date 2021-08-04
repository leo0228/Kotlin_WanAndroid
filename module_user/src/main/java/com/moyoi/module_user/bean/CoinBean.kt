package com.moyoi.module_user.bean

import com.moyoi.library_common.bean.CoinBean
import com.moyoi.library_common.http.HttpResponse

/**
 * @Description 积分相关bean
 * @Author Lu
 * @Date 2021/6/28 10:19
 * @Version: 1.0
 */

data class UserCoinData(
    val data: CoinBean
) : HttpResponse()

data class MyCoinListData(
    val data: CoinData<MyCoinBean>
) : HttpResponse()

data class CoinRankData(
    val data: CoinData<CoinBean>
) : HttpResponse()

data class CoinData<T>(
    val curPage: String = "",
    val datas: List<T>? = null,
    val offset: String = "",
    val over: Boolean = false,
    val pageCount: String = "",
    val size: String = "",
    val total: String = ""
)

data class MyCoinBean(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)

