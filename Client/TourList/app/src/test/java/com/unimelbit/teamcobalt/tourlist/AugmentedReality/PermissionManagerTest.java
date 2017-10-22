package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.karan.churi.PermissionManager.PermissionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the PermissionManager
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class PermissionManagerTest {

    PermissionManager manager;
    Activity activity;
    Context context;
    PackageManager packageManager;
    PackageInfo packageInfo;
    String[] permissions;

    /*
     * Sets up the permission manager before each test
     */
    @Before
    public void setUp() throws Exception {
        this.manager = new PermissionManager() {};
        this.activity = mock(Activity.class);
        this.context = mock(Context.class);
        this.packageManager = mock(PackageManager.class);
        this.packageInfo = new PackageInfo();


        this.permissions = new String[]{"a", "b", "c"};
        packageInfo.requestedPermissions = permissions;

        //noinspection WrongConstant
        when(packageManager.getPackageInfo(isA(String.class), eq(PackageManager.GET_PERMISSIONS))).thenReturn(packageInfo);

        when(context.getPackageName()).thenReturn("");
        when(context.getPackageManager()).thenReturn(packageManager);
        when(context.checkPermission(isA(String.class), isA(int.class), isA(int.class))).thenReturn(PackageManager.PERMISSION_GRANTED);

        when(activity.getApplicationContext()).thenReturn(context);


    }

    /*
     * Checks that the permissions are requested
     */
    @Test
    public void checkAndRequestPermissions() throws Exception {

        Boolean result = manager.checkAndRequestPermissions(activity);
        assertTrue(result);

    }

    /*
     * Checks that the status of the permissions is returned
     */
    @Test
    public void getStatus() throws Exception {

        manager.checkAndRequestPermissions(activity);
        ArrayList<PermissionManager.statusArray> status = manager.getStatus();
        assertTrue(status.get(0).denied.isEmpty());

        for (int i = 0; i < permissions.length; i++) {
            assertEquals(permissions[i], status.get(0).granted.get(i));
        }

    }

    /*
     * checks the Setter for the permissions
     */
    @Test
    public void setPermission() throws Exception {
        manager.checkAndRequestPermissions(activity);

       List<String> result= manager.setPermission();

        for (int i = 0; i < permissions.length; i++) {
            assertEquals(permissions[i], result.get(i));
        }

    }

}