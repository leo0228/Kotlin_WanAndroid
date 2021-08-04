package com.moyoi.library_common.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.R
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.ActivityUserShareBinding
import com.moyoi.library_common.viewmodel.UserShareViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlin.math.abs

/**
 * @Description 分享人对应列表数据页面
 * @Author Lu
 * @Date 2021/7/8 11:19
 * @Version: 1.0
 */

@Route(path = ARouterData.PATH_USER_SHARE)
class UserShareActivity : BaseActivity<ActivityUserShareBinding>() {

    private var id: String = ""
    private var title: String = ""

    private val mArticleListAdapter = ArticleListAdapter()

    override fun preCreate() {
        initShowToolbar(true)

        intent.extras?.let {
            title = it.getString(Keys.TITLE, "")
            id = it.getString(Keys.ID, "")
        }
    }

    override fun getViewBinding(): ActivityUserShareBinding =
        ActivityUserShareBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = ""

        val viewModel = getViewModel(UserShareViewModel::class.java)
        mViewBinding.recyclerView.adapter = mArticleListAdapter
        mViewBinding.refreshView.setRefreshHeader(ClassicsHeader(this))
        mViewBinding.refreshView.setRefreshFooter(ClassicsFooter(this))
        mViewBinding.refreshView.autoRefresh()
        mViewBinding.refreshView.setOnRefreshListener {
            viewModel.getUserShareArticleList(true, id)
        }
        mViewBinding.refreshView.setOnLoadMoreListener {
            viewModel.getUserShareArticleList(false, id)
        }

        mViewBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange //所有子级View的滚动范围

            if (verticalOffset == 0 && totalScrollRange == 0) return@OnOffsetChangedListener

            if (verticalOffset == 0) {//完全展开
                mViewBinding.collapsingToolbar.title = ""
            } else if (abs(verticalOffset) >= totalScrollRange && totalScrollRange != 0) {//折叠
                mViewBinding.collapsingToolbar.title = title
            } else {//中间
                mViewBinding.collapsingToolbar.title = ""
            }
        })

        mViewBinding.username.text = getString(R.string.username, title)
        viewModel.dataResult.observe(this, { userShare ->
            userShare.coinInfo.apply {
                mViewBinding.coinCount.text = getString(R.string.coin, this.coinCount)
                mViewBinding.coinRank.text = getString(R.string.rank, this.rank)
            }

            userShare.shareArticles.datas?.let { articleList ->
                if (viewModel.isRefresh) {
                    mArticleListAdapter.setNewData(articleList)
                } else {
                    mArticleListAdapter.addData(articleList)
                }
                if (mViewBinding.refreshView.isRefreshing) {
                    mViewBinding.refreshView.finishRefresh()
                }
                if (mViewBinding.refreshView.isLoading) {
                    mViewBinding.refreshView.finishLoadMore()
                }
                if (viewModel.page >= viewModel.pageCount) {
                    mViewBinding.refreshView.setNoMoreData(true)
                }
            }
        })
    }
}

