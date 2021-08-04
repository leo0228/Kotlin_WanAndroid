package com.moyoi.module_user.ui

import android.view.View
import com.moyoi.library_base.base.BaseListActivity
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.databinding.LayoutArticleListBinding
import com.moyoi.library_base.utils.numberAnimator
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.library_common.constant.Constants
import com.moyoi.module_user.R
import com.moyoi.module_user.adapter.MyCoinAdapter
import com.moyoi.module_user.bean.MyCoinBean
import com.moyoi.module_user.databinding.ActivityMyCoinBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description MyCoinActivity
 * @Author Lu
 * @Date 2021/6/24 17:23
 * @Version: 1.0
 */
class MyCoinActivity :
    BaseListActivity<ActivityMyCoinBinding, UserViewModel<List<MyCoinBean>>, MyCoinAdapter, MyCoinBean>() {

    override fun preCreate() {
        initShowToolbar(true)
    }
    override fun getViewBinding(): ActivityMyCoinBinding =
        ActivityMyCoinBinding.inflate(layoutInflater)

    override fun getViewModel(): UserViewModel<List<MyCoinBean>> = UserViewModel()
    override fun getAdapter(): MyCoinAdapter = MyCoinAdapter()
    override fun setArticleListBinding(): LayoutArticleListBinding = mViewBinding.articleList
    override fun loadData(isRefresh: Boolean) = mViewModel.getCoinList(isRefresh)
    override fun getLoadSirLayout(): View = mViewBinding.llCoin

    override fun initView() {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.account_my_coin)
    }

    override fun onResume() {
        super.onResume()

        SimpleLiveDataBus.getInstance().with<CoinBean>(Constants.USER_COIN_UPDATE)
            .observe(this, { coinBean ->
                if (coinBean != null) {
                    numberAnimator(mViewBinding.coinCount, coinBean.coinCount)
                }
            })
    }
}