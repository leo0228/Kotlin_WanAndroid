package com.moyoi.module_user.ui

import android.view.View
import com.moyoi.library_base.base.BaseListActivity
import com.moyoi.library_base.databinding.LayoutArticleListBinding
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.module_user.R
import com.moyoi.module_user.adapter.RankAdapter
import com.moyoi.module_user.databinding.ActivityRankBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description RankActivity
 * @Author Lu
 * @Date 2021/6/24 18:40
 * @Version: 1.0
 */
class RankActivity :
    BaseListActivity<ActivityRankBinding, UserViewModel<List<CoinBean>>, RankAdapter, CoinBean>() {

    override fun preCreate() {
        initShowToolbar(true)
    }

    override fun getViewBinding(): ActivityRankBinding = ActivityRankBinding.inflate(layoutInflater)
    override fun getViewModel(): UserViewModel<List<CoinBean>> = UserViewModel()
    override fun getAdapter(): RankAdapter = RankAdapter()
    override fun setArticleListBinding(): LayoutArticleListBinding = mViewBinding.articleList
    override fun loadData(isRefresh: Boolean) = mViewModel.getCoinRank(isRefresh)
    override fun getLoadSirLayout(): View = mViewBinding.llRank
    override fun initView() {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.account_coin_rank)
    }

    override fun updateUI() {
        mViewModel.dataResult.observe(this, { result ->
            if (result != null) {
                if (mViewModel.isRefresh) {
                    mViewBinding.rankUsername1.text = result[0].username
                    mViewBinding.rankCoinCount1.text = result[0].coinCount
                    mViewBinding.rankUsername2.text = result[1].username
                    mViewBinding.rankCoinCount2.text = result[1].coinCount
                    mViewBinding.rankUsername3.text = result[2].username
                    mViewBinding.rankCoinCount3.text = result[2].coinCount
                    mAdapter.setNewData(result.subList(3, result.size))
                } else {
                    mAdapter.addData(result.subList(3, result.size))
                }

                if (setArticleListBinding().refreshView.isRefreshing) {
                    setArticleListBinding().refreshView.finishRefresh()
                }
                if (setArticleListBinding().refreshView.isLoading) {
                    setArticleListBinding().refreshView.finishLoadMore()
                }
                if (mViewModel.page >= mViewModel.pageCount) {
                    setArticleListBinding().refreshView.setNoMoreData(true)
                }
            }
        })
    }
}