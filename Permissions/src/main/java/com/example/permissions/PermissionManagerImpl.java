package com.example.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

public class PermissionManagerImpl implements PermissionManager {
    private static PermissionManagerImpl instance;
    private Context context;
    private HashMap<Integer, Runnable> runAfterPermissionGranted;

    private PermissionManagerImpl(Context context) {
        this.context = context;
        runAfterPermissionGranted = new HashMap<>();
    }

    public static synchronized PermissionManagerImpl getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionManagerImpl(context);
        }
        return instance;
    }

    @Override
    public boolean checkPermissionStatus(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission(Activity activity, String permission, int requestCode, Runnable runAfterPermissionGranted) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            this.runAfterPermissionGranted.put(requestCode, runAfterPermissionGranted);
            //   ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            showCustomDialog(activity, permission, requestCode);
        } else {
            runAfterPermissionGranted.run();
        }
    }

    private void showCustomDialog(Activity activity, String permission, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = null;

        switch (permission) {
            case android.Manifest.permission.CAMERA:
                dialogView = inflater.inflate(R.layout.dialog_camera_permission, null);
                break;
            case android.Manifest.permission.READ_CONTACTS:
                dialogView = inflater.inflate(R.layout.dialog_contacts_permission, null);
                break;
            case android.Manifest.permission.ACCESS_FINE_LOCATION:
                dialogView = inflater.inflate(R.layout.dialog_location_permission, null);
                break;
        }

        if (dialogView != null) {
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialogView.findViewById(R.id.btnGrantPermission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }



    @Override
    public void handlePermissionResponse(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (runAfterPermissionGranted.get(requestCode) != null)
                runAfterPermissionGranted.get(requestCode).run();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Delegate handling to the singleton instance
        getInstance(context).handlePermissionResponse(requestCode, permissions, grantResults);
    }
}