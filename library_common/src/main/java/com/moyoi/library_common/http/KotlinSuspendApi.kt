package com.moyoi.library_common.http

import com.moyoi.library_common.bean.*
import retrofit2.http.*

/**
 * @Description Retrofit和协程
 * @Author Lu
 * @Date 2021/6/3 17:05
 * @Version: 1.0
 */
interface KotlinSuspendApi {

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    suspend fun searchListByKAsync(
        @Path("page") page: Int,
        @Field("k") k: String
    ): ArticleListData

    /**
     * 搜索
     * https://wanandroid.com/article/list/0/json?author=鸿洋
     * @param page page
     * @param author：作者昵称，不支持模糊匹配。
     */
    @GET("/article/list/{page}/json")
    suspend fun searchListByAuthorAsync(
        @Path("page") page: Int,
        @Query("author") author: String
    ): ArticleListData


    //======================================收藏======================================

    /**
     * 获取自己收藏的文章列表
     * @param page page
     * @return Deferred<ArticleResponse>
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticleListAsync(@Path("page") page: Int): ArticleListData

    /**
     * 收藏文章
     * @param id id
     * @return Deferred<ArticleResponse>
     */
    @POST("/lg/collect/{id}/json")
    suspend fun addCollectArticleAsync(@Path("id") id: String): HttpResponse

    /**
     * 收藏站外文章
     * @param title title
     * @param author author
     * @param link link
     * @return Deferred<ArticleResponse>
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    suspend fun addCollectOutsideArticleAsync(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String
    ): HttpResponse

    /**
     * https://www.wanandroid.com/lg/uncollect/2805/json
     * 取消收藏--我的收藏页面
     * @param id id
     * @param originId -1
     * @return Deferred<ArticleResponse>
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    suspend fun removeCollectPageAsync(
        @Path("id") id: String,
        @Field("originId") originId: String = "-1"
    ): HttpResponse

    /**
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     * 取消收藏--文章列表
     * @param id id
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun removeCollectArticleAsync(
        @Path("id") id: String
    ): HttpResponse

    /**
     * 收藏网站列表
     * https://www.wanandroid.com/lg/collect/usertools/json
     * 方法：GET
     */
    @GET("/lg/collect/usertools/json")
    suspend fun getCollectWebsiteList(): WebsiteListData

    /**
     * 收藏网址
     * https://www.wanandroid.com/lg/collect/addtool/json
     * 方法：POST
     * 参数：name,link
     */
    @POST("/lg/collect/addtool/json")
    @FormUrlEncoded
    suspend fun addCollectWebsite(
        @Field("name") name: String,
        @Field("link") link: String
    ): HttpResponse


    /**
     * 编辑收藏网站
     * https://www.wanandroid.com/lg/collect/updatetool/json
     * 方法：POST
     * 参数：id,name,link
     */
    @POST("/lg/collect/updatetool/json")
    @FormUrlEncoded
    suspend fun updateCollectWebsite(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("link") link: String
    ): HttpResponse

    /**
     * 删除收藏网站
     * https://www.wanandroid.com/lg/collect/deletetool/json
     * 方法：POST
     * 参数：id
     */
    @POST("/lg/collect/deletetool/json")
    @FormUrlEncoded
    suspend fun removeCollectWebsite(
        @Field("id") id: String
    ): HttpResponse

    //======================================分享======================================

    /**
     * https://www.wanandroid.com/user/2/share_articles/1/json
     * 分享人对应列表数据
     * 用户id: 拼接在url上
     * 页码拼接在url上从1开始
     */
    @GET("/user/{userid}/share_articles/{page}/json")
    suspend fun getUserShareArticleAsync(
        @Path("userid") userid: String,
        @Path("page") page: Int
    ): ShareArticleData


    //======================================问答======================================
    /**
     * https://wanandroid.com/wenda/comments/14500/json
     * 问答评论列表
     * 问答 id，可以通过问答列表获取
     * 方法：GET
     */
    @GET("/wenda/comments/{id}/json")
    suspend fun getWendaCommentsAsync(
        @Path("id") id: String
    ): WendaCommentsListData
}