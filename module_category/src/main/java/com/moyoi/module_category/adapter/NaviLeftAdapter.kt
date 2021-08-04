package com.moyoi.module_category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.module_category.R
import com.moyoi.module_category.bean.NaviBean
import com.moyoi.module_category.databinding.ItemLeftTextBinding

/**
 * @Description NaviLeftAdapter
 * @Author Lu
 * @Date 2021/6/10 17:27
 * @Version: 1.0
 */
class NaviLeftAdapter : BaseAdapter<NaviBean>() {

    private var index: Int = -1 //用于记录点击位置的变量，默认-1是指没有item的position被选中

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemLeftTextBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: NaviBean) {
        val binding = holder.binding as ItemLeftTextBinding
        binding.leftName.text = item.name

        //如果前面点击事件传递进来的点击的位置index和布局item的相同，则设置checked为true，判断进行变色
        val checked = position == index

        if (checked) {
            binding.leftName.setBackgroundResource(R.color.white)
        } else {
            binding.leftName.setBackgroundResource(R.color.background)
            if (position == 0 && index == -1) {
                binding.leftName.setBackgroundResource(R.color.white)
            }
        }
    }

    fun setChoose(pos: Int) {
        index = pos
        notifyDataSetChanged()
    }
}