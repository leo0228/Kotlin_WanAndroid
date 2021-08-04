package com.moyoi.module_message.api

import com.moyoi.module_message.bean.UnOrReadMessageListData
import com.moyoi.module_message.bean.UnReadMessageNumData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Description message_api
 * @Author Lu
 * @Date 2021/7/20 11:58
 * @Version: 1.0
 */
interface MessageApi {
    /**
     * https://wanandroid.com/message/lg/count_unread/json
     * 方法：GET
     * 未读消息数量
     */
    @GET("/message/lg/count_unread/json")
    suspend fun getUnReadMessageNum(): UnReadMessageNumData

    /**
     * https://wanandroid.com/message/lg/readed_list/页码/json
     * 注意页码从 1 开始
     * 方法：GET
     * 已读消息列表
     */
    @GET("/message/lg/readed_list/{page}/json")
    suspend fun getReadMessageList(@Path("page") page: Int): UnOrReadMessageListData

    /**
     * https://wanandroid.com/message/lg/unread_list/页码/json
     * 注意页码从 1 开始
     * 方法：GET
     * 未读消息列表
     */
    @GET("/message/lg/unread_list/{page}/json")
    suspend fun getUnReadMessageList(@Path("page") page: Int): UnOrReadMessageListData
}