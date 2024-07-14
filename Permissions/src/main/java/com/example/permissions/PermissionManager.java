package com.example.permissions;

import android.app.Activity;

public interface PermissionManager {

    boolean checkPermissionStatus(String permission);
    void requestPermission(Activity activity, String permission, int requestCode, Runnable runAfterPermissionGranted);
    void handlePermissionResponse(int requestCode, String[] permissions, int[] grantResults);
    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
