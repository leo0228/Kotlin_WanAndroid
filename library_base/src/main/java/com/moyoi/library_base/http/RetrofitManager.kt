package com.moyoi.library_base.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.moyoi.library_base.BuildConfig
import com.moyoi.library_base.base.BaseApplication
import com.moyoi.library_base.http.config.CacheInterceptor
import com.moyoi.library_base.http.config.LocalCookieJar
import com.moyoi.library_base.http.config.WeakNetworkInterceptor
import com.moyoi.library_base.utils.showInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Description RetrofitManager
 * @Author Lu
 * @Date 2021/6/3 16:47
 * @Version: 1.0
 */

object RetrofitManager {
    private const val BASE_URL = "https://wanandroid.com"
    private const val TIMEOUT = 10L
    private const val CACHE_SIZE = 100 * 1024 * 1024

    private var mRetrofit: Retrofit? = null

    //Retrofit缓存
    private val retrofitList: Map<String, Retrofit> = mutableMapOf()

    private var mOkClient: OkHttpClient? = null

    private fun getRetrofit(baseUrl: String = BASE_URL): Retrofit {
        if (retrofitList.isEmpty()) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())//协程
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        } else {
            mRetrofit = if (retrofitList.containsKey(baseUrl)) {
                retrofitList[baseUrl]
            } else {
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())//协程
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            }
        }
        return mRetrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (mOkClient == null) {
            val builder = OkHttpClient.Builder()
            builder.callTimeout(TIMEOUT, TimeUnit.SECONDS)
            builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            builder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
            builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)//重试
            builder.cookieJar(LocalCookieJar())
            builder.cache(Cache(BaseApplication.context.cacheDir, CACHE_SIZE.toLong()))
            builder.addInterceptor(CacheInterceptor())//使用缓存数据
//            builder.addInterceptor(WeakNetworkInterceptor())//断网弱网拦截器
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(
                    HttpLoggingInterceptor { message -> showInfo(message, "OkHttp") }.setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
            }
            mOkClient = builder.build()
        }
        return mOkClient!!
    }

    fun <T> getService(service: Class<T>, baseUrl: String = BASE_URL): T =
        getRetrofit(baseUrl).create(service)

    inline fun <reified T> create(): T = getService(T::class.java)

}