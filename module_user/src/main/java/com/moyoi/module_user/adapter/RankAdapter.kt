package com.moyoi.module_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.module_user.databinding.ItemRankBinding

/**
 * @Description RankAdapter
 * @Author Lu
 * @Date 2021/6/24 17:50
 * @Version: 1.0
 */
class RankAdapter : BaseAdapter<CoinBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemRankBinding::inflate
    }

    override fun onItemView(
        holder: ViewBindHolder,
        position: Int,
        item: CoinBean
    ) {
        val binding = holder.binding as ItemRankBinding
        binding.rankNum.text = (position + 4).toString()
        binding.rankUsername.text = item.username
        binding.rankCoinCount.text = item.coinCount
    }
}