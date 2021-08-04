package com.moyoi.module_category.api

import com.moyoi.library_common.bean.ArticleListData
import com.moyoi.library_common.bean.TreeListData
import com.moyoi.library_common.bean.WebsiteListData
import com.moyoi.module_category.bean.NaviListData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Description category_api
 * @Author Lu
 * @Date 2021/7/20 12:16
 * @Version: 1.0
 */
interface CategoryApi {

    @GET("/friend/json")
    suspend fun getWebsiteList(): WebsiteListData //常用网站

    //https://www.wanandroid.com/navi/json
    @GET("/navi/json")
    suspend fun getNaviList(): NaviListData//导航数据

    //https://www.wanandroid.com/article/list/1/json?cid=294
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: String
    ): ArticleListData //知识体系下的文章

    //https://www.wanandroid.com/project/list/1/json?cid=294
    @GET("/project/list/{page}/json")
    suspend fun getProjectArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: String
    ): ArticleListData //项目列表数据

    //https://www.wanandroid.com/project/tree/json
    @GET("/project/tree/json")
   suspend fun getProjectTreeList(): TreeListData//项目分类

}