package com.moyoi.module_user.ui

import android.view.View
import com.moyoi.library_base.base.BaseAdapter
import com.moyoi.library_base.base.BaseListActivity
import com.moyoi.library_base.databinding.LayoutArticleListBinding
import com.moyoi.library_base.utils.toast
import com.moyoi.library_common.adapter.ArticleListAdapter
import com.moyoi.library_common.bean.ArticleBean
import com.moyoi.library_common.widget.StandardDialog
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.ActivityMyShareBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description 分享文章列表
 * @Author Lu
 * @Date 2021/6/29 16:44
 * @Version: 1.0
 */
class MyShareActivity :
    BaseListActivity<ActivityMyShareBinding, UserViewModel<List<ArticleBean>>, ArticleListAdapter, ArticleBean>() {

    override fun preCreate() {
        initShowToolbar(true)
    }

    override fun getViewBinding(): ActivityMyShareBinding =
        ActivityMyShareBinding.inflate(layoutInflater)

    override fun getViewModel(): UserViewModel<List<ArticleBean>> = UserViewModel()
    override fun getAdapter(): ArticleListAdapter = ArticleListAdapter()
    override fun setArticleListBinding(): LayoutArticleListBinding = mViewBinding.articleList
    override fun loadData(isRefresh: Boolean) = mViewModel.getPrivateArticleList(isRefresh)
    override fun getLoadSirLayout(): View = mViewBinding.llShare
    override fun initView() {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.account_my_share)

        mAdapter.setOnItemLongClickListener(object :
            BaseAdapter.OnItemLongClickListener {
            override fun onItemLongClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                val articles = mAdapter.getItem(position)
                StandardDialog.newInstance().setContent("确定删除此分享吗？")
                    .setOnDialogClickListener(object : StandardDialog.OnDialogClickListener {
                        override fun onConfirm(dialog: StandardDialog) {
                            mViewModel.delUserArticle(articles.id)
                            mViewModel.delShareResult.observe(this@MyShareActivity, { result ->
                                if (result) {
                                    toast("删除分享成功")
                                    mAdapter.removeData(position)
                                }
                            })
                        }

                        override fun onCancel(dialog: StandardDialog) {

                        }
                    }).show(supportFragmentManager)
            }
        })
    }
}