package com.moyoi.library_common.utils

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * @Description GameBox
 * @Author Lu
 * @Date 2021/6/22 18:26
 * @Version: 1.0
 */

//对应xml中ImageView控件中的app:loadImageUrl="@{viewModel.pictureUrl}",必须添加<data><variable>
object DataBindingHelper {
    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImageUrl(imageView: ImageView, pictureUrl: String?) {
        if (!TextUtils.isEmpty(pictureUrl)) {
            Glide.with(imageView.context)
                .asGif()
                .load(pictureUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }
}

