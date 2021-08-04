package com.moyoi.module_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_common.bean.HotKeyBean
import com.moyoi.module_home.databinding.ItemHotKeyTextBinding

class HotKeyAdapter : BaseAdapter<HotKeyBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemHotKeyTextBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: HotKeyBean) {
        val binding = holder.binding as ItemHotKeyTextBinding
        binding.hotKey.text = item.name
    }

}