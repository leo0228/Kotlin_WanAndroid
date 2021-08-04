package com.moyoi.module_user.ui

import android.view.View
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.base.BaseListFragment
import com.moyoi.library_base.utils.showToast
import com.moyoi.library_common.bean.WebsiteBean
import com.moyoi.library_common.viewmodel.CollectionModel
import com.moyoi.library_common.widget.EditDialog
import com.moyoi.library_common.widget.StandardDialog
import com.moyoi.module_user.R
import com.moyoi.module_user.adapter.WebsiteAdapter

/**
 * @Description MyCollectWebsiteFragment
 * @Author Lu
 * @Date 2021/7/27 18:20
 * @Version: 1.0
 */
class MyCollectWebsiteFragment :
    BaseListFragment<CollectionModel<List<WebsiteBean>>, WebsiteAdapter, WebsiteBean>() {

    override fun loadData(isRefresh: Boolean) = mViewModel.getCollectWebsiteList()
    override fun getViewModel(): CollectionModel<List<WebsiteBean>> = CollectionModel()
    override fun getAdapter(): WebsiteAdapter = WebsiteAdapter()
    override fun initView() {
        mAdapter.setOnItemChildClickListener(object : BaseAdapter.OnItemChildClickListener {
            override fun onItemChildClick(
                view: View,
                holder: BaseAdapter.ViewBindHolder,
                position: Int
            ) {
                val item = mAdapter.getItem(position)
                if (view.id == R.id.website_edit) {
                    EditDialog.newInstance()
                        .setTitle("编辑收藏网址")
                        .setName(item.name)
                        .setLink(item.link)
                        .setOnDialogClickListener(object : EditDialog.OnDialogClickListener {
                            override fun onConfirm(dialog: EditDialog) {
                                val name = dialog.getName()
                                val link = dialog.getLink()
                                mViewModel.updateCollectWebsite(item.id, name, link)
                                mViewModel.updateCollectResult.observe(viewLifecycleOwner,
                                    { result ->
                                        if (result) {
                                            loadData(true)
                                            R.string.collect_update_success.showToast()
                                        }
                                    })
                            }

                            override fun onCancel(dialog: EditDialog) {

                            }
                        }).show(childFragmentManager)
                } else if (view.id == R.id.website_delete) {
                    StandardDialog.newInstance().setContent("确定删除吗？")
                        .setOnDialogClickListener(object : StandardDialog.OnDialogClickListener {
                            override fun onConfirm(dialog: StandardDialog) {
                                mViewModel.removeCollectWebsite(item.id)
                                mViewModel.removeCollectResult.observe(viewLifecycleOwner,
                                    { result ->
                                        if (result) {
                                            mAdapter.removeData(position)
                                            R.string.collect_delete_success.showToast()
                                        }
                                    })
                            }

                            override fun onCancel(dialog: StandardDialog) {

                            }
                        }).show(childFragmentManager)
                }
            }
        })
    }
}