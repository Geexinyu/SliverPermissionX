package com.gxinyu.sliverpermission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class RequestFragment extends Fragment {

    private Request mRequestManager;
    private RequestFragment rootRequestManagerFragment;
    private final Set<RequestFragment> childRequestManagerFragments = new HashSet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            registerFragmentWithRoot(activity);
        } catch (IllegalStateException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unregisterFragmentWithRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterFragmentWithRoot();
    }

    public Request getRequestManager() {
        return mRequestManager;
    }

    public void setRequestManager(Request requestManager) {
        mRequestManager = requestManager;
    }

    @SuppressWarnings("deprecation")
    private void addChildRequestManagerFragment(RequestFragment child) {
        childRequestManagerFragments.add(child);
    }

    @SuppressWarnings("deprecation")
    private void removeChildRequestManagerFragment(RequestFragment child) {
        childRequestManagerFragments.remove(child);
    }

    public void setParentFragmentHint(Fragment parentFragmentHint) {
        if (parentFragmentHint != null && parentFragmentHint.getActivity() != null) {
            registerFragmentWithRoot(parentFragmentHint.getActivity());
        }
    }

    @SuppressWarnings("deprecation")
    private void registerFragmentWithRoot(@NonNull Activity activity) {
        unregisterFragmentWithRoot();
        rootRequestManagerFragment = SliverPermission.get().getRequestRetriever().getRequestFragment(activity);
        if (!equals(rootRequestManagerFragment)) {
            rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    private void unregisterFragmentWithRoot() {
        if (rootRequestManagerFragment != null) {
            rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            rootRequestManagerFragment = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRequestManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestManager.onRequestPermissionsResult(requestCode,permissions, grantResults);
    }


}
