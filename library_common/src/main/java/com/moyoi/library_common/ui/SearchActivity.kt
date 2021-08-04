package com.moyoi.library_common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.toast
import com.moyoi.library_common.R
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_common.adapter.HistoryAdapter
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.ActivitySearchBinding
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.library_common.viewmodel.SearchViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

@Route(path = ARouterData.PATH_SEARCH)
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private var type = "key"
    private var key = ""

    private val mArticleListAdapter = ArticleListAdapter()
    private val mHistoryAdapter = HistoryAdapter()

    override fun preCreate() {
        initShowToolbar(true)

        intent.extras?.let {
            type = it.getString(Keys.TYPE, "")
            key = it.getString(Keys.KEY, "")
        }
    }

    override fun getViewBinding(): ActivitySearchBinding =
        ActivitySearchBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)

        intent.extras?.let {
            searchAndUpdateUI(key)
        }

        val viewModel = getViewModel(SearchViewModel::class.java)
        mViewBinding.recyclerView.adapter = mArticleListAdapter
        mViewBinding.refreshView.setRefreshHeader(ClassicsHeader(this))
        mViewBinding.refreshView.setRefreshFooter(ClassicsFooter(this))
        mViewBinding.refreshView.setOnRefreshListener {
            val key = mViewBinding.searchEdit.text.toString()
            if (type == "key") {
                viewModel.searchArticleListByK(true, key)
            }
            if (type == "author") {
                viewModel.searchArticleListByAuthor(true, key)
            }
        }
        mViewBinding.refreshView.setOnLoadMoreListener {
            val key = mViewBinding.searchEdit.text.toString()
            if (type == "key") {
                viewModel.searchArticleListByK(false, key)
            }
            if (type == "author") {
                viewModel.searchArticleListByAuthor(false, key)
            }
        }

        mViewBinding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAndUpdateUI(mViewBinding.searchEdit.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
        mViewBinding.searchClear.setOnClickListener {
            mViewBinding.llSearch.visibility = View.VISIBLE
            mViewBinding.refreshView.visibility = View.GONE
            mViewBinding.searchEdit.setText("")
            type = "key"
            initHistory()
        }

        mHistoryAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                val key = mHistoryAdapter.getItem(position)
                searchAndUpdateUI(key)
            }
        })
        mHistoryAdapter.setOnItemChildClickListener(object : BaseAdapter.OnItemChildClickListener {
            override fun onItemChildClick(
                view: View,
                holder: BaseAdapter.ViewBindHolder,
                position: Int
            ) {
                if (view.id == R.id.history_delete) {
                    mHistoryAdapter.removeData(position)
                    val data = mHistoryAdapter.getData()
                    mViewBinding.searchHistory.visibility =
                        if (data.isNotEmpty()) View.VISIBLE else View.GONE
                    DataHelper.setHistorySearch(data)
                }
            }
        })
        mViewBinding.historyList.layoutManager = GridLayoutManager(this, 3)
        mViewBinding.historyList.adapter = mHistoryAdapter

        mViewBinding.deleteAll.setOnClickListener {
            mHistoryAdapter.clearData()
            mViewBinding.searchHistory.visibility = View.GONE
            DataHelper.setHistorySearch(ArrayList())
        }

        initHotkey()
        initHistory()
        viewModel.dataResult.observe(this, { result ->
            if (result != null) {
                if (viewModel.isRefresh) {
                    mArticleListAdapter.setNewData(result)
                } else {
                    mArticleListAdapter.addData(result)
                }
                if (mViewBinding.refreshView.isRefreshing) {
                    mViewBinding.refreshView.finishRefresh()
                }
                if (mViewBinding.refreshView.isLoading) {
                    mViewBinding.refreshView.finishLoadMore()
                }
                if (viewModel.page == viewModel.pageCount) {
                    mViewBinding.refreshView.setNoMoreData(true)
                }
            }
        })
    }

    private fun initHotkey() {
        DataHelper.getHotkey().observe(this, { result ->
            mViewBinding.hotKey.visibility = if (result.isNotEmpty()) View.VISIBLE else View.GONE
            mViewBinding.searchFb.removeAllViews()
            result.forEach { hot ->
                val inflater = LayoutInflater.from(mViewBinding.searchFb.context)
                val textView = inflater.inflate(
                    R.layout.item_flexbox_text,
                    mViewBinding.searchFb,
                    false
                ) as TextView

                val hotName = hot.name
                textView.apply {
                    text = hotName
                    setOnClickListener {
                        searchAndUpdateUI(hotName)
                    }
                }
                mViewBinding.searchFb.addView(textView)
            }
        })
    }

    private fun initHistory() {
        DataHelper.getHistorySearch().observe(this, { result ->
            mViewBinding.searchHistory.visibility =
                if (result.isNotEmpty()) View.VISIBLE else View.GONE
            mHistoryAdapter.setNewData(result)
        })
    }

    private fun searchAndUpdateUI(key: String) {
        initHistory()
        if (checkParameter(key)) {
            mViewBinding.searchEdit.setText(key)
            mViewBinding.llSearch.visibility = View.GONE
            mViewBinding.refreshView.visibility = View.VISIBLE
            mViewBinding.refreshView.autoRefresh()

            //添加到历史搜索
            val list = mHistoryAdapter.getData()
            if (list.contains(key)) {
                list.remove(key)
            }
            list.add(0, key)
            DataHelper.setHistorySearch(list)
        }
    }

    private fun checkParameter(key: String): Boolean {
        if (key.isBlank()) {
            toast(R.string.search_not_empty)
            return false
        }
        return true
    }

    override fun onBackPressed() {
        if (mViewBinding.refreshView.visibility == View.VISIBLE) {
            mViewBinding.refreshView.visibility = View.GONE
            mViewBinding.llSearch.visibility = View.VISIBLE
            mViewBinding.searchEdit.setText("")
            type = "key"
            initHistory()
        } else {
            finish()
        }
    }
}