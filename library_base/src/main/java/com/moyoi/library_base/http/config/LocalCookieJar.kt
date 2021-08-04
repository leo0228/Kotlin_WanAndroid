package com.moyoi.library_base.http.config

import android.os.Build
import android.webkit.CookieManager
import androidx.annotation.RequiresApi
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


/**
 * @Description cookie的本地化存储
 * @Author Lu
 * @Date 2021/7/20 11:16
 * @Version: 1.0
 */
// internal 修饰类的方法，表示这个类方法只适合当前module使用
internal class LocalCookieJar : CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookieList: MutableList<Cookie> = ArrayList()
        CookieManager.getInstance().getCookie(url.toString())?.let { cookiesStr ->
            if (cookiesStr.isNotEmpty()) {
                val cookies = cookiesStr.split(";".toRegex())
                for (cookie in cookies) {
                    Cookie.parse(url, cookie)?.apply {
                        cookieList.add(this)
                    }
                }
            }
        }
        return cookieList
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        for (cookie in cookies) {
            cookieManager.setCookie(url.toString(), cookie.toString())
        }
        cookieManager.flush()
    }

}