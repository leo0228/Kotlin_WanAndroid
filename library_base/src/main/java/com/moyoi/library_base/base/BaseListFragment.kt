package com.moyoi.library_base.base

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moyoi.library_base.databinding.LayoutArticleListBinding
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

/**
 * @Description 文章列表Fragment基类
 * @Author Lu
 * @Date 2021/7/2 15:53
 * @Version: 1.0
 */
abstract class BaseListFragment<VM : BaseViewModel<List<DATA>>, A : BaseAdapter<DATA>, DATA> :
    BaseDataFragment<LayoutArticleListBinding, VM, List<DATA>>() {

    /**
     * Adapter
     */
    protected val mAdapter: A by lazy { getAdapter() }

    override fun getViewBinding(): LayoutArticleListBinding =
        LayoutArticleListBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.refreshView.setRefreshHeader(ClassicsHeader(context))
        mViewBinding.refreshView.setRefreshFooter(ClassicsFooter(context))
        mViewBinding.refreshView.setOnRefreshListener {
            loadData(true)
        }
        mViewBinding.refreshView.setOnLoadMoreListener {
            loadData(false)
        }

        mViewBinding.recyclerView.adapter = mAdapter

        if (isStaggered()) {
            mViewBinding.recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            mViewBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        initView()
    }

    override fun onNetworkResponse(result: List<DATA>) {
        if (mViewModel.isRefresh) {
            mAdapter.setNewData(result)
        } else {
            mAdapter.addData(result)
        }

        if (mViewBinding.refreshView.isRefreshing) {
            mViewBinding.refreshView.finishRefresh()
        }
        if (mViewBinding.refreshView.isLoading) {
            mViewBinding.refreshView.finishLoadMore()
        }
        if (mViewModel.page >= mViewModel.pageCount) {
            mViewBinding.refreshView.setNoMoreData(true)
        }
    }

    abstract fun getAdapter(): A
    open fun initView() {}
    open fun isStaggered(): Boolean = false
}