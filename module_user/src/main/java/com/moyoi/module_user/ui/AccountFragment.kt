package com.moyoi.module_user.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseFragment
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.numberAnimator
import com.moyoi.library_base.utils.start
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.library_common.bean.UserBean
import com.moyoi.library_common.constant.Constants
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.FragmentAccountBinding
import com.moyoi.module_user.viewmodel.UserViewModel


/**
 * @Description AccountFragment,需要登录
 * @Author Lu
 * @Date 2021/6/29 16:44
 * @Version: 1.0
 */
class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    override fun getViewBinding(): FragmentAccountBinding =
        FragmentAccountBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.setting.setOnClickListener {
            start(SettingActivity::class.java)
        }
        mViewBinding.llMyCoin.setOnClickListener {
            start(MyCoinActivity::class.java)
        }
        mViewBinding.llMyCollect.setOnClickListener {
            start(MyCollectActivity::class.java)
        }
        mViewBinding.llMyShare.setOnClickListener {
            start(MyShareActivity::class.java)
        }
        mViewBinding.llRank.setOnClickListener {
            start(RankActivity::class.java)
        }
        mViewBinding.llShareArticle.setOnClickListener {
            start(ShareArticleActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        SimpleLiveDataBus.getInstance().with<UserBean>(Constants.USER_STATUS_UPDATE)
            .observe(this, { userBean ->
                if (userBean != null) {
                    mViewBinding.username.text = userBean.username
                } else {
                    mViewBinding.username.setText(R.string.login_please_login)
                    mViewBinding.username.setOnClickListener {
                        ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                    }
                }
            })

        SimpleLiveDataBus.getInstance().with<CoinBean>(Constants.USER_COIN_UPDATE)
            .observe(this, { coinBean ->
                if (coinBean != null) {
                    mViewBinding.coinCount.text = coinBean.coinCount
                } else {
                    mViewBinding.coinCount.text = "0"
                    mViewBinding.coinCount.setOnClickListener {
                        ARouter.getInstance().build(ARouterData.PATH_LOGIN).navigation()
                    }
                }
            })
    }

}