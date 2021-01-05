package com.gxinyu.sliverpermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import java.util.Arrays;

/**
 *
 */
public class Request implements RequestCallBack {

    private int mode;
    private Object target;
    private RequestCallBack callBack;
    private String[] requestPermissions = new String[]{};//权限列表
    private AlertDialog notAllowedDialog;

    @NonNull
    public RequestRetriever getRequestRetriever() {
        return SliverPermission.get().getRequestRetriever();
    }

    @NonNull
    public Context getContext() {
        return getRequestRetriever().getContext(target);
    }

    /**
     * 校验权限名称是否正确
     */
    private void checkVaildPermission() {
        if (requestPermissions == null || requestPermissions.length == 0) {
            callBack.onPermissionDenied(null, "You can't apply for less than one permission");
            return;
        }
        for (int i = 0; i < requestPermissions.length; i++) {
            if (TextUtils.isEmpty(requestPermissions[i])) {
                callBack.onPermissionDenied(requestPermissions[i], "The permission you applied for is empty");
                return;
            }
            boolean contains = Arrays.asList(Permission.ALL_PERSSIONS).contains(requestPermissions[i]);
            if (!contains) {
                callBack.onPermissionDenied(requestPermissions[i], "The permission you applied for is not in the dynamic permissions");
                return;
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
    private int checkDeniedPermissionIndex(Context context, String[] permissionList) {
        for (int i = 0; i < permissionList.length; i++) {
            String permission = permissionList[i];
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param requestBuilder
     */
    public void apply(RequestBuilder requestBuilder) {
        RequestCallBack callBack = requestBuilder.callBack;
        this.mode = requestBuilder.mode;
        this.target = requestBuilder.target;
        this.callBack = callBack == null ? this : callBack;
        this.requestPermissions = requestBuilder.permissions;
        checkVaildPermission();
        requestPermissions();
    }

    public void requestPermissions() {
        int permissionIndex = checkDeniedPermissionIndex(getContext(), requestPermissions);
        if (permissionIndex == -1) {
            callBack.onPermissionGranted(requestPermissions);
        } else {
            String[] requestPermission = mode == RequestBuilder.NORMAL ? requestPermissions
                    : new String[]{requestPermissions[permissionIndex]};
            getRequestRetriever().requestPermissions(target, this, requestPermission);
        }
    }

    /**
     * fragment跳转到其他activity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SliverPermission.REQUEST_CODE_SYSTEM) {
            requestPermissions();
        }
    }

    /**
     * 权限申请回调
     *
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == SliverPermission.REQUEST_CODE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                int grantResult = grantResults[i];
                String permissionName = permissions[i];
                if (grantResult == -1) {
                    boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), permissionName);
                    if (!rationale) {
                        showNotAllowedPermission(getContext(), permissionName);
                        callBack.onPermissionDeniedNotAllowed(permissionName);
                        return;
                    } else {
                        callBack.onPermissionDenied(permissionName, permissionName + "权限被禁止!");
                        return;
                    }
                } else {
                    if (mode == RequestBuilder.CHAIN) {
                        requestPermissions();
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
    public void showNotAllowedPermission(final Context context, String permissionName) {
        //此时禁止弹出选择权限框，需要用户主动去开启
        if (notAllowedDialog == null) {
            notAllowedDialog = new AlertDialog.Builder(context)
                    .setTitle("权限提示")
                    .setMessage(permissionName + "权限拒绝不再询问,需要去系统权限管理处开启!")
                    .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //开启权限
                            getRequestRetriever().openSystemSetting(target);
                        }
                    })
                    .setNegativeButton("取消", null).create();
        }
        if (notAllowedDialog.isShowing()) {
            notAllowedDialog.dismiss();
        }

        notAllowedDialog.show();
    }

    @Override
    public void onPermissionGranted(String[] permissionList) {
        Log.e("TAG", "权限申请成功");
    }

    @Override
    public void onPermissionDenied(String permissionName, String message) {
        Log.e("TAG", "权限申请失败");
    }

    @Override
    public void onPermissionDeniedNotAllowed(String permissionName) {
        Log.e("TAG", "权限申请被拒绝");
    }


}
