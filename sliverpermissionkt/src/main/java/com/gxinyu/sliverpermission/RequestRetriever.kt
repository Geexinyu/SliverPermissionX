package com.gxinyu.sliverpermission

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

/**
 * 管理activity和fragment
 *
 *
 * 后续吧fragment添加到集合中
 */
class RequestRetriever {
    fun getRequestFragment(activity: Activity): RequestFragment {
        return getRequestFragment(activity.fragmentManager, null)
    }

    fun getRequestSupportFragment(activity: FragmentActivity): RequestSupportFragment {
        return getRequestSupportFragment(activity.supportFragmentManager, null)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun getRequestFragment(fragment: Fragment): RequestFragment {
        requireNotNull(fragment.activity) { "You cannot start a load on a fragment before it is attached" }
        val fm = fragment.childFragmentManager
        return getRequestFragment(fm, fragment)
    }

    private fun getRequestSupportFragment(fragment: androidx.fragment.app.Fragment): RequestSupportFragment {
        requireNotNull(fragment.activity) { "You cannot start a load on a fragment before it is attached" }
        val fm = fragment.childFragmentManager
        return getRequestSupportFragment(fm, fragment)
    }

    private fun getRequestFragment(
            fm: FragmentManager,
            parentHint: Fragment?): RequestFragment {
        var current: RequestFragment = fm.findFragmentByTag(FRAGMENT_TAG) as RequestFragment
        if (current == null) {
            current = RequestFragment()
            current.setParentFragmentHint(parentHint)
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        return current
    }

    private fun getRequestSupportFragment(
            fm: androidx.fragment.app.FragmentManager,
            parentHint: androidx.fragment.app.Fragment?): RequestSupportFragment {
        var current: RequestSupportFragment = fm.findFragmentByTag(FRAGMENT_TAG) as RequestSupportFragment
        if (current == null) {
            current = RequestSupportFragment()
            current.setParentFragmentHint(parentHint)
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        return current
    }

    /**
     * 公共的方法
     *
     * @param context
     * @param requestPermissions
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestPermissions(context: Context, request: Request, requestPermissions: Array<String>) {
        requireNotNull(context) { "You cannot start a load on a null context" }
        if (context is FragmentActivity) {
            val requestSupportFragment: RequestSupportFragment = getRequestSupportFragment(context)
            request(requestSupportFragment, request, requestPermissions)
        } else if (context is Activity) {
            val requestFragment: RequestFragment = getRequestFragment(context)
            request(requestFragment, request, requestPermissions)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun request(fragment: Fragment, request: Request, requestPermissions: Array<String>) {
        val current: RequestFragment = getRequestFragment(fragment)
        current.requestManager = request
        current.requestPermissions(requestPermissions, REQUEST_CODE_PERMISSION)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun request(fragment: androidx.fragment.app.Fragment, request: Request, requestPermissions: Array<String>) {
        val current: RequestSupportFragment = getRequestSupportFragment(fragment)
        current.requestManager = request
        current.requestPermissions(requestPermissions, REQUEST_CODE_PERMISSION)
    }

    /**
     * 公共的方法
     *
     * @param context
     */
    fun openSystemSetting(context: Context?) {
        requireNotNull(context) { "You cannot start a load on a null context" }
        if (context is FragmentActivity) {
            openSystemSetting(getRequestSupportFragment(context))
        } else if (context is Activity) {
            openSystemSetting(getRequestFragment(context))
        }
    }

    private fun openSystemSetting(fragment: Fragment) {
        var intent: Intent
        try {
            intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", fragment.activity.packageName, null)
            intent.data = uri
        } catch (e: Exception) {
            intent = Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS)
        }
        fragment.startActivityForResult(intent, REQUEST_CODE_SYSTEM)
    }

    private fun openSystemSetting(fragment: androidx.fragment.app.Fragment) {
        var intent: Intent
        try {
            intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", fragment.activity!!.packageName, null)
            intent.data = uri
        } catch (e: Exception) {
            intent = Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS)
        }
        fragment.startActivityForResult(intent, REQUEST_CODE_SYSTEM)
    }
}