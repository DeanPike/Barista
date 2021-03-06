package com.adevinta.android.barista.sample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import com.adevinta.android.barista.interaction.PermissionGranter
import org.junit.Assert.assertEquals
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test

class PermissionGranterTest {

    @Before
    fun setUp() {
        assumeTrue("This test needs to run on a device with Android 23 or above", Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    }

    @Test
    fun grants_simple_permission() {
        launchActivity {
            verifyPermissionNotGranted(PERMISSION_1_CONTACTS)
            requestPermission(PERMISSION_1_CONTACTS)

            PermissionGranter.allowPermissionsIfNeeded(PERMISSION_1_CONTACTS)

            verifyPermissionGranted(PERMISSION_1_CONTACTS)
        }
    }

    /**
     * Since Android Q the Location permission dialog is different to the others
     */
    @Test
    fun grants_location_permission() {
        launchActivity {
            verifyPermissionNotGranted(LOCATION_PERMISSION)
            requestPermission(LOCATION_PERMISSION)

            PermissionGranter.allowPermissionsIfNeeded(LOCATION_PERMISSION)

            verifyPermissionGranted(LOCATION_PERMISSION)
        }
    }

    @Test
    fun ignores_already_granted_permission() {
        launchActivity {
            verifyPermissionNotGranted(PERMISSION_2_CAMERA)
            requestPermission(PERMISSION_2_CAMERA)
            PermissionGranter.allowPermissionOneTime(PERMISSION_2_CAMERA)
            verifyPermissionGranted(PERMISSION_2_CAMERA)

            PermissionGranter.allowPermissionsIfNeeded(PERMISSION_2_CAMERA)
        }
    }

    @Test
    fun denies_simple_permission() {
        launchActivity {
            verifyPermissionNotGranted(PERMISSION_3_CALLS)
            requestPermission(PERMISSION_3_CALLS)

            PermissionGranter.denyPermissions(PERMISSION_3_CALLS)

            verifyPermissionNotGranted(PERMISSION_3_CALLS)
        }
    }


}

// We can't reuse permission from one test to another, because they stay granted after each test
private const val PERMISSION_1_CONTACTS = Manifest.permission.READ_CONTACTS
private const val PERMISSION_2_CAMERA = Manifest.permission.CAMERA
private const val PERMISSION_3_CALLS = Manifest.permission.ANSWER_PHONE_CALLS
private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

private fun ActivityScenario<*>.requestPermission(permission: String) {
    onActivity { activity ->
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
    }
}

private fun ActivityScenario<*>.verifyPermissionNotGranted(permission: String) {
    onActivity { activity ->
        val permissionValue = ContextCompat.checkSelfPermission(activity, permission)
        assertEquals("Permission $permission expected to be denied but was granted;", PackageManager.PERMISSION_DENIED, permissionValue)
    }
}

private fun ActivityScenario<*>.verifyPermissionGranted(permission: String) {
    onActivity { activity ->
        val permissionValue = ContextCompat.checkSelfPermission(activity, permission)
        assertEquals("Expected permission $permission was not granted;", PackageManager.PERMISSION_GRANTED, permissionValue)
    }
}

private fun launchActivity(block: ActivityScenario<*>.() -> Unit) {
    ActivityScenario.launch<EmptyActivity>(EmptyActivity::class.java).use { scenario ->
        scenario.block()
    }
}
