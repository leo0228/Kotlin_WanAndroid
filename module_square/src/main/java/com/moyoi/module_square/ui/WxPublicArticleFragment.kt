package com.moyoi.module_square.ui

import android.os.Bundle
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_square.viewmodel.SquareViewModel

/**
 * @Description 公众号文章列表
 * @Author Lu
 * @Date 2021/6/29 16:44
 * @Version: 1.0
 */
class WxPublicArticleFragment :
    BaseListFragment<SquareViewModel<List<ArticleBean>>, ArticleListAdapter, ArticleBean>() {
    private var id = ""

    companion object {
        @JvmStatic
        fun newInstance(id: String): WxPublicArticleFragment {
            val fragment = WxPublicArticleFragment()
            val bundle = Bundle()
            bundle.putString(Keys.ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(Keys.ID, "")
        }
    }

    override fun loadData(isRefresh: Boolean) = mViewModel.getWxArticleList(true, id)
    override fun getViewModel(): SquareViewModel<List<ArticleBean>> = SquareViewModel()
    override fun getAdapter(): ArticleListAdapter = ArticleListAdapter()
}