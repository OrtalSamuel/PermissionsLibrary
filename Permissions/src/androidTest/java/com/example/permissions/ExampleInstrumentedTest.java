package com.example.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule2 = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule3 = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule4 = GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS);


    @Test
    public void permissionsRequestTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertTrue(PermissionManagerImpl.getInstance(appContext).checkPermissionStatus(Manifest.permission.CAMERA));
        assertTrue(PermissionManagerImpl.getInstance(appContext).checkPermissionStatus(Manifest.permission.ACCESS_FINE_LOCATION));
        assertTrue(PermissionManagerImpl.getInstance(appContext).checkPermissionStatus(Manifest.permission.READ_CONTACTS));
        assertFalse(PermissionManagerImpl.getInstance(appContext).checkPermissionStatus(Manifest.permission.READ_SMS));
    }
}