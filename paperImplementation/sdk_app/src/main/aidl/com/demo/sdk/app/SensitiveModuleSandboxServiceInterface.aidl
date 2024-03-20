// SensitiveModuleSandboxServiceInterface.aidl
package com.demo.sdk.app;
import android.view.SurfaceControlViewHost.SurfacePackage;
import com.demo.sdk.app.ISensitiveModuleSandboxServiceCallbackInterface;

// Declare any non-default types here with import statements

interface SensitiveModuleSandboxServiceInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    int getPid();
    int execSensitiveModule(int handle, String sensitiveModuleName);
    SurfacePackage launchViewExecSensitiveModule(int handle, String sensitiveModuleName, in IBinder hostToken, in ISensitiveModuleSandboxServiceCallbackInterface getHandleCallback);
}