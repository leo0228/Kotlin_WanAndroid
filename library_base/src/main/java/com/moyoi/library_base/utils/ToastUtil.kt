package com.moyoi.library_base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.moyoi.library_base.base.BaseApplication

/**
 * @Description ToastUtil
 * @Author Lu
 * @Date 2021/7/19 13:50
 * @Version: 1.0
 */

@SuppressLint("ShowToast")
fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).show()
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, this.resources.getString(resId), duration).show()
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseApplication.context, this, duration).show()
}

