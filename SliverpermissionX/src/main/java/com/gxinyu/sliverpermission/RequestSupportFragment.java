package com.gxinyu.sliverpermission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.HashSet;
import java.util.Set;


public class RequestSupportFragment extends Fragment {

    private Request mRequestManager;
    private RequestSupportFragment rootRequestManagerFragment;
    private final Set<RequestSupportFragment> childRequestManagerFragments = new HashSet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            registerFragmentWithRoot(getActivity());
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
    private void addChildRequestManagerFragment(RequestSupportFragment child) {
        childRequestManagerFragments.add(child);
    }

    @SuppressWarnings("deprecation")
    private void removeChildRequestManagerFragment(RequestSupportFragment child) {
        childRequestManagerFragments.remove(child);
    }

    public void setParentFragmentHint(Fragment parentFragmentHint) {
        if (parentFragmentHint != null && parentFragmentHint.getActivity() != null) {
            registerFragmentWithRoot(parentFragmentHint.getActivity());
        }
    }

    @SuppressWarnings("deprecation")
    private void registerFragmentWithRoot(@NonNull FragmentActivity activity) {
        unregisterFragmentWithRoot();
        rootRequestManagerFragment = SliverPermission.get().getRequestRetriever().getRequestSupportFragment(activity);
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
