package com.moyoi.library_base.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Description [Fragment] 基类，封装在 [MainScope] 中
 * @Author Lu
 * @Date 2021/7/2 9:32
 * @Version: 1.0
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), CoroutineScope by MainScope(){

    protected val mHandler: Handler = Handler(Looper.getMainLooper())

    /**
     * ViewBinding
     */
    protected val mViewBinding: VB by lazy { getViewBinding() }

    /**
     * RootView
     */
    protected var mRootView: View? = null

    /**
     * 判断是否初始化View
     */
    protected var isViewInit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = mViewBinding.root
        }

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isViewInit) {
            super.onViewCreated(view, savedInstanceState)
            work(view, savedInstanceState)
            isViewInit = true
        }
    }

    abstract fun getViewBinding(): VB
    abstract fun work(view: View, savedInstanceState: Bundle?)

    /**
     * 获取VM，只能获取不带参数的VM
     */
    fun <T : ViewModel> getViewModel(clazz: Class<T>): T = ViewModelProviders.of(this).get(clazz)

    override fun onDestroy() {
        super.onDestroy()
        // 取消MainScope下的所有的协程
        cancel()
    }
}