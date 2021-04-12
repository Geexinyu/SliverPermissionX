package com.gxinyu.sliverpermission

import android.content.Context
import android.os.Build
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 申请权限的参数
 * 后续需要再添加
 */
class RequestBuilder(var context: Context) {

    companion object {
        const val NORMAL = 0x01 //一次性
        const val CHAIN = 0x02 //链状
    }

    @IntDef(NORMAL, CHAIN)
    @Retention(RetentionPolicy.SOURCE)
    annotation class MODE

    var mode = NORMAL
    var callBack: RequestCallBack? = null
    var permissions: Array<String>? = null

    /**
     * 设置模式
     *
     * @param mode
     * @return
     */
    fun mode(@MODE mode: Int): RequestBuilder {
        this.mode = mode
        return this
    }

    /**
     * 设置回调
     *
     * @param requestCallBack
     * @return
     */
    fun callback(requestCallBack: RequestCallBack?): RequestBuilder {
        callBack = requestCallBack
        return this
    }

    /**
     * 设置申请的权限
     *
     * @param permissions
     * @return
     */
    fun permissions(permissions: Array<String>): RequestBuilder {
        this.permissions = permissions
        return this
    }

    /**
     * 开始申请
     */
    fun apply() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissions?.get(0)?.let { callBack!!.onPermissionDenied(it, "Please make sure the Build.VERSION.SDK_INT is above 23") }
        } else {
            val request = Request()
            request.apply(this)
        }
    }

}