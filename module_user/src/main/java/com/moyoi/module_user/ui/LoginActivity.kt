package com.moyoi.module_user.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.data.ARouterData
import com.moyoi.library_base.utils.toast
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.module_user.R
import com.moyoi.module_user.databinding.ActivityLoginBinding
import com.moyoi.module_user.viewmodel.UserViewModel

/**
 * @Description LoginActivity
 * @Author Lu
 * @Date 2021/6/17 20:59
 * @Version: 1.0
 */
@Route(path = ARouterData.PATH_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun preCreate() {
        initShowToolbar(false)
    }

    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        val viewModel = getViewModel(UserViewModel::class.java)
        viewModel.userDataResult.observe(this, { result ->
            mViewBinding.loading.visibility = View.GONE
            if (result != null) {
                viewModel.getUserCoin()
                toast(R.string.login_success)
                DataHelper.setUser(result)
                finish()
            }
        })

        mViewBinding.login.setOnClickListener {
            if (checkContent()) {
                mViewBinding.loading.visibility = View.VISIBLE
                viewModel.login(
                    mViewBinding.username.text.toString(),
                    mViewBinding.password.text.toString()
                )
            }
        }

        mViewBinding.register.setOnClickListener {
            if (checkContent()) {
                mViewBinding.loading.visibility = View.VISIBLE
                viewModel.register(
                    mViewBinding.username.text.toString(),
                    mViewBinding.password.text.toString()
                )
            }
        }
    }

    private fun checkContent(): Boolean {
        // error hint
        mViewBinding.username.error = null
        mViewBinding.password.error = null
        // cancel
        var cancel = false
        // attempt to view
        var focusView: View? = null
        // if register, check password confirm
        val pwdText = mViewBinding.password.text.toString()
        val usernameText = mViewBinding.username.text.toString()

        // check password
        if (TextUtils.isEmpty(pwdText)) {
            mViewBinding.password.error = getString(R.string.password_not_empty)
            focusView = mViewBinding.password
            cancel = true
        } else if (mViewBinding.password.text.length < 6) {
            mViewBinding.password.error = getString(R.string.password_length_short)
            focusView = mViewBinding.password
            cancel = true
        }

        // check username
        if (TextUtils.isEmpty(usernameText)) {
            mViewBinding.username.error = getString(R.string.username_not_empty)
            focusView = mViewBinding.username
            cancel = true
        }

        // requestFocus
        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus()
            }
            return false
        } else {
            return true
        }
    }

}