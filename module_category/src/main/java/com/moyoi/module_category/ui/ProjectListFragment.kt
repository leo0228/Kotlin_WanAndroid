package com.moyoi.module_category.ui

import android.os.Bundle
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_category.viewmodel.CategoryViewModel

/**
 * @Description 项目文章列表
 * @Author Lu
 * @Date 2021/6/29 16:44
 * @Version: 1.0
 */
class ProjectListFragment : BaseListFragment<CategoryViewModel<List<ArticleBean>>, ArticleListAdapter, ArticleBean>() {

    private var cid = ""

    companion object {
        @JvmStatic
        fun newInstance(cid: String): ProjectListFragment {
            val fragment = ProjectListFragment()
            val bundle = Bundle()
            bundle.putString(Keys.CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getString(Keys.CID, "")
        }
    }

    override fun loadData(isRefresh: Boolean) = mViewModel.getProjectList(isRefresh, cid)
    override fun isStaggered(): Boolean = true
    override fun getViewModel(): CategoryViewModel<List<ArticleBean>> = CategoryViewModel()
    override fun getAdapter(): ArticleListAdapter = ArticleListAdapter()
}