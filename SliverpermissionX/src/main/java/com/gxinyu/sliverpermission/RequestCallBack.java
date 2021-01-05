package com.gxinyu.sliverpermission;

public interface RequestCallBack {

    void onPermissionGranted(String[] permissionList);

    void onPermissionDenied(String permissionName, String message);

    void onPermissionDeniedNotAllowed(String permissionName);
}
