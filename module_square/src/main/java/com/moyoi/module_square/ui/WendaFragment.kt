package com.moyoi.module_square.ui

import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.module_square.viewmodel.SquareViewModel

/**
 * @Description WendaFragment
 * @Author Lu
 * @Date 2021/7/2 17:26
 * @Version: 1.0
 */
class WendaFragment : BaseListFragment<SquareViewModel<List<ArticleBean>>, ArticleListAdapter, ArticleBean>() {

    override fun loadData(isRefresh: Boolean) = mViewModel.getWenDaList(isRefresh)
    override fun getViewModel(): SquareViewModel<List<ArticleBean>> = SquareViewModel()
    override fun getAdapter(): ArticleListAdapter = ArticleListAdapter()

}