package com.moyoi.module_category.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseDataFragment
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.bean.WebsiteBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_category.R
import com.moyoi.module_category.databinding.FragmentUseWebBinding
import com.moyoi.module_category.viewmodel.CategoryViewModel

/**
 * @Description 常用网址展示页
 * @Author Lu
 * @Date 2021/6/25 11:00
 * @Version: 1.0
 */
class UseWebFragment :
    BaseDataFragment<FragmentUseWebBinding, CategoryViewModel<List<WebsiteBean>>, List<WebsiteBean>>() {

    override fun getViewBinding(): FragmentUseWebBinding =
        FragmentUseWebBinding.inflate(layoutInflater)

    override fun getViewModel(): CategoryViewModel<List<WebsiteBean>> = CategoryViewModel()
    override fun loadData(isRefresh: Boolean) = mViewModel.getWebsiteList()
    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.useFb.removeAllViews()
    }

    override fun onNetworkResponse(result: List<WebsiteBean>) {
        mHandler.post {
            result.forEach { website ->
                val inflater = LayoutInflater.from(mViewBinding.root.context)
                val textView = inflater.inflate(
                    R.layout.item_flexbox_text,
                    mViewBinding.useFb,
                    false
                ) as TextView

                textView.text = website.name
                textView.setOnClickListener {
                    ARouter.getInstance()
                        .build(ARouterData.PATH_WEBVIEW)
                        .withBoolean(Keys.WEBSITE, true)
                        .withString(Keys.ID, website.id)
                        .withString(Keys.TITLE, website.name)
                        .withString(Keys.URL, website.link)
                        .withBoolean(Keys.COLLECT, website.collect)
                        .navigation()
                }
                mViewBinding.useFb.addView(textView)
            }
        }
    }
}