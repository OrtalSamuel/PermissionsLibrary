package com.example.libraryleasson;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.permissions.CurrencyManager;
import com.example.permissions.PermissionManagerImpl;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final int CONTACTS_PERMISSION_REQUEST_CODE = 102;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });

        findViewById(R.id.btnContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactsPermission();
            }
        });

        findViewById(R.id.btnLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });

    }

    private void requestCameraPermission() {
        PermissionManagerImpl permissionManager = PermissionManagerImpl.getInstance(this);
        if (!permissionManager.checkPermissionStatus(android.Manifest.permission.CAMERA)) {
            permissionManager.requestPermission(this, android.Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE, new Runnable() {
                @Override
                public void run() {
                    openCameraApp();
                }
            });
        } else {
            // Camera permission already granted
            Toast.makeText(this, "Camera permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestLocationPermission() {
        PermissionManagerImpl permissionManager = PermissionManagerImpl.getInstance(this);
        if (!permissionManager.checkPermissionStatus(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionManager.requestPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE, new Runnable() {
                @Override
                public void run() {
                    openLocationSettings();
                }
            });
        } else {
            // Location permission already granted
            Toast.makeText(this, "Location permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestContactsPermission() {
        PermissionManagerImpl permissionManager = PermissionManagerImpl.getInstance(this);
        if (!permissionManager.checkPermissionStatus(android.Manifest.permission.READ_CONTACTS)) {
            permissionManager.requestPermission(this, android.Manifest.permission.READ_CONTACTS, CONTACTS_PERMISSION_REQUEST_CODE, new Runnable() {
                @Override
                public void run() {
                    openContactsApp();
                }
            });
        } else {
            // Contacts permission already granted
            Toast.makeText(this, "Contacts permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLocationSettings() {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(locationIntent);
    }

    public void openCameraApp() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void openContactsApp() {
        Intent contactsIntent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        startActivity(contactsIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Delegate permission handling to the singleton instance with the correct context
        PermissionManagerImpl.getInstance(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

//    private void openDialog() {
//        CurrencyManager.openCurrencyDialog(this, new CurrencyManager.CallBack_Currency() {
//            @Override
//            public void selected(String name, String Code, String symbol) {
//                main_BTM_open.setText(symbol);
//            }
//        });
//    }
