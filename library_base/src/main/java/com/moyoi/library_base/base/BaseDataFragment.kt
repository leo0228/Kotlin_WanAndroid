package com.moyoi.library_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.moyoi.library_base.R
import com.moyoi.library_base.http.utils.NetWorkUtils
import com.moyoi.library_base.loadsir.*
import com.moyoi.library_base.utils.showToast

/**
 * @Description [BaseDataFragment] 基类
 * @Author Lu
 * @Date 2021/7/2 9:32
 * @Version: 1.0
 */
abstract class BaseDataFragment<VB : ViewBinding, VM : BaseViewModel<DATA>, DATA> :
    BaseFragment<VB>() {

    /**
     * ViewModel
     */
    protected val mViewModel: VM by lazy { getViewModel() }

    abstract fun getViewModel(): VM

    private lateinit var loadService: LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = mViewBinding.root
        }

        loadService = LoadSir.getDefault().register(mRootView) {loadData(true) }
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isViewInit) {
            super.onViewCreated(view, savedInstanceState)
            work(view, savedInstanceState)
            loadData(true)
            isViewInit = true
        }

        mViewModel.dataResult.observe(viewLifecycleOwner, { result ->
            if (result != null) {
                onNetworkResponse(result)
            }
        })

        mViewModel.statusResult.observe(viewLifecycleOwner, { status ->
            when (status) {
                ViewStatus.EMPTY -> loadService.showCallback(EmptyCallback::class.java)
                ViewStatus.SHOW_CONTENT -> loadService.showSuccess()
                ViewStatus.ERROR -> loadService.showCallback(ErrorCallback::class.java)
                ViewStatus.TIMEOUT_ERROR -> loadService.showCallback(TimeoutCallback::class.java)
                ViewStatus.NO_LOGIN-> loadService.showCallback(NoLoginCallback::class.java)
            }
        })
    }

    abstract fun loadData(isRefresh: Boolean)
    abstract fun onNetworkResponse(result: DATA)
}