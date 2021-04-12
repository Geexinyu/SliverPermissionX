package com.gxinyu.sliverpermission

interface RequestCallBack {

    fun onPermissionGranted(permissionList: Array<String>)

    fun onPermissionDenied(permissionName: String, message: String)

    fun onPermissionDeniedNotAllowed(permissionName: String?)


}