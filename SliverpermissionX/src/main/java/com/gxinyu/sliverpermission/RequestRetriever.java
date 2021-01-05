package com.gxinyu.sliverpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 管理activity和fragment
 * <p>
 * 后续吧fragment添加到集合中
 */
public class RequestRetriever {

    @NonNull
    public RequestFragment getRequestFragment(@NonNull Activity activity) {
        return getRequestFragment(activity.getFragmentManager(), null);
    }

    @NonNull
    public RequestSupportFragment getRequestSupportFragment(@NonNull FragmentActivity activity) {
        return getRequestSupportFragment(activity.getSupportFragmentManager(), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    public RequestFragment getRequestFragment(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        android.app.FragmentManager fm = fragment.getChildFragmentManager();
        return getRequestFragment(fm, fragment);
    }

    @NonNull
    public RequestSupportFragment getRequestSupportFragment(@NonNull Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        FragmentManager fm = fragment.getChildFragmentManager();
        return getRequestSupportFragment(fm, fragment);
    }

    @NonNull
    private RequestFragment getRequestFragment(
            @NonNull final android.app.FragmentManager fm,
            @Nullable android.app.Fragment parentHint) {
        RequestFragment current = (RequestFragment) fm.findFragmentByTag(SliverPermission.FRAGMENT_TAG);
        if (current == null) {
            current = new RequestFragment();
            current.setParentFragmentHint(parentHint);
            fm.beginTransaction().add(current, SliverPermission.FRAGMENT_TAG).commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        return current;
    }

    @NonNull
    private RequestSupportFragment getRequestSupportFragment(
            @NonNull final FragmentManager fm,
            @Nullable Fragment parentHint) {
        RequestSupportFragment current = (RequestSupportFragment) fm.findFragmentByTag(SliverPermission.FRAGMENT_TAG);
        if (current == null) {
            current = new RequestSupportFragment();
            current.setParentFragmentHint(parentHint);
            fm.beginTransaction().add(current, SliverPermission.FRAGMENT_TAG).commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        return current;
    }

    /**
     * 公共的方法
     *
     * @param target
     * @param requestPermissions
     */
    public void requestPermissions(Object target, Request request, String[] requestPermissions) {
        if (target == null) {
            throw new IllegalArgumentException("You cannot start a load on a null target");
        } else {
            if (target instanceof FragmentActivity) {
                request((FragmentActivity) target, request, requestPermissions);
            } else if (target instanceof Activity) {
                request((Activity) target, request, requestPermissions);
            } else if (target instanceof android.app.Fragment) {
                request((android.app.Fragment) target, request, requestPermissions);
            } else if (target instanceof Fragment) {
                request((Fragment) target, request, requestPermissions);
            }
        }
    }

    private void request(Activity activity, Request request, String[] requestPermissions) {
        RequestFragment current = getRequestFragment(activity);
        current.setRequestManager(request);
        current.requestPermissions(requestPermissions, SliverPermission.REQUEST_CODE_PERMISSION);
    }

    private void request(FragmentActivity activity, Request request, String[] requestPermissions) {
        RequestSupportFragment current = getRequestSupportFragment(activity);
        current.setRequestManager(request);
        current.requestPermissions(requestPermissions, SliverPermission.REQUEST_CODE_PERMISSION);
    }

    private void request(android.app.Fragment fragment, Request request, String[] requestPermissions) {
        RequestFragment current = getRequestFragment(fragment);
        current.setRequestManager(request);
        current.requestPermissions(requestPermissions, SliverPermission.REQUEST_CODE_PERMISSION);
    }

    private void request(Fragment fragment, Request request, String[] requestPermissions) {
        RequestSupportFragment current = getRequestSupportFragment(fragment);
        current.setRequestManager(request);
        current.requestPermissions(requestPermissions, SliverPermission.REQUEST_CODE_PERMISSION);
    }

    /**
     * 获取context
     *
     * @param target
     * @return
     */
    public Context getContext(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("You cannot start a load on a null target");
        } else {
            if (target instanceof FragmentActivity) {
                return (FragmentActivity) target;
            } else if (target instanceof Activity) {
                return (Activity) target;
            } else if (target instanceof android.app.Fragment) {
                return ((android.app.Fragment) target).getActivity();
            } else if (target instanceof Fragment) {
                return ((Fragment) target).getActivity();
            }
        }
        return null;
    }

    /**
     * 公共的方法
     *
     * @param target
     */
    public void openSystemSetting(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("You cannot start a load on a null target");
        } else {
            if (target instanceof FragmentActivity) {
                openSystemSetting(getRequestSupportFragment((FragmentActivity) target));
            } else if (target instanceof Activity) {
                openSystemSetting(getRequestFragment((Activity) target));
            } else if (target instanceof android.app.Fragment) {
                openSystemSetting(getRequestFragment(((android.app.Fragment) target).getActivity()));
            } else if (target instanceof Fragment) {
                openSystemSetting(getRequestSupportFragment(((Fragment) target).getActivity()));
            }
        }
    }

    private void openSystemSetting(android.app.Fragment fragment) {
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
            intent.setData(uri);
        } catch (Exception e) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        }
        fragment.startActivityForResult(intent, SliverPermission.REQUEST_CODE_SYSTEM);
    }

    private void openSystemSetting(Fragment fragment) {
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
            intent.setData(uri);
        } catch (Exception e) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        }
        fragment.startActivityForResult(intent, SliverPermission.REQUEST_CODE_SYSTEM);
    }


}
