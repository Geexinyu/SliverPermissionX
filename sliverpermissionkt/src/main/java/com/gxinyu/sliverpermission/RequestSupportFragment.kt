package com.gxinyu.sliverpermission

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class RequestSupportFragment : Fragment() {

    var requestManager: Request? = null
    private var rootRequestManagerFragment: RequestSupportFragment? = null
    private val childRequestManagerFragments: MutableSet<RequestSupportFragment> = HashSet()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            registerFragmentWithRoot(activity!!)
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

    private fun addChildRequestManagerFragment(child: RequestSupportFragment) {
        childRequestManagerFragments.add(child)
    }

    private fun removeChildRequestManagerFragment(child: RequestSupportFragment) {
        childRequestManagerFragments.remove(child)
    }

    fun setParentFragmentHint(parentFragmentHint: Fragment?) {
        if (parentFragmentHint != null && parentFragmentHint.activity != null) {
            registerFragmentWithRoot(parentFragmentHint.activity!!)
        }
    }

    private fun registerFragmentWithRoot(activity: FragmentActivity) {
        unregisterFragmentWithRoot()
        rootRequestManagerFragment = SliverPermission.INSTANCE.getRequestRetriever().getRequestSupportFragment(activity)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestManager!!.onActivityResult(requestCode, resultCode, data)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}