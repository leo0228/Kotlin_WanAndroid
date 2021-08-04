package com.moyoi.library_base.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*
import kotlin.collections.HashMap

object ActivityResultHelper {

    /**
     * 忽略电池优化,保持后台常驻
     */
    @SuppressLint("BatteryLife")
    fun FragmentActivity.requestIgnoreBatteryOptimizations() {
        //申请加入白名单
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            //判断应用是否在白名单中
            if (powerManager.isIgnoringBatteryOptimizations(packageName)) {
                return
            }
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:${packageName}")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 申请日历权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestCalendarPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请相机权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestCameraPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请联系人权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestContactsPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请位置权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestLocationPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请电话权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestPhonePermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请录音权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestRecordAudioPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请传感器权限
     *
     * @param callback 回调
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    fun FragmentActivity.requestSensorsPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.BODY_SENSORS
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请短信权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestSMSPermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
        )
        requestPermissions(permissions, callback)
    }

    /**
     * 申请存储空间权限
     *
     * @param callback 回调
     */
    fun FragmentActivity.requestStoragePermissions(callback: PermissionsCallback? = null) {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, callback)
    }

    fun FragmentActivity.startForResult(intent: Intent, callback: ActivityCallback) {
        getResultFragment().startForResult(intent, callback)
    }

    private fun FragmentActivity.requestPermissions(
        permissions: Array<String>,
        callback: PermissionsCallback?
    ) {
        getResultFragment().requestForPermissions(permissions, callback)
    }

    private fun FragmentActivity.getResultFragment(): ResultFragment {
        var fragment =
            supportFragmentManager.findFragmentByTag(ResultFragment::class.java.simpleName)
        if (fragment == null) {
            fragment = ResultFragment.newInstance()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(fragment, ResultFragment::class.java.simpleName)
            fragmentTransaction.commitAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()
        }
        return fragment as ResultFragment
    }

}

interface ActivityCallback {
    fun onActivityResult(resultCode: Int, data: Intent?)
}

interface PermissionsCallback {
    fun allow()
    fun deny()
//    fun denyAndNotAskAgain()
}

class ResultFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): ResultFragment {
            return ResultFragment()
        }
    }

    private val activityCallbacks: MutableMap<Int, ActivityCallback?> = HashMap()
    private val permissionsCallbacks: MutableMap<Int, PermissionsCallback?> = HashMap()

    private lateinit var fragmentActivity: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActivity = activity as FragmentActivity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callback: ActivityCallback? = activityCallbacks[requestCode]
        callback?.onActivityResult(resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val callback: PermissionsCallback? = permissionsCallbacks[requestCode]
        val length: Int = grantResults.size
        for (i in 0 until length) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        fragmentActivity,
//                        permissions[i]
//                    )
//                ) {
//                    callback?.denyAndNotAskAgain()
//                } else {
//                    callback?.deny()
//                }
                callback?.deny()
                return
            }
        }
        if (length > 0) {
            callback?.allow()
        }
    }

    fun startForResult(intent: Intent, callback: ActivityCallback?) {
        val requestCode: Int = Random().nextInt(0x0000FFFF)
        activityCallbacks[requestCode] = callback
        startActivityForResult(intent, requestCode)
    }

    /**
     * 动态权限申请方法
     */
    fun requestForPermissions(permissions: Array<String>, callback: PermissionsCallback?) {
        for (permission in permissions) {
            val permissionResult = ActivityCompat.checkSelfPermission(fragmentActivity, permission)
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                val requestCode: Int = Random().nextInt(0x0000FFFF)
                permissionsCallbacks[requestCode] = callback
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, requestCode)
                }
                return
            }
        }
        callback?.allow()
    }

}