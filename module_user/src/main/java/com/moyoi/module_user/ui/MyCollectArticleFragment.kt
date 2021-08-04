package com.moyoi.module_user.ui

import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.viewmodel.CollectionModel

/**
 * @Description MyCollectArticleFragment
 * @Author Lu
 * @Date 2021/7/27 18:15
 * @Version: 1.0
 */
class MyCollectArticleFragment :
    BaseListFragment<CollectionModel<List<ArticleBean>>, ArticleListAdapter, ArticleBean>() {

    override fun loadData(isRefresh: Boolean) = mViewModel.getCollectArticleList(isRefresh)
    override fun getViewModel(): CollectionModel<List<ArticleBean>> = CollectionModel()
    override fun getAdapter(): ArticleListAdapter = ArticleListAdapter()
}