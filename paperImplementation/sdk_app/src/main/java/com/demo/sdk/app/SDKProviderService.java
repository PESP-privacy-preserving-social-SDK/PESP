package com.demo.sdk.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;

import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.ISDKProviderServiceCallbackInterface;
import com.demo.client_sdk.ISDKProviderServiceInterface;

import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

public class SDKProviderService extends Service {

    private static final String TAG = SDKProviderService.class.getCanonicalName();
    private ISDKProviderServiceInterface.Stub stub = new ISDKProviderServiceInterface.Stub() {
        @Override
        public int getHandle(String functionSignature, Bundle input, ApplicationInfo applicationInfo, ISDKProviderServiceCallbackInterface getHandleCallback) throws RemoteException {
//            int callingUid = Binder.getCallingUid();
//            long token = Binder.clearCallingIdentity();
            int handle = SDKProviderService.getHandle(functionSignature, input, applicationInfo);
//            Binder.restoreCallingIdentity(token);
            Log.e(TAG, "returning: " + handle);

            Bundle bundle = new Bundle();
            bundle.putString("success key", "success value");
            try {
                getHandleCallback.onSuccess(bundle);
            } catch (RemoteException e) {
                Log.e("getHandleCallback","getHandleCallback onSuccess Remote Error");
                e.printStackTrace();
            }
            return handle;
        }

        @Override
        public int sendSensitiveData(int handle, String remoteHost, String sensitiveModuleName, ISDKProviderServiceCallbackInterface getHandleCallback) throws RemoteException {
            int ret_handle = SDKRuntime.sanboxservice.execSensitiveModule(handle, sensitiveModuleName);

            Log.e(TAG, "sendSensitiveData returning: " + ret_handle);
            Bundle bundle = new Bundle();
            bundle.putString("success key", "success value");
            getHandleCallback.onSuccess(bundle);
            return ret_handle;
        }

        @Override
        public SurfaceControlViewHost.SurfacePackage launchSensitiveModuleView(int handle, String sensitiveModuleName, IBinder hostToken, ISDKProviderServiceCallbackInterface getHandleCallback) throws RemoteException {
            Log.e(TAG, "launchSensitiveModuleView invoked");
            SurfaceControlViewHost.SurfacePackage sp = SDKRuntime.sanboxservice.launchViewExecSensitiveModule(handle, sensitiveModuleName, hostToken, new SensitiveModuleServiceCallbackProxy(Runnable::run, new SensitiveModuleServiceCallback() {
                @Override
                public void onSuccess(Bundle bundle) {
                    try {
                        getHandleCallback.onSuccess(bundle);
                    } catch (RemoteException e) {
                        Log.e(TAG, "returning onSuccess error");
                    }
                }

                @Override
                public void onError(String errorMsg) {

                }
            }));
            Log.e(TAG, "launchSensitiveModuleView returning: ");
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(ClientSDK.SENSITIVE_BUNDLE_KEY, sp);
//            getHandleCallback.onSuccess(bundle);
//            DisplayManager mDisplayManager =
//                getApplicationContext().getSystemService(DisplayManager.class);
//            Context createWindowContext =
//                getApplicationContext().createDisplayContext(mDisplayManager.getDisplay(0))
//                        .createWindowContext(
//                                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, null
//                        );
//            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(
//                createWindowContext,
//                mDisplayManager.getDisplay(0),
//                hostToken
//            );
//            return surfaceControlViewHost.getSurfacePackage();
            return sp;
        }

        @Override
        public int getPid(){
            return android.os.Process.myPid();
        }
    };
    public SDKProviderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind from " + intent.getPackage());
        return stub.asBinder();
    }

    public static String getSensitive(int handle, String sensitiveModuleName) {
        Map<Integer, String> sensitiveStorage = new HashMap();
        sensitiveStorage.put(101, "True"); //getFBLoginStatus
        sensitiveStorage.put(102, "Test User\nsensitiveUserID\nsensitiveUserEmail\n\ngranted\ngranted\ngranted\ngranted");
        sensitiveStorage.put(201, "True"); //getTwitterLoginStatus

        if (!sensitiveStorage.containsKey(handle)){
            return "handle-invalid";
        }
        Log.e(TAG, "sensitiveModule" + sensitiveModuleName + "getSensitive: " + sensitiveStorage.get(handle));
        return sensitiveStorage.get(handle);
    }

    public static int getHandle(String functionSignature, Bundle input, ApplicationInfo applicationInfo) {
        Map<Integer, String> sensitiveStorage = new HashMap();
        Map<String, Integer> mockCallMap = new HashMap();


        mockCallMap.put("getFBLoginStatus", 101);
        mockCallMap.put("getFBUserProfile", 102);
        mockCallMap.put("getTwitterLoginStatus", 201);

        if (!mockCallMap.containsKey(functionSignature)){
            return -999;
        }
        return mockCallMap.get(functionSignature);
//        return 88888;
    }

    private ClassLoader getClassLoader(ApplicationInfo applicationInfo) {
        return new DexClassLoader(applicationInfo.sourceDir, null, null, getClass().getClassLoader());
    }
}