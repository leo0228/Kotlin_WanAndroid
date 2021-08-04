package com.moyoi.library_base.utils

import android.content.Context
import com.moyoi.library_base.base.BaseApplication

object MetricsUtil {
    /**
     * 获取屏幕宽度
     */
    val screenWidth: Int
        get() = getScreenWidth(BaseApplication.context)

    fun getScreenWidth(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return metrics.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    val screenHeight: Int
        get() = getScreenHeight(BaseApplication.context)

    fun getScreenHeight(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return metrics.heightPixels
    }

    val densityDpi: Int
        get() = getDensityDpi(BaseApplication.context)

    fun getDensityDpi(context: Context): Int {
        return context.resources.displayMetrics.densityDpi
    }

    /**
     * dp转px
     */
    fun dp2px(dp: Float): Float {
        return dp2px(BaseApplication.context, dp)
    }

    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f)
    }

    /**
     * px转dp
     */
    fun px2dp(px: Float): Float {
        return px2dp(BaseApplication.context, px)
    }

    fun px2dp(context: Context, px: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f)
    }

    /**
     * px转sp
     */
    fun px2sp(px: Float): Float {
        return px2sp(BaseApplication.context, px)
    }

    fun px2sp(context: Context, px: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (px / fontScale + 0.5f)
    }

    /**
     * sp转px
     */
    fun sp2px(sp: Float): Float {
        return sp2px(BaseApplication.context, sp)
    }

    fun sp2px(context: Context, sp: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (sp * fontScale + 0.5f)
    }
}