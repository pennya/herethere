package com.herethere.www.util;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.herethere.www.BuildConfig;
import com.herethere.www.R;

/**
 * Created by KJH on 2017-09-24.
 * 위치 권한이 필요한 경우가 SearchActivity, TMapLocationSelectActivity 등 여러곳에서 필요하므로
 * 퍼미션을 부여받는 Base Activity를 만듬
 */

public abstract class BasePermissionActivity extends AppCompatActivity{
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE  = 99;

    protected FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    protected abstract void showToastFromBasePermissionActivity(String msg);
    protected abstract void getLocationInfo(double latitude, double longitude);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * 위치 권한이 있는지 없는지 확인
     * @return
     */
    protected boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    protected void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    protected void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            //사용자가 한번 권한을 거절한 경우 또는 '다시묻지않음'을 체크하여 거절한 경우
            new AlertDialog.Builder(this)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startLocationPermissionRequest();
                        }
                    })
                    .create()
                    .show();
        } else {
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                    // 사용자가 취소를 누른 경우
                    showToastFromBasePermissionActivity(getString(R.string.reject_location_permission));
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    // 사용자가 '다시묻지않음' 체크를 누른 경우 환경설정에서 수동 변경
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.title_location_permission)
                            .setMessage(R.string.text_default_setting_location_permission)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .create()
                            .show();
                }
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    protected void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            getLocationInfo(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        } else {
                            task.getException();
                            showToastFromBasePermissionActivity(getString(R.string.no_location_detected));
                        }
                    }
                });
    }
}
