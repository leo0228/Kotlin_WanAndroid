package com.moyoi.module_user.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_base.utils.toast
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.ActivityShareArticleBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description 分享文章
 * @Author Lu
 * @Date 2021/7/8 17:54
 * @Version: 1.0
 */
@Route(path = ARouterData.PATH_SHARE_ARTICLE)
class ShareArticleActivity : BaseActivity<ActivityShareArticleBinding>() {

    override fun preCreate() {
        initShowToolbar(true)
    }

    override fun getViewBinding(): ActivityShareArticleBinding =
        ActivityShareArticleBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.share_article_title)

        val viewModel = getViewModel(UserViewModel::class.java)
        mViewBinding.share.safeClick {
            val title = mViewBinding.title.text.toString()
            val link = mViewBinding.link.text.toString()
            if (checkParameter(link)) {
                viewModel.addUserArticle(title, link)
                viewModel.addShareResult.observe(this, { result ->
                    if (result) {
                        toast(R.string.share_success)
                        finish()
                    }
                })
            }
        }
    }

    private fun checkParameter(key: String): Boolean {
        if (key.isBlank()) {
            toast(R.string.share_not_empty)
            return false
        }
        return true
    }

}