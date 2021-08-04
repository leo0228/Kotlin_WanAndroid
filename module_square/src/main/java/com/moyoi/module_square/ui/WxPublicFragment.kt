package com.moyoi.module_square.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moyoi.library_base.base.BaseDataFragment
import com.moyoi.library_common.bean.TreeBean
import com.moyoi.library_common.databinding.LayoutTabLayoutWhiteBinding
import com.moyoi.module_square.viewmodel.SquareViewModel

/**
 * @Description 公众号展示页
 * @Author Lu
 * @Date 2021/6/25 9:43
 * @Version: 1.0
 */
class WxPublicFragment :
    BaseDataFragment<LayoutTabLayoutWhiteBinding, SquareViewModel<List<TreeBean>>, List<TreeBean>>() {

    override fun getViewBinding(): LayoutTabLayoutWhiteBinding =
        LayoutTabLayoutWhiteBinding.inflate(layoutInflater)

    override fun getViewModel(): SquareViewModel<List<TreeBean>> = SquareViewModel()
    override fun loadData(isRefresh: Boolean) = mViewModel.getWxPublicTree()
    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.viewPager.offscreenPageLimit = 1
    }

    override fun onNetworkResponse(result: List<TreeBean>) {
        mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = result.size

            override fun getItem(position: Int): Fragment {
                return WxPublicArticleFragment.newInstance(result[position].id)
            }

            override fun getPageTitle(position: Int): CharSequence {
                return result[position].name
            }
        }
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }
}