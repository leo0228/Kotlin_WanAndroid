package com.moyoi.library_common.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_common.bean.WendaCommentsBean
import com.moyoi.library_common.databinding.ItemCommentBinding

/**
 * @Description 评论适配器
 * @Author Lu
 * @Date 2021/7/9 14:41
 * @Version: 1.0
 */
class CommentAdapter : BaseAdapter<WendaCommentsBean>() {
    private val mCommentReplyAdapter: CommentReplyAdapter by lazy {
        CommentReplyAdapter()
    }

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemCommentBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: WendaCommentsBean) {
        val binding = holder.binding as ItemCommentBinding

        mCommentReplyAdapter.setNewData(getItem(position).replyComments)

        binding.username.text = item.userName
        binding.time.text = item.niceDate
        binding.content.text = Html.fromHtml(item.contentMd)
        binding.recyclerView.adapter = mCommentReplyAdapter
    }
}