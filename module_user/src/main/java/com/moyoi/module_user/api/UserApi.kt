package com.moyoi.module_user.api

import com.moyoi.library_common.bean.ShareArticleData
import com.moyoi.library_common.bean.UserData
import com.moyoi.library_common.http.HttpResponse
import com.moyoi.module_user.bean.CoinRankData
import com.moyoi.module_user.bean.MyCoinListData
import com.moyoi.module_user.bean.UserCoinData
import retrofit2.http.*

/**
 * @Description user_api
 * @Author Lu
 * @Date 2021/7/20 12:05
 * @Version: 1.0
 */
interface UserApi {

    //======================================分享======================================
    /**
     * https://wanandroid.com/user/lg/private_articles/1/json
     * 自己的分享的文章列表
     * 页码，从1开始
     */
    @GET("/user/lg/private_articles/{page}/json")
    suspend fun getPrivateArticle(@Path("page") page: Int): ShareArticleData


    /**
     * https://www.wanandroid.com/lg/user_article/add/json
     * 分享文章
     */
    @POST("/lg/user_article/add/json")
    @FormUrlEncoded //没有Field标签的不需要添加
    suspend fun addUserArticle(
        @Field("title") title: String,
        @Field("link") link: String
    ): HttpResponse

    /**
     * https://wanandroid.com/lg/user_article/delete/9475/json
     * 删除自己分享的文章
     * 文章id，拼接在链接上
     */
    @POST("/lg/user_article/delete/{id}/json")
    suspend fun delUserArticle(@Path("id") id: String): HttpResponse

    //======================================积分======================================
    /**
     * https://www.wanandroid.com/coin/rank/1/json
     * 积分排行榜
     */
    @GET("/coin/rank/{page}/json")
    suspend fun getCoinRank(@Path("page") page: Int): CoinRankData

    /**
     * https://www.wanandroid.com/lg/coin/userinfo/json
     * 获取个人积分，需要登录后访问
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getCoinUser(): UserCoinData

    /**
     * https://www.wanandroid.com/lg/coin/list/1/json
     * 获取个人积分获取列表，需要登录后访问
     */
    @GET("/lg/coin/list/{page}/json")
    suspend fun getCoinList(@Path("page") page: Int): MyCoinListData

    //======================================用户登录，注册======================================
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): UserData

    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): UserData

    @GET("/user/logout/json")
    suspend fun logout(): HttpResponse
}