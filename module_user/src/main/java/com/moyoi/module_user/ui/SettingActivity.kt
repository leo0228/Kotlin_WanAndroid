package com.moyoi.module_user.ui

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.CacheUtil
import com.moyoi.library_base.utils.ScreenRecordHelper.startScreenRecord
import com.moyoi.library_base.utils.ScreenRecordHelper.stopScreenRecord
import com.moyoi.library_base.data.SystemDataHelper
import com.moyoi.library_base.utils.safeClick
import com.moyoi.library_base.utils.showError
import com.moyoi.library_common.bean.UserBean
import com.moyoi.library_common.constant.Constants

import com.moyoi.library_common.constant.Keys
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.library_common.widget.StandardDialog
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.ActivitySettingBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description SettingActivity
 * @Author Lu
 * @Date 2021/7/13 16:50
 * @Version: 1.0
 */
class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    private var countDownTimer: CountDownTimer? = null

    override fun preCreate() {
        initShowToolbar(true)
    }

    override fun getViewBinding(): ActivitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.title = getString(R.string.account_setting)

        SystemDataHelper.getUIMode().observe(this, { result ->
            when (result) {
                1 -> {
                    mViewBinding.darkTheme.isChecked = false
                    mViewBinding.systemTheme.isChecked = false
                }
                2 -> {
                    mViewBinding.darkTheme.isChecked = true
                    mViewBinding.systemTheme.isChecked = false
                }
                else -> {
                    mViewBinding.systemTheme.isChecked = true
                    mViewBinding.darkTheme.isChecked = false
                }
            }
        })

        SystemDataHelper.getScreenRecordStatus().observe(this, { result ->
            when (result) {
                0 -> {
                    mViewBinding.screenRecord.isChecked = false
                }
                1 -> {
                    mViewBinding.screenRecord.isChecked = true
                }
            }
        })

        mViewBinding.systemTheme.setOnCheckedChangeListener { _, isChecked ->
            SystemDataHelper.setUIMode(
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    -1
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1
                }
            )
        }

        mViewBinding.darkTheme.setOnCheckedChangeListener { _, isChecked ->
            SystemDataHelper.setUIMode(
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1
                }
            )
        }

        mViewBinding.screenRecord.setOnCheckedChangeListener { view, isChecked ->
            val status = if (isChecked) 1 else 0
            SystemDataHelper.setScreenRecordStatus(status)
            if (isChecked) {
                countDownTimer = object : CountDownTimer(5 * 1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        mViewBinding.recordTips.text = "${(millisUntilFinished / 1000) + 1}s后开始录屏"
                    }

                    override fun onFinish() {
                        mViewBinding.recordTips.text = ""
                        startScreenRecord { code, message ->
                            if (code != Activity.RESULT_OK) {
                                showError("message:${message}")
                                mViewBinding.recordTips.text = message
                                mViewBinding.screenRecord.isChecked = false
                            }
                        }
                    }
                }.start()
            } else {
                mViewBinding.recordTips.text = ""
                countDownTimer?.cancel()
                view.postDelayed({
                    stopScreenRecord()
                }, 1000)
            }
        }

        mViewBinding.privacyPolicy.safeClick {
            ARouter.getInstance().build(ARouterData.PATH_WEBVIEW)
                .withString(Keys.URL, "file:///android_asset/privacy_policy.html")
                .withString(Keys.TITLE, "隐私政策")
                .navigation()
        }

        mViewBinding.feedback.safeClick {
            ARouter.getInstance().build(ARouterData.PATH_WEBVIEW)
                .withString(Keys.URL, "https://wanandroid.com")
                .withString(Keys.TITLE, "问题反馈")
                .navigation()
        }

        mViewBinding.about.safeClick {
            ARouter.getInstance().build(ARouterData.PATH_WEBVIEW)
                .withString(Keys.URL, "https://wanandroid.com")
                .withString(Keys.TITLE, "关于")
                .navigation()
        }

        mViewBinding.cacheSize.text = CacheUtil.getTotalCacheSize(this)
        mViewBinding.clearCache.safeClick {
            StandardDialog.newInstance()
                .setContent("确定要清除缓存吗？")
                .setOnDialogClickListener(object : StandardDialog.OnDialogClickListener {
                    override fun onConfirm(dialog: StandardDialog) {
                        CacheUtil.clearAllCache(this@SettingActivity)
                        mViewBinding.cacheSize.text =
                            CacheUtil.getTotalCacheSize(this@SettingActivity)
                    }

                    override fun onCancel(dialog: StandardDialog) {
                    }
                })
                .show(supportFragmentManager)
        }

        mViewBinding.update.safeClick {
            StandardDialog.newInstance()
                .setTitle("感谢使用")
                .setContent("喜欢的话，请给颗♥哈")
                .setOnDialogClickListener(object : StandardDialog.OnDialogClickListener {
                    override fun onConfirm(dialog: StandardDialog) {
                        ARouter.getInstance().build(ARouterData.PATH_WEBVIEW)
                            .withString(Keys.URL, "https://www.bilibili.com/")
                            .withString(Keys.TITLE, "哔哩哔哩")
                            .navigation()
                    }

                    override fun onCancel(dialog: StandardDialog) {
                    }
                })
                .show(supportFragmentManager)
        }

        mViewBinding.loginLogout.safeClick {
            StandardDialog.newInstance().setContent("确定退出登录吗？")
                .setOnDialogClickListener(object : StandardDialog.OnDialogClickListener {
                    override fun onConfirm(dialog: StandardDialog) {
                        val viewModel = getViewModel(UserViewModel::class.java)
                        viewModel.logout()
                        viewModel.logoutResult.observe(this@SettingActivity, { result ->
                            if (result) {
                                DataHelper.setUser(UserBean())
                            }
                        })
                    }

                    override fun onCancel(dialog: StandardDialog) {

                    }
                }).show(supportFragmentManager)
        }
    }

    override fun onResume() {
        super.onResume()

        SimpleLiveDataBus.getInstance().with<UserBean>(Constants.USER_STATUS_UPDATE)
            .observe(this, { userBean ->
                if (userBean != null) {
                    mViewBinding.loginLogout.text = getString(R.string.logout)
                    mViewBinding.loginLogout.visibility = View.VISIBLE
                } else {
                    mViewBinding.loginLogout.visibility = View.GONE
                }
            })
    }
}