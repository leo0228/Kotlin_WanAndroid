package com.moyoi.library_base.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.moyoi.library_base.db.SimpleDBHelper

/**
 * @Description 系统数据持久化辅助类
 * @Author Lu
 * @Date 2021/7/22 17:44
 * @Version: 1.0
 */
object SystemDataHelper {

    private const val UI_MODE = "ui_mode"
    private const val SCREEN_RECORD = "screen_record"

    /**
     * mode :
     *      -1 : AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
     *       1 : AppCompatDelegate.MODE_NIGHT_NO,
     *       2 : AppCompatDelegate.MODE_NIGHT_YES
     */
    fun setUIMode(mode: Int) {
        SimpleDBHelper.set(UI_MODE, mode.toString())
    }

    fun getUIMode(): LiveData<Int> {
        return Transformations.map(SimpleDBHelper.get(UI_MODE)) {
            try {
                it.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                1
            }
        }
    }

    /**
     * status :
     *       0 : 关闭,
     *       1 : 开启,
     */
    fun setScreenRecordStatus(status: Int) {
        SimpleDBHelper.set(SCREEN_RECORD, status.toString())
    }

    fun getScreenRecordStatus(): LiveData<Int> {
        return Transformations.map(SimpleDBHelper.get(SCREEN_RECORD)) {
            try {
                it.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }
    }
}