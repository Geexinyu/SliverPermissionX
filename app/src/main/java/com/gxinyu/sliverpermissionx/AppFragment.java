package com.gxinyu.sliverpermissionx;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gxinyu.sliverpermission.Permission;
import com.gxinyu.sliverpermission.RequestBuilder;
import com.gxinyu.sliverpermission.SliverPermission;


public class AppFragment extends Fragment {

    public static String GROUP_SOMES[] = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_app, container);
        inflate.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        inflate.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });

        inflate.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someGroup();
            }
        });

        inflate.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someGroup2();
            }
        });

        inflate.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystem(getActivity());
            }
        });


        return inflate;
    }

    public void camera() {
        SliverPermission.with(this)
                .permissions(new String[]{Permission.CAMERA})
                .apply();
    }

    public void location() {
        SliverPermission.with(this)
                .permissions(Permission.GROUP_LOCATION)
                .apply();
    }

    public void someGroup() {
        SliverPermission.with(this)
                .permissions(GROUP_SOMES)
                .apply();
    }

    public void someGroup2() {
        SliverPermission.with(this)
                .mode(RequestBuilder.CHAIN)
                .permissions(GROUP_SOMES)
                .apply();
    }

    public void openSystem(Activity activity) {
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
        } catch (Exception e) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        }
        activity.startActivityForResult(intent, 1);
    }
}
