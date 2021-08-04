package com.moyoi.library_base.http.config

import com.moyoi.library_base.base.BaseApplication
import com.moyoi.library_base.http.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @Description 缓存拦截器
 * @Author Lu
 * @Date 2021/7/22 9:52
 * @Version: 1.0
 */
class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if (!NetWorkUtils.isNetConnected(BaseApplication.context)) {
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE) // 直接使用缓存
        }
        return chain.proceed(requestBuilder.build())
    }
}