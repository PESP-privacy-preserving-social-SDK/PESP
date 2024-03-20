// ISDKProviderServiceInterface.aidl
package com.demo.client_sdk;


// Declare any non-default types here with import statements
import com.demo.client_sdk.ISDKProviderServiceCallbackInterface;
import android.content.pm.ApplicationInfo;
import android.view.SurfaceControlViewHost.SurfacePackage;
import android.os.IBinder;

interface ISDKProviderServiceInterface {
    int getHandle(String functionSignature, in Bundle input, in ApplicationInfo applicationInfo, in ISDKProviderServiceCallbackInterface getHandleCallback);
    int sendSensitiveData(int handle, String remoteHost, String sensitiveModuleName, in ISDKProviderServiceCallbackInterface getHandleCallback);
    SurfacePackage launchSensitiveModuleView(int handle,String sensitiveModuleName, in IBinder hostToken, in ISDKProviderServiceCallbackInterface getHandleCallback);
    int getPid();
}