package com.moyoi.module_square.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moyoi.library_base.base.BaseFragment
import com.moyoi.library_common.databinding.LayoutTabLayoutRedBinding

/**
 * @Description SquareMainFragment
 * @Author Lu
 * @Date 2021/7/6 10:38
 * @Version: 1.0
 */
class SquareMainFragment : BaseFragment<LayoutTabLayoutRedBinding>() {

    override fun getViewBinding(): LayoutTabLayoutRedBinding =
        LayoutTabLayoutRedBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.viewPager.offscreenPageLimit = 1
        mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = 3

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> SquareFragment()
                    1 -> WendaFragment()
                    else -> WxPublicFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> "广场"
                    1 -> "问答"
                    else -> "公众号"
                }
            }
        }
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }

}