package com.moyoi.library_base.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.leaf.library.StatusBarUtil
import com.moyoi.library_base.R
import com.moyoi.library_base.utils.ActivityStackManager
import com.moyoi.library_base.data.SystemDataHelper
import com.moyoi.library_base.utils.showDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Description [ActivityCompat] 基类，封装在 [MainScope] 中
 * @Author Lu
 * @Date 2021/7/2 14:50
 * @Version: 1.0
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), CoroutineScope by MainScope() {

    /**
     * ViewBinding
     */
    protected val mViewBinding: VB by lazy { getViewBinding() }

    /**
     * 是否显示toolbar
     */
    private var isShowToolbar: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        showDebug(msg = "activity create start")
        window.setBackgroundDrawableResource(R.color.background)
        initUiMode()
        preCreate()
        super.onCreate(savedInstanceState)
        ActivityStackManager.addActivity(this)
        setContentView(mViewBinding.root)
        work(savedInstanceState)
        setToolbar()
        showDebug(msg = "activity create end")
    }

    abstract fun preCreate()
    abstract fun getViewBinding(): VB
    abstract fun work(savedInstanceState: Bundle?)

    fun initShowToolbar(isShow: Boolean) {
        this.isShowToolbar = isShow
    }

    private fun setToolbar(){
        if (isShowToolbar){
            supportActionBar?.show()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.hide()
        }
    }

    /**
     * 获取VM，只能获取不带参数的VM
     */
    fun <T : ViewModel> getViewModel(clazz: Class<T>): T = ViewModelProviders.of(this).get(clazz)

    override fun onDestroy() {
        showDebug(msg = "activity onDestroy")
        super.onDestroy()
        // 取消MainScope下的所有的协程
        cancel()
        ActivityStackManager.removeActivity(this)
    }

    /**
     * 设置夜间模式方法
     */
    private fun initUiMode() {
        SystemDataHelper.getUIMode().observe(this, {
            val name = this.componentName.className
            if (name == "com.leo.wanandroid.MainActivity") {
                StatusBarUtil.setTransparentForWindow(this)
            } else {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.theme))
            }
            when (it) {
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}