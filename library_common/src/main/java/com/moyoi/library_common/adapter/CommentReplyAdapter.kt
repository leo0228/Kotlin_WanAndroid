package com.moyoi.library_common.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_common.bean.WendaCommentsBean
import com.moyoi.library_common.databinding.ItemCommentReplyBinding

/**
 * @Description 回复适配器
 * @Author Lu
 * @Date 2021/7/9 14:45
 * @Version: 1.0
 */
class CommentReplyAdapter : BaseAdapter<WendaCommentsBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemCommentReplyBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: WendaCommentsBean) {
        val binding = holder.binding as ItemCommentReplyBinding
        binding.username.text = item.userName
        binding.toUserName.text = "@${item.toUserName}"
        binding.time.text = item.niceDate
        binding.content.text = Html.fromHtml(item.content)
    }
}