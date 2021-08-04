package com.moyoi.library_common.utils

import android.app.Activity
import android.view.ViewGroup
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager

/**
 * @Description WanAndroid
 * @Author Lu
 * @Date 2021/7/20 16:37
 * @Version: 1.0
 */

/**
 * getAgentWeb
 */
fun String.getAgentWeb(
    activity: Activity, webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    receivedTitleCallback: ChromeClientCallbackManager.ReceivedTitleCallback?
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()// 使用默认进度条
    .defaultProgressBarColor() // 使用默认进度条颜色
    .setReceivedTitleCallback(receivedTitleCallback) //设置 Web 页面的 title 回调
    .createAgentWeb()
    .ready()
    .go(this)!!