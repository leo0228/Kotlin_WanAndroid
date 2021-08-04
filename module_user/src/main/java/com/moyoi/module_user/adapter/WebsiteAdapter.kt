package com.moyoi.module_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_common.bean.CollectDataBus
import com.moyoi.library_common.bean.WebsiteBean
import com.moyoi.library_common.constant.Constants
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.ItemWebsiteBinding

/**
 * @Description WanAndroid
 * @Author Lu
 * @Date 2021/7/27 18:20
 * @Version: 1.0
 */
class WebsiteAdapter : BaseAdapter<WebsiteBean>() {

    init {
        addOnClickListener(R.id.website_edit)
        addOnClickListener(R.id.website_delete)
    }

    override fun onCreateViewBinding(viewType: Int): (LayoutInflater, ViewGroup, Boolean) -> ViewBinding {
        return ItemWebsiteBinding::inflate
    }

    override fun onItemView(holder: ViewBindHolder, position: Int, item: WebsiteBean) {
        val binding = holder.binding as ItemWebsiteBinding
        val context = binding.root.context
        binding.websiteName.text = item.name
        binding.websiteLink.text = item.link

        binding.root.safeClick {
            ARouter.getInstance()
                .build(ARouterData.PATH_WEBVIEW)
                .withBoolean(Keys.WEBSITE, true)
                .withString(Keys.ID, item.id)
                .withString(Keys.TITLE, item.name)
                .withString(Keys.URL, item.link)
                .withBoolean(Keys.COLLECT, true)
                .navigation()

            SimpleLiveDataBus.getInstance().with<CollectDataBus>(Constants.USER_COLLECT_UPDATE)
                .observe(context as FragmentActivity, {
                    if (it.id == item.id) {
                        if (!it.collect) {
                            removeData(position)
                        }
                    }
                })
        }
    }
}