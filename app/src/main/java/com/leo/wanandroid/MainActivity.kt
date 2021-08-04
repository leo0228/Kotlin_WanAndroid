package com.leo.wanandroid

import android.os.Bundle
import com.leo.wanandroid.databinding.ActivityMainBinding
import com.moyoi.library_base.base.BaseActivity
import com.moyoi.library_base.bus.SimpleLiveDataBus
import com.moyoi.library_base.utils.showToast
import com.moyoi.library_common.bean.CoinBean
import com.moyoi.library_common.bean.UserBean
import com.moyoi.library_common.constant.Constants
import com.moyoi.library_common.utils.DataHelper
import com.moyoi.module_category.ui.CategoryFragment
import com.moyoi.module_home.ui.HomeFragment
import com.moyoi.module_message.ui.MessageFragment
import com.moyoi.module_square.ui.SquareMainFragment
import com.moyoi.module_user.ui.AccountFragment

/**
 * @Description MainActivity
 * @Author Lu
 * @Date 2021/6/1 14:46
 * @Version: 1.0
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var exitTime = 0L
    private var lastIndex = 0

    private val mFragments = arrayOf(
        HomeFragment(),
        CategoryFragment(),
        MessageFragment(),
        SquareMainFragment(),
        AccountFragment()
    )


    override fun preCreate() {

    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun work(savedInstanceState: Bundle?) {
        setFragmentPosition(0)
        mViewBinding.bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> setFragmentPosition(0)
                R.id.item_category -> setFragmentPosition(1)
                R.id.item_message -> setFragmentPosition(2)
                R.id.item_square -> setFragmentPosition(3)
                R.id.item_account -> setFragmentPosition(4)
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onResume() {
        super.onResume()

        DataHelper.getUser().observe(this, { userBean ->
            if (userBean != null){
                SimpleLiveDataBus.getInstance().with<UserBean>(Constants.USER_STATUS_UPDATE)
                    .postValue(userBean)
            } else {
                SimpleLiveDataBus.getInstance().with<UserBean>(Constants.USER_STATUS_UPDATE)
                    .postValue(null)
            }
        })

        DataHelper.getCoin().observe(this, { coinBean ->
            if (coinBean != null) {
                SimpleLiveDataBus.getInstance().with<CoinBean>(Constants.USER_COIN_UPDATE)
                    .postValue(coinBean)
            } else {
                SimpleLiveDataBus.getInstance().with<CoinBean>(Constants.USER_COIN_UPDATE)
                    .postValue(null)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("POSITION", lastIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setFragmentPosition(savedInstanceState.getInt("POSITION"))
    }

    private fun setFragmentPosition(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        val currentFragment = mFragments[position]
        val lastFragment = mFragments[lastIndex]
        lastIndex = position
        ft.hide(lastFragment)
        if (!currentFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            ft.add(R.id.container, currentFragment)
        }
        ft.show(currentFragment)
        ft.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            exitTime = System.currentTimeMillis()
            "再按一次退出".showToast()
        } else {
            moveTaskToBack(true)
        }
    }
}