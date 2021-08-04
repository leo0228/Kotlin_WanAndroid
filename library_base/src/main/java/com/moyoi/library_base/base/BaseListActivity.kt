package com.moyoi.library_base.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.kingja.loadsir.core.LoadSir
import com.moyoi.library_base.databinding.LayoutArticleListBinding
import com.moyoi.library_base.loadsir.EmptyCallback
import com.moyoi.library_base.loadsir.ErrorCallback
import com.moyoi.library_base.loadsir.NoLoginCallback
import com.moyoi.library_base.loadsir.TimeoutCallback
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

/**
 * @Description BaseListActivity
 * @Author Lu
 * @Date 2021/7/12 15:04
 * @Version: 1.0
 */
abstract class BaseListActivity<VB : ViewBinding, VM : BaseViewModel<List<DATA>>, A : BaseAdapter<DATA>, DATA> :
    BaseActivity<VB>() {
    /**
     * ViewModel
     */
    protected val mViewModel: VM by lazy { getViewModel() }

    /**
     * Adapter
     */
    protected val mAdapter: A by lazy { getAdapter() }

    override fun work(savedInstanceState: Bundle?) {
        val loadService = LoadSir.getDefault().register(getLoadSirLayout()) { loadData(true) }

        initView()

        val listBinding = setArticleListBinding()
        listBinding.refreshView.setRefreshHeader(ClassicsHeader(this))
        listBinding.refreshView.setRefreshFooter(ClassicsFooter(this))
        listBinding.refreshView.setOnRefreshListener {
            loadData(true)
        }
        listBinding.refreshView.setOnLoadMoreListener {
            loadData(false)
        }

        listBinding.recyclerView.adapter = mAdapter

        loadData(true)

        updateUI()

        mViewModel.statusResult.observe(this, { status ->
            when (status) {
                ViewStatus.EMPTY -> loadService.showCallback(EmptyCallback::class.java)
                ViewStatus.SHOW_CONTENT -> loadService.showSuccess()
                ViewStatus.ERROR -> loadService.showCallback(ErrorCallback::class.java)
                ViewStatus.TIMEOUT_ERROR -> loadService.showCallback(TimeoutCallback::class.java)
                ViewStatus.NO_LOGIN -> loadService.showCallback(NoLoginCallback::class.java)
            }
        })
    }

    open fun initView() {}
    open fun updateUI() {
        mViewModel.dataResult.observe(this, { result ->
            if (result != null) {
                if (mViewModel.isRefresh) {
                    mAdapter.setNewData(result)
                } else {
                    mAdapter.addData(result)
                }

                val listBinding = setArticleListBinding()
                if (listBinding.refreshView.isRefreshing) {
                    listBinding.refreshView.finishRefresh()
                }
                if (listBinding.refreshView.isLoading) {
                    listBinding.refreshView.finishLoadMore()
                }
                if (mViewModel.page >= mViewModel.pageCount) {
                    listBinding.refreshView.setNoMoreData(true)
                }
            }
        })
    }

    abstract fun getViewModel(): VM
    abstract fun getAdapter(): A
    abstract fun setArticleListBinding(): LayoutArticleListBinding
    abstract fun loadData(isRefresh: Boolean)
    abstract fun getLoadSirLayout(): View
}