package com.moyoi.module_message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_message.bean.MessageBean
import com.moyoi.module_message.databinding.ItemMessageBinding

/**
 * @Description MessageAdapter
 * @Author Lu
 * @Date 2021/7/7 12:28
 * @Version: 1.0
 */
class MessageAdapter : BaseAdapter<MessageBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemMessageBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: MessageBean) {
        val binding = holder.binding as ItemMessageBinding
        binding.msgTag.text = item.tag
        binding.msgTime.text = item.niceDate
        binding.msgFormUser.text = "@${item.fromUser}"
        binding.msgTitle.text = item.title
        binding.message.text = item.message

        binding.root.safeClick {
            ARouter.getInstance()
                .build(ARouterData.PATH_WEBVIEW)
                .withString(Keys.TITLE, item.title)
                .withString(Keys.URL, item.fullLink)
                .navigation()
        }
    }
}