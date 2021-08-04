package com.moyoi.module_category.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moyoi.library_base.base.BaseFragment
import com.moyoi.library_common.databinding.LayoutTabLayoutRedBinding

/**
 * @Description CategoryFragment
 * @Author Lu
 * @Date 2021/7/6 9:48
 * @Version: 1.0
 */
class CategoryFragment : BaseFragment<LayoutTabLayoutRedBinding>() {

    override fun getViewBinding(): LayoutTabLayoutRedBinding =
        LayoutTabLayoutRedBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.viewPager.offscreenPageLimit = 1
        mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = 4

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> SystemFragment()
                    1 -> ProjectFragment()
                    2 -> NaviFragment()
                    else -> UseWebFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> "体系"
                    1 -> "项目"
                    2 -> "导航"
                    else -> "常用网址"
                }
            }
        }
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }

}