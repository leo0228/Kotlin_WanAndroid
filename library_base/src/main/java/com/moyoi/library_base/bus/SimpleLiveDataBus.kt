package com.moyoi.library_base.bus

import androidx.lifecycle.MutableLiveData

/**
 * @Description 及时更新数据
 * @Author Lu
 * @Date 2021/6/29 14:46
 * @Version: 1.0
 */
class SimpleLiveDataBus {
    private var bus: MutableMap<String, MutableLiveData<Any>> = HashMap()

    companion object {

        private var liveDataBus: SimpleLiveDataBus? = null

        @JvmStatic
        fun getInstance() = liveDataBus ?: synchronized(SimpleLiveDataBus::class.java) {
            liveDataBus ?: SimpleLiveDataBus().also { bus -> liveDataBus = bus }
        }

    }

    @Synchronized
    fun <T> with(key: String): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = MutableLiveData()
        }
        return bus[key] as MutableLiveData<T>
    }

}