package com.moyoi.module_category.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_common.bean.TreeBean
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.ActivityCommonArticleListBinding

/**
 * @Description CommonArticleListActivity
 * @Author Lu
 * @Date 2021/7/5 13:10
 * @Version: 1.0
 */
@Route(path = ARouterData.PATH_SYSTEM)
class SystemListActivity : BaseActivity<ActivityCommonArticleListBinding>() {

    private var treeBean: TreeBean? = null

    override fun preCreate() {
        initShowToolbar(true)

        intent.extras?.let {
            treeBean = it.getParcelable(Keys.BEAN)
        }
    }

    override fun getViewBinding(): ActivityCommonArticleListBinding = ActivityCommonArticleListBinding.inflate(layoutInflater)
    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)

        treeBean?.let { tree ->
            supportActionBar?.title = tree.name
            tree.children?.let { children ->
                mViewBinding.viewPager.offscreenPageLimit = 1
//                mViewBinding.viewPager.adapter = object : FragmentStateAdapter(this) {
//                    override fun getItemCount(): Int = children.size
//
//                    override fun createFragment(position: Int): Fragment {
//                        return CommonArticleListFragment.newInstance(children[position].id)
//                    }
//
//                }
//                mViewBinding.run {
//                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                        tab.text = children[position].name
//                    }.attach()
//                }
                mViewBinding.viewPager.adapter = object : FragmentStatePagerAdapter(
                    supportFragmentManager,
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ) {
                    override fun getCount(): Int = children.size

                    override fun getItem(position: Int): Fragment {
                        return SystemListFragment.newInstance(children[position].id)
                    }

                    override fun getPageTitle(position: Int): CharSequence {
                        return children[position].name
                    }
                }
                mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
            }

            mViewBinding.viewPager.currentItem = tree.childrenSelectPosition
        }
    }
}