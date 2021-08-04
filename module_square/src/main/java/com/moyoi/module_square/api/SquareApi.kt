package com.moyoi.module_square.api

import com.moyoi.library_common.bean.ArticleListData
import com.moyoi.library_common.bean.TreeListData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Description square_api
 * @Author Lu
 * @Date 2021/7/20 12:01
 * @Version: 1.0
 */
interface SquareApi {
    //======================================广场======================================

    /**
     * 广场列表数据
     */
    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleListAsync(@Path("page") page: Int): ArticleListData

    //======================================问答======================================

    /**
     * https://wanandroid.com/wenda/list/1/json
     * 问答
     * pageId,拼接在链接上，例如上面的1
     */
    @GET("/wenda/list/{page}/json")
    suspend fun getWendaListAsync(@Path("page") page: Int): ArticleListData

    //======================================公众号======================================

    /**
     * https://wanandroid.com/wxarticle/chapters/json
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
   suspend fun getWxPublicAsync(): TreeListData

    /**
     * https://wanandroid.com/wxarticle/list/408/1/json
     * 查看某个公众号历史数据
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getWxArticleListAsync(
        @Path("id") wxId: String,
        @Path("page") page: Int
    ): ArticleListData

    /**
     * https://wanandroid.com/wxarticle/list/405/1/json?k=Java
     * 在某个公众号中搜索历史文章
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     * k : 字符串，eg:Java
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun searchWxArticleListAsync(
        @Path("id") wxId: String,
        @Path("page") page: Int,
        @Query("k") k: String
    ): ArticleListData
}