package com.moyoi.module_category.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.base.BaseDataFragment
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_category.R
import com.moyoi.module_category.adapter.NaviLeftAdapter
import com.moyoi.module_category.bean.NaviBean
import com.moyoi.module_category.databinding.FragmentNaviBinding
import com.moyoi.module_category.viewmodel.CategoryViewModel

/**
 * @Description NaviFragment
 * @Author Lu
 * @Date 2021/6/22 10:38
 * @Version: 1.0
 */
class NaviFragment :
    BaseDataFragment<FragmentNaviBinding, CategoryViewModel<List<NaviBean>>, List<NaviBean>>() {

    private val mLeftAdapter = NaviLeftAdapter()

    override fun getViewBinding(): FragmentNaviBinding = FragmentNaviBinding.inflate(layoutInflater)
    override fun getViewModel(): CategoryViewModel<List<NaviBean>> = CategoryViewModel()
    override fun loadData(isRefresh: Boolean) = mViewModel.getNaviList()
    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.recyclerViewLeft.adapter = mLeftAdapter

        //左边点击联动右边
        mLeftAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                mLeftAdapter.setChoose(position)
                val articles = mLeftAdapter.getItem(position).articles
                fillFlexboxLayout(articles)
            }
        })
    }

    override fun onNetworkResponse(result: List<NaviBean>) {
        mLeftAdapter.setNewData(result)
        fillFlexboxLayout(result[0].articles)
    }

    private fun fillFlexboxLayout(data: List<ArticleBean>? = null) {
        mHandler.post {
            mViewBinding.naviFb.removeAllViews()
            data?.forEach { article ->
                val inflater = LayoutInflater.from(mViewBinding.naviFb.context)
                val textView = inflater.inflate(
                    R.layout.item_flexbox_text,
                    mViewBinding.naviFb,
                    false
                ) as TextView

                textView.text = article.title
                textView.setOnClickListener {
                    ARouter.getInstance()
                        .build(ARouterData.PATH_WEBVIEW)
                        .withString(Keys.ID, article.id)
                        .withString(Keys.TITLE, article.title)
                        .withString(Keys.URL, article.link)
                        .withBoolean(Keys.COLLECT, article.collect ?: false)
                        .navigation()
                }
                mViewBinding.naviFb.addView(textView)
            }
        }
    }
}