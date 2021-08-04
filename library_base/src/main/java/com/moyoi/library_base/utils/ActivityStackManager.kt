package com.moyoi.library_base.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.moyoi.library_base.R

/**
 * @Description [AppCompatActivity]栈管理
 * @Author Lu
 * @Date 2021/7/22 17:07
 * @Version: 1.0
 */
object ActivityStackManager {

    private val activityList = mutableListOf<AppCompatActivity>()

    /**
     * 添加一个[AppCompatActivity]实例
     */
    fun addActivity(activity: AppCompatActivity) {
        activityList.add(activity)
    }

    /**
     * 移除指定[AppCompatActivity]实例
     */
    fun removeActivity(activity: AppCompatActivity) {
        activityList.remove(activity)
    }

    /**
     * finish所有[AppCompatActivity]
     */
    fun finishAll() {
        activityList.takeIf { it.size > 0 }?.let {
            activityList.forEach {
                if (!it.isFinishing && !it.isDestroyed) {
                    it.finish()
                }
            }
        }
    }

}