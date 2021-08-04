package com.moyoi.module_category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.bean.TreeBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.LayoutFlexboxBinding
import com.moyoi.module_category.R


/**
 * @Description SystemRightAdapter
 * @Author Lu
 * @Date 2021/6/11 11:49
 * @Version: 1.0
 */
class SystemRightAdapter : BaseAdapter<TreeBean>() {

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return LayoutFlexboxBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: TreeBean) {
        val binding = holder.binding as LayoutFlexboxBinding
        binding.flexbox.removeAllViews()

        item.children?.forEach { childTree ->
            val inflater = LayoutInflater.from(binding.root.context)
            val textView = inflater.inflate(
                R.layout.item_flexbox_text,
                binding.flexbox,
                false
            ) as TextView

            textView.apply {
                text = childTree.name
                setOnClickListener {
                    ARouter.getInstance()
                        .build(ARouterData.PATH_SYSTEM)
                        .withParcelable(Keys.BEAN, item)
                        .navigation()
                }
            }
            binding.flexbox.addView(textView)
        }

    }
}