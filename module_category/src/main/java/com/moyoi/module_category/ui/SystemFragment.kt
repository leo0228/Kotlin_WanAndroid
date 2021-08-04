package com.moyoi.module_category.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.base.BaseFragment
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.library_common.widget.TopItemDecoration
import com.moyoi.module_category.adapter.SystemLeftAdapter
import com.moyoi.module_category.adapter.SystemRightAdapter
import com.moyoi.module_category.databinding.FragmentSystemBinding

/**
 * @Description 体系展示页
 * @Author Lu
 * @Date 2021/6/22 10:03
 * @Version: 1.0
 */
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    private val mLeftAdapter = SystemLeftAdapter()
    private val mRightAdapter = SystemRightAdapter()

    override fun getViewBinding(): FragmentSystemBinding =
        FragmentSystemBinding.inflate(layoutInflater)

    override fun work(view: View, savedInstanceState: Bundle?) {
        mViewBinding.recyclerViewLeft.adapter = mLeftAdapter
        mViewBinding.recyclerViewRight.adapter = mRightAdapter

        val manager = mViewBinding.recyclerViewRight.layoutManager as LinearLayoutManager

        //左边点击联动右边
        mLeftAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                mLeftAdapter.setChoose(position)
                manager.scrollToPositionWithOffset(position, 0)
            }
        })

        //右边滑动联动左边
        mViewBinding.recyclerViewRight.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemPosition = manager.findFirstVisibleItemPosition()
                if (firstItemPosition != -1) {
                    mLeftAdapter.setChoose(firstItemPosition)
                    mViewBinding.recyclerViewLeft.smoothScrollToPosition(firstItemPosition)
                }
            }
        })

        DataHelper.getTreeList().observe(viewLifecycleOwner, { treeList ->
            if (treeList != null) {
                mLeftAdapter.setNewData(treeList)
                mRightAdapter.setNewData(treeList)

                val top = TopItemDecoration(requireContext()).apply {
                    tagListener = { index ->
                        treeList[index].name
                    }
                }
                mViewBinding.recyclerViewRight.addItemDecoration(top)
            }
        })
    }

}