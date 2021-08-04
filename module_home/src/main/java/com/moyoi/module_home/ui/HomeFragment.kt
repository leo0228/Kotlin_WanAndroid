package com.moyoi.module_home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.base.BaseDataFragment
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.MetricsUtil.dp2px
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.utils.RecyclerViewHelper
import com.moyoi.module_home.adapter.CustomBannerAdapter
import com.moyoi.module_home.adapter.HotKeyAdapter
import com.moyoi.module_home.databinding.FragmentHomeBinding
import com.moyoi.module_home.databinding.LayoutBannerBinding
import com.moyoi.module_home.databinding.LayoutTopViewBinding
import com.moyoi.module_home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.youth.banner.indicator.CircleIndicator

/**
 * @Description 首页
 * @Author Lu
 * @Date 2021/6/22 10:03
 * @Version: 1.0
 */
class HomeFragment : BaseDataFragment<FragmentHomeBinding, HomeViewModel, List<ArticleBean>>() {
    private lateinit var mRecycleHelper_1: RecyclerViewHelper
    private lateinit var mRecycleHelper_2: RecyclerViewHelper

    private val mHotKeyAdapter = HotKeyAdapter()
    private val mCustomBannerAdapter = CustomBannerAdapter()
    private val mArticleListAdapter = ArticleListAdapter()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun getViewModel(): HomeViewModel = HomeViewModel()
    
    override fun loadData(isRefresh: Boolean) {
        mViewModel.getTree()
        mViewModel.getHotKey()
        mViewModel.getBanner()
        mViewModel.getHomeArticleList(true)
    }

    override fun onNetworkResponse(result: List<ArticleBean>) {
        if (mViewModel.isRefresh) {
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
        if (mViewModel.page == mViewModel.pageCount) {
            mViewBinding.refreshView.setNoMoreData(true)
        }

        mViewModel.bannerResult.observe(viewLifecycleOwner, { bannerResult ->
            if (bannerResult != null) {
                mCustomBannerAdapter.setBannerList(bannerResult)
            }
        })

        mViewModel.topArticleListResult.observe(viewLifecycleOwner, { topArticleListResult ->
            if (topArticleListResult != null) {
                topArticleListResult.forEach {
                    it.top = true
                }
                mArticleListAdapter.addData(0, topArticleListResult)
            }
        })

        mViewModel.hotKeyResult.observe(viewLifecycleOwner, { hotKeyResult ->
            if (hotKeyResult != null) {
                mHotKeyAdapter.setNewData(hotKeyResult)
                mRecycleHelper_1.startTimerTask()
                mRecycleHelper_2.startTimerTask()
            }
        })
    }

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.recyclerView.adapter = mArticleListAdapter
        mViewBinding.refreshView.setRefreshHeader(ClassicsHeader(context))
        mViewBinding.refreshView.setRefreshFooter(ClassicsFooter(context))
        mViewBinding.refreshView.setOnRefreshListener {
            mViewModel.getBanner()
            mViewModel.getHomeArticleList(true)
        }

        mViewBinding.refreshView.setOnLoadMoreListener {
            mViewModel.getHomeArticleList(false)
        }

        mRecycleHelper_1 = RecyclerViewHelper(mViewBinding.floatSearch.hotKey)
        mHotKeyAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                val k = mHotKeyAdapter.getItem(position).name
                ARouter.getInstance()
                    .build(ARouterData.PATH_SEARCH)
                    .withString(Keys.KEY, k)
                    .withString(Keys.TYPE, "key")
                    .navigation()
            }
        })
        mViewBinding.floatSearch.hotKey.adapter = mHotKeyAdapter

        mViewBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            // 解决打开首页出现两个搜索框
            if (verticalOffset <= -dp2px(40f)) {
                mViewBinding.llFloatSearch.visibility = View.VISIBLE
            } else {
                mViewBinding.llFloatSearch.visibility = View.GONE
            }
        })


        mViewBinding.llHeader.removeAllViews()
        addTopView()
        addBannerView()
    }

    private fun addTopView() {
        val inflater = LayoutInflater.from(activity)
        val topView = LayoutTopViewBinding.inflate(inflater)
        topView.ivAdd.safeClick {
            ARouter.getInstance().build(ARouterData.PATH_SHARE_ARTICLE).navigation()
        }
        mRecycleHelper_2 = RecyclerViewHelper(topView.topSearch.hotKey)
        topView.topSearch.hotKey.adapter = mHotKeyAdapter
        mViewBinding.llHeader.addView(topView.root)
    }

    private fun addBannerView() {
        val inflater = LayoutInflater.from(activity)
        val bannerView = LayoutBannerBinding.inflate(inflater)
        bannerView.banner.addBannerLifecycleObserver(activity)
            .setAdapter(mCustomBannerAdapter)
            .setIndicator(CircleIndicator(activity))
            .start()
        mViewBinding.llHeader.addView(bannerView.root)
    }

    override fun onResume() {
        super.onResume()
        mRecycleHelper_1.startTimerTask()
        mRecycleHelper_2.startTimerTask()
    }

    override fun onPause() {
        super.onPause()
        mRecycleHelper_1.stopTimerTask()
        mRecycleHelper_2.stopTimerTask()
    }
}


