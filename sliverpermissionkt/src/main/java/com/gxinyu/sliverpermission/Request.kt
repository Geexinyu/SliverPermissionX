package com.gxinyu.sliverpermission

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class Request : RequestCallBack {

    private var mode = 0
    private var context: Context? = null
    private var callBack: RequestCallBack? = null
    private var requestPermissions: Array<String>? = null //权限列表
    lateinit var notAllowedDialog: AlertDialog

    private fun getRequestRetriever() = SliverPermission.INSTANCE.getRequestRetriever()

    /**
     * @param requestBuilder
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun apply(requestBuilder: RequestBuilder) {
        this.mode = requestBuilder.mode
        this.context = requestBuilder.context
        this.callBack = requestBuilder.callBack ?: this
        this.requestPermissions = requestBuilder.permissions
        checkVaild()
        requestPermissions()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestPermissions() {
        val permissionIndex: Int = checkDeniedPermissionIndex(context, requestPermissions)
        require(permissionIndex != -1)
        checkNotNull(context)
        when (mode) {
            RequestBuilder.NORMAL -> {
                getRequestRetriever().requestPermissions(context!!, this, requestPermissions!!)
            }
            RequestBuilder.CHAIN -> {
                getRequestRetriever().requestPermissions(context!!, this, arrayOf(requestPermissions!![permissionIndex]))
            }
        }
    }

    /**
     * 校验当前权限集合的状态
     *
     * @param permissionList
     * @return -1代表全部成功
     * 其他值则代表当前权限未申请成功的权限所在下标
     */
    private fun checkDeniedPermissionIndex(context: Context?, permissionList: Array<String>?): Int {
        for (i in permissionList!!.indices) {
            val permission = permissionList[i]
            if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return i
            }
        }
        return -1
    }


    /**
     * 校验权限名称是否正确
     */
    private fun checkVaild() {
        checkNotNull(requestPermissions)
        check(requestPermissions?.size == 0)
        requestPermissions?.forEach {
            require(ALL_PERSSIONS.contains(it)) {
                "$it permission you applied for is not in the dynamic permissions"
            }
        }
    }

    /**
     * fragment跳转到其他activity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SYSTEM) {
            requestPermissions()
        }
    }

    /**
     * 权限申请回调
     *
     * @param permissions
     * @param grantResults
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for (i in permissions.indices) {
                val grantResult = grantResults[i]
                val permissionName = permissions[i]
                if (grantResult == -1) {
                    val rationale = ActivityCompat.shouldShowRequestPermissionRationale((context as Activity?)!!, permissionName)
                    if (!rationale) {
                        showNotAllowedPermission(context, permissionName)
                        callBack!!.onPermissionDeniedNotAllowed(permissionName)
                        return
                    } else {
                        callBack!!.onPermissionDenied(permissionName, permissionName + "权限被禁止!")
                        return
                    }
                } else {
                    if (mode == RequestBuilder.CHAIN) {
                        requestPermissions()
                    }
                }
            }
        }
    }

    /**
     * 不再出现申请权限系统弹窗之后
     *
     * @param context
     * @param permissionName
     */
    private fun showNotAllowedPermission(context: Context?, permissionName: String) {
        //此时禁止弹出选择权限框，需要用户主动去开启
        notAllowedDialog = AlertDialog.Builder(context)
                .setTitle("权限提示")
                .setMessage(permissionName + "权限拒绝不再询问,需要去系统权限管理处开启!")
                .setPositiveButton("开启") { _, _ -> //开启权限
                    getRequestRetriever().openSystemSetting(context)
                }
                .setNegativeButton("取消", null).create()
        if (notAllowedDialog.isShowing) {
            notAllowedDialog.dismiss()
        }
        notAllowedDialog.show()
    }


    override fun onPermissionGranted(permissionList: Array<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionDenied(permissionName: String, message: String) {
        TODO("Not yet implemented")
    }

    override fun onPermissionDeniedNotAllowed(permissionName: String?) {
        TODO("Not yet implemented")
    }


}