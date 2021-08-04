package com.moyoi.library_common.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.db.SimpleDBHelper
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.library_common.bean.HotKeyBean
import com.moyoi.library_common.bean.TreeBean
import com.moyoi.library_common.bean.UserBean
import com.moyoi.library_common.constant.Constants

/**
 * @Description 数据持久化辅助类
 * @Author Lu
 * @Date 2021/6/29 14:37
 * @Version: 1.0
 */
object DataHelper {

    private const val USER = "user"
    private const val COIN = "coin"
    private const val HOT_KEY = "hot_key"
    private const val HISTORY_SEARCH = "history_search"
    private const val TREE_LIST = "tree_list"

    fun setUser(userBean: UserBean) {
        SimpleLiveDataBus.getInstance().with<UserBean>(Constants.USER_STATUS_UPDATE)
            .postValue(userBean)
        SimpleDBHelper.set(USER, userBean.toJson())
    }

    fun getUser(): LiveData<UserBean> {
        return Transformations.map(SimpleDBHelper.get(USER)) {
            try {
                Gson().fromJson(it.toString(), UserBean::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun setCoin(coinBean: CoinBean) {
        SimpleLiveDataBus.getInstance().with<CoinBean>(Constants.USER_COIN_UPDATE)
            .postValue(coinBean)
        SimpleDBHelper.set(COIN, coinBean.toJson())
    }

    fun getCoin(): LiveData<CoinBean> {
        return Transformations.map(SimpleDBHelper.get(COIN)) {
            try {
                Gson().fromJson(it.toString(), CoinBean::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun setTreeList(list: List<TreeBean>) {
        SimpleDBHelper.set(TREE_LIST, Gson().toJson(list))
    }

    fun getTreeList(): LiveData<List<TreeBean>> {
        return Transformations.map(SimpleDBHelper.get(TREE_LIST)) {
            try {
                Gson().fromJson(it.toString(), object : TypeToken<List<TreeBean>>() {}.type)
            } catch (e: Exception) {
                e.printStackTrace()
                ArrayList()
            }
        }
    }

    fun setHotkey(list: List<HotKeyBean>) {
        SimpleDBHelper.set(HOT_KEY, Gson().toJson(list))
    }

    fun getHotkey(): LiveData<List<HotKeyBean>> {
        return Transformations.map(SimpleDBHelper.get(HOT_KEY)) {
            try {
                Gson().fromJson(it.toString(), object : TypeToken<List<HotKeyBean>>() {}.type)
            } catch (e: Exception) {
                e.printStackTrace()
                ArrayList()
            }
        }
    }

    fun setHistorySearch(list: List<String>) {
        SimpleDBHelper.set(HISTORY_SEARCH, Gson().toJson(list))
    }

    fun getHistorySearch(): LiveData<List<String>> {
        return Transformations.map(SimpleDBHelper.get(HISTORY_SEARCH)) {
            try {
                Gson().fromJson(it.toString(), object : TypeToken<List<String>>() {}.type)
            } catch (e: Exception) {
                e.printStackTrace()
                ArrayList()
            }
        }
    }
}