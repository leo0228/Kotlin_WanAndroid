package com.moyoi.library_base.utils

import android.content.Context

/**
 * @Description 缓存工具类
 * @Author Lu
 * @Date 2021/7/14 12:10
 * @Version: 1.0
 */
object CacheUtil {
    fun getTotalCacheSize(context: Context): String {
        var cacheSize = FileUtils.getSize(context.cacheDir)
        if (FileUtils.isSDCardAlive()) {
            context.externalCacheDir?.apply {
                cacheSize += FileUtils.getSize(this)
            }
        }
        return FileUtils.formatSize(cacheSize.toDouble())
    }

    fun clearAllCache(context: Context) {
        FileUtils.delete(context.cacheDir)
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(context.externalCacheDir)
        }
    }
}