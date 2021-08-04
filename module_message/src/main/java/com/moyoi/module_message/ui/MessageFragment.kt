package com.moyoi.module_message.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moyoi.library_base.base.BaseFragment
import com.moyoi.library_common.databinding.LayoutTabLayoutRedBinding

/**
 * @Description MessageFragment,需要登录
 * @Author Lu
 * @Date 2021/7/6 10:32
 * @Version: 1.0
 */
class MessageFragment : BaseFragment<LayoutTabLayoutRedBinding>() {

    override fun getViewBinding(): LayoutTabLayoutRedBinding =
        LayoutTabLayoutRedBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.viewPager.offscreenPageLimit = 1
        mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = 2

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> ReadOrUnMessageFragment.newInstance(0)//未读
                    else -> ReadOrUnMessageFragment.newInstance(1)//已读
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> "新消息"
                    else -> "历史消息"
                }
            }
        }
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }

}