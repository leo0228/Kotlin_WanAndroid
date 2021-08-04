package com.moyoi.library_common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_common.R
import com.moyoi.library_common.databinding.ItemHistorySearchBinding

/**
 * @Description 历史搜索适配器
 * @Author Lu
 * @Date 2021/7/6 12:09
 * @Version: 1.0
 */
class HistoryAdapter : BaseAdapter<String>() {

    init {
        addOnClickListener(R.id.history_delete)
    }

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemHistorySearchBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: String) {
        val binding = holder.binding as ItemHistorySearchBinding
        binding.historyName.text = item
    }
}