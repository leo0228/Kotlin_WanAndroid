package com.moyoi.module_message.ui

import android.os.Bundle
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_common.constant.Keys
import com.moyoi.module_message.adapter.MessageAdapter
import com.moyoi.module_message.bean.MessageBean
import com.moyoi.module_message.viewmodel.MessageViewModel

/**
 * @Description ReadOrUnMessageFragment
 * @Author Lu
 * @Date 2021/7/7 12:04
 * @Version: 1.0
 */
class ReadOrUnMessageFragment : BaseListFragment<MessageViewModel, MessageAdapter, MessageBean>() {

    private var type: Int = 0

    companion object {
        @JvmStatic
        fun newInstance(type: Int): ReadOrUnMessageFragment {
            val fragment = ReadOrUnMessageFragment()
            val bundle = Bundle()
            bundle.putInt(Keys.TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(Keys.TYPE)
        }
    }

    override fun loadData(isRefresh: Boolean) {
        if (type == 0) {
            mViewModel.getUnReadMessageList(isRefresh)
        } else {
            mViewModel.getReadMessageList(isRefresh)
        }
    }

    override fun getViewModel(): MessageViewModel = MessageViewModel()
    override fun getAdapter(): MessageAdapter = MessageAdapter()

}