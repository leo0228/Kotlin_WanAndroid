package com.moyoi.module_home.api

import com.moyoi.library_common.bean.*
import com.moyoi.module_home.bean.BannerListData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Description home_api
 * @Author Lu
 * @Date 2021/7/20 11:51
 * @Version: 1.0
 */
interface HomeApi {
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): ArticleListData//首页文章列表

    @GET("/banner/json")
    suspend fun getBannerList(): BannerListData//首页banner

    @GET("/hotkey/json")
    suspend fun getHotkeyList(): HotkeyListData//搜索热词

    @GET("/article/top/json")
    suspend fun getTopArticleList(): TopArticleListData//置顶文章

    //https://www.wanandroid.com/tree/json
    @GET("/tree/json")
    suspend fun getTreeList(): TreeListData//体系数据
}