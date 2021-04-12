package com.gxinyu.sliverpermission

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import java.util.*

class RequestFragment : Fragment() {

    var requestManager: Request? = null
    private var rootRequestManagerFragment: RequestFragment? = null
    private val childRequestManagerFragments: MutableSet<RequestFragment> = HashSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            registerFragmentWithRoot(activity)
        } catch (e: IllegalStateException) {
        }
    }

    override fun onDetach() {
        super.onDetach()
        unregisterFragmentWithRoot()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterFragmentWithRoot()
    }

    private fun addChildRequestManagerFragment(child: RequestFragment) {
        childRequestManagerFragments.add(child)
    }

    private fun removeChildRequestManagerFragment(child: RequestFragment) {
        childRequestManagerFragments.remove(child)
    }

    fun setParentFragmentHint(parentFragmentHint: Fragment?) {
        if (parentFragmentHint != null && parentFragmentHint.activity != null) {
            registerFragmentWithRoot(parentFragmentHint.activity)
        }
    }

    private fun registerFragmentWithRoot(activity: Activity) {
        unregisterFragmentWithRoot()
        rootRequestManagerFragment = SliverPermission.INSTANCE.getRequestRetriever().getRequestFragment(activity)
        if (!equals(rootRequestManagerFragment)) {
            rootRequestManagerFragment!!.addChildRequestManagerFragment(this)
        }
    }

    private fun unregisterFragmentWithRoot() {
        if (rootRequestManagerFragment != null) {
            rootRequestManagerFragment!!.removeChildRequestManagerFragment(this)
            rootRequestManagerFragment = null
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        requestManager!!.onActivityResult(requestCode, resultCode, data)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}