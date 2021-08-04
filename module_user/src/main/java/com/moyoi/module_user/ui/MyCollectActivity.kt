package com.moyoi.module_user.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_common.databinding.ActivityCommonArticleListBinding
import com.moyoi.module_user.R

/**
 * @Description 收藏列表
 * @Author Lu
 * @Date 2021/6/29 16:44
 * @Version: 1.0
 */

class MyCollectActivity : BaseActivity<ActivityCommonArticleListBinding>() {

    override fun preCreate() {
        initShowToolbar(true)
    }

    override fun getViewBinding(): ActivityCommonArticleListBinding = ActivityCommonArticleListBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.account_my_collect)

        mViewBinding.viewPager.offscreenPageLimit = 1
        mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = 2

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> MyCollectArticleFragment()
                    else -> MyCollectWebsiteFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> "收藏的文章"
                    else -> "收藏的网址"
                }
            }
        }
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }


}