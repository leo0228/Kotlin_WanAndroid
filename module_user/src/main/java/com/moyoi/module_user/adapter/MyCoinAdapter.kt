package com.moyoi.module_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.module_user.bean.MyCoinBean
import com.moyoi.module_user.databinding.ItemMyCoinBinding

/**
 * @Description MyCoinAdapter
 * @Author Lu
 * @Date 2021/6/24 17:50
 * @Version: 1.0
 */
class MyCoinAdapter : BaseAdapter<MyCoinBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemMyCoinBinding::inflate
    }

    override fun onItemView(
        holder: ViewBindHolder,
        position: Int,
        item: MyCoinBean
    ) {
        val binding = holder.binding as ItemMyCoinBinding
        val desc = item.desc //2021-06-24 09:12:33 签到 , 积分：10 + 7
        val firstSpace = desc.indexOf(" ")
        val secondSpace = desc.indexOf(" ", firstSpace + 1)
        val time = desc.substring(0, secondSpace) //2021-06-24 09:12:33
        val title = desc.substring(secondSpace + 1) //签到积分10+7
            .replace(",", "")
            .replace("：", "")
            .replace(" ", "")

        binding.coinCount.text = "+${item.coinCount}"
        binding.title.text = title
        binding.time.text = time
    }
}