package com.gxinyu.sliverpermission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * 校验生命周期
 * <p>
 * 版本校验
 * <p>
 * 还有其他设置
 */

public class SliverPermission {

    public static final String FRAGMENT_TAG = "com.gxinyu.sliverpermission";
    public static int REQUEST_CODE_PERMISSION = 0x01;//权限请求码
    public static int REQUEST_CODE_SYSTEM = 0x02;//打开系统权限设置

    private SliverPermission() {
    }

    private static class SingleTonHolder {
        private static SliverPermission INSTANCE = new SliverPermission();
    }

    public static SliverPermission get() {
        return SingleTonHolder.INSTANCE;
    }

    public static RequestBuilder with(Activity activity) {
        checkBuildVersion();
        checkContext(activity);
        RequestBuilder requestBuilder = new RequestBuilder(activity);
        return requestBuilder;
    }

    public static RequestBuilder with(FragmentActivity activity) {
        checkBuildVersion();
        checkContext(activity);
        RequestBuilder requestBuilder = new RequestBuilder(activity);
        return requestBuilder;
    }

    public static RequestBuilder with(android.app.Fragment fragment) {
        checkBuildVersion();
        checkContext(fragment.getActivity());
        RequestBuilder requestBuilder = new RequestBuilder(fragment);
        return requestBuilder;
    }

    public static RequestBuilder with(Fragment fragment) {
        checkBuildVersion();
        checkContext(fragment.getActivity());
        RequestBuilder requestBuilder = new RequestBuilder(fragment);
        return requestBuilder;
    }


    public static RequestRetriever getRequestRetriever() {
        return new RequestRetriever();
    }

    private static void checkBuildVersion() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
    }

    private static void checkContext(Context context) {
        if (context == null) {
            throw new NullPointerException("You context must not null for apply permission!");
        }
        if (context instanceof Activity) {
            assertNotDestroyed((Activity) context);
            isActivityVisible((Activity) context);
        } else if (context instanceof Application) {
            throw new IllegalArgumentException("You context must be activity for apply permission!");
        } else if (context instanceof Service) {
            throw new IllegalArgumentException("You context must be activity for apply permission!");
        }
    }

    public static void isActivityVisible(Activity activity) {
        if (activity.isFinishing()) {
            throw new IllegalArgumentException("You cannot start a load for a finishing activity");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

}
