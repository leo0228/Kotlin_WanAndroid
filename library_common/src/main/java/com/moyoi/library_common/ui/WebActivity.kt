package com.moyoi.library_common.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.toast
import com.moyoi.library_common.R
import com.moyoi.library_common.bean.CollectDataBus
import com.moyoi.library_common.constant.Constants
import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.databinding.ActivityWebBinding
import com.moyoi.library_common.utils.getAgentWeb
import com.moyoi.library_common.viewmodel.CollectionModel

/**
 * @Description 详情页
 * @Author Lu
 * @Date 2021/6/18 15:51
 * @Version: 1.0
 */
@Route(path = ARouterData.PATH_WEBVIEW)
class WebActivity : BaseActivity<ActivityWebBinding>() {

    private lateinit var agentWeb: AgentWeb
    private var webTitle: String = ""
    private var webUrl: String = ""
    private var id: String = ""
    private var originId: String = ""
    private var collect: Boolean = false
    private var isWebsite: Boolean = false

    override fun preCreate() {
        initShowToolbar(true)

        intent.extras?.let {
            id = it.getString(Keys.ID, "")
            originId = it.getString(Keys.ORIGINID, "")
            webUrl = it.getString(Keys.URL, "")
            webTitle = it.getString(Keys.TITLE, "")
            collect = it.getBoolean(Keys.COLLECT, false)
            isWebsite = it.getBoolean(Keys.WEBSITE, false)
        }
    }

    override fun getViewBinding(): ActivityWebBinding = ActivityWebBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)

        agentWeb = webUrl.getAgentWeb(
            this,
            mViewBinding.webContent,
            LinearLayout.LayoutParams(-1, -1),
            receivedTitleCallback
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (id != "") {
            menuInflater.inflate(R.menu.menu_content, menu)
            if (collect) {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_red_24
                )
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val viewModel = getViewModel(CollectionModel::class.java)
        when (item.itemId) {
            R.id.menuShare -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        getString(
                            R.string.share_article_url,
                            getString(R.string.app_name),
                            webTitle,
                            webUrl
                        )
                    )
                    type = Constants.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.share_title)))
                }
                return true
            }
            R.id.menuLike -> {
                if (collect) {
                    if (isWebsite) {
                        viewModel.removeCollectWebsite(id)
                    } else {
                        if (originId != "-1") {
                            viewModel.removeCollectPage(id, originId)
                        } else {
                            viewModel.removeCollectArticle(id)
                        }
                    }

                    viewModel.removeCollectResult.observe(this, { result ->
                        if (result) {
                            collect = false
                            toast(R.string.collect_cancel_success)
                            item.icon = ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_baseline_favorite_border_24
                            )
                        } else {
                            toast(R.string.collect_cancel_failed)
                        }
                    })
                } else {
                    if (id == "0") {
                        viewModel.addCollectOutsideArticle(
                            webTitle,
                            getString(R.string.outside_title),
                            webUrl
                        )
                    } else {
                        if (isWebsite) {
                            viewModel.addCollectWebsite(webTitle, webUrl)
                        } else {
                            viewModel.addCollectArticle(id)
                        }
                    }
                    viewModel.addCollectResult.observe(this, { result ->
                        if (result) {
                            collect = true
                            toast(R.string.collect_success)
                            item.icon = ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_baseline_favorite_red_24
                            )

                        } else {
                            toast(R.string.collect_failed)
                        }
                    })
                    return true
                }
            }
            R.id.menuBrowser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(webUrl)
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        SimpleLiveDataBus.getInstance().with<CollectDataBus>(Constants.USER_COLLECT_UPDATE)
            .postValue(CollectDataBus(id, collect))
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    private val receivedTitleCallback =
        ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
            title?.let {
                supportActionBar?.title = it
            }
        }

}