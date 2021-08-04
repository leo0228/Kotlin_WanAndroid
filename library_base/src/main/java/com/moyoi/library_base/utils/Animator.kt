package com.moyoi.library_base.utils

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView

/**
 * @Description WanAndroid
 * @Author Lu
 * @Date 2021/7/20 16:38
 * @Version: 1.0
 */


/**
 * 积分显示动画
 */
fun numberAnimator(view: TextView, number: String) {
    val from = view.text.toString().toInt()
    val to = number.toInt()
    val animator = ValueAnimator.ofInt(from, to)
    animator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        view.text = String.format("%d", value)
    }
    animator.duration = 1000
    animator.interpolator = DecelerateInterpolator()
    animator.start()
}