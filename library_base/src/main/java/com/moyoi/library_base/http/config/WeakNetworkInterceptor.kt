package com.moyoi.library_base.http.config

import com.moyoi.library_base.base.BaseApplication
import com.moyoi.library_base.base.BaseViewModel
import com.moyoi.library_base.base.ViewStatus
import com.moyoi.library_base.http.utils.NetWorkUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * @Description 网络适配器
 * @Author Lu
 * @Date 2021/7/22 11:10
 * @Version: 1.0
 */
class WeakNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetWorkUtils.isNetConnected(BaseApplication.context)) {
            val response = chain.proceed(chain.request())
            val responseBody =
                ResponseBody.create(response.body!!.contentType(), "")
            return response.newBuilder()
                .code(400)
                .message(
                    String.format(
                        "Unable to resolve host %s: No address associated with hostname",
                        chain.request().url.host
                    )
                )
                .body(responseBody)
                .build()
        } else {
            return chain.proceed(chain.request())
        }
    }
}