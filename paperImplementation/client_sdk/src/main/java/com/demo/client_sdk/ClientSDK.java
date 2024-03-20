package com.demo.client_sdk;

import android.app.sdksandbox.SdkSandboxManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ClientSDK {
    private static final String TAG = ClientSDK.class.getCanonicalName();
    private static final AtomicBoolean sdkInitialized = new AtomicBoolean(false);
    private static Context applicationContext;

    private static final String SDKProviderServicePackageName = "com.demo.sdk.app";
    private static final String SDKProviderServiceClassName = "com.demo.sdk.app.SDKProviderService";
    private static ISDKProviderServiceInterface SDKProviderServiceInterface;
    private static final AtomicBoolean connected = new AtomicBoolean(false);
    public static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static final String SENSITIVE_BUNDLE_KEY = "sensitiveViewBundleKey";

    public static boolean isInitialized() {
        return sdkInitialized.get();
    }

    public static Context getApplicationContext() {
        if (!ClientSDK.isInitialized()) {
            Log.e(TAG,  "The ClientSDK has not been initialized, make sure to call "
                    + "ClientSDK.sdkInitialize() first.");
        }
        return applicationContext;
    }

    private static void connectToSDKProviderService() {
        Intent intent = new Intent();
        intent.setPackage(applicationContext.getPackageName());
        intent.setClassName(SDKProviderServicePackageName, SDKProviderServiceClassName);
        applicationContext.getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE | Context.BIND_INCLUDE_CAPABILITIES);
        Log.e(TAG, "connectToSDKProviderService 4");
    }

    private static final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected 5.1");
            SDKProviderServiceInterface = ISDKProviderServiceInterface.Stub.asInterface(service);
            connected.set(true);
            Log.e(TAG, "SDKProviderService connected");
            Toast.makeText(getApplicationContext() , "sdk-app service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceConnected 5.2");
            SDKProviderServiceInterface = null;
            connected.set(false);
            Log.e(TAG, "SDKProviderService disconnected ");
            Toast.makeText(getApplicationContext() , "sdk-app service disconnected", Toast.LENGTH_SHORT).show();
        }


    };

    public static synchronized void sdkInitialize(final Context applicationContext) {
        if (sdkInitialized.get()) {
            return;
        }
        ClientSDK.applicationContext = applicationContext.getApplicationContext();
        connectToSDKProviderService();
        sdkInitialized.set(true);
    }

    public static int getHandle(String functionSignature, Bundle input, ISDKProviderServiceCallbackInterface getHandleCallback){
        if(!ClientSDK.isInitialized()) {
            Log.e(TAG, "getHandle not initialized");
            return -1;
        }
        if (!ClientSDK.connected.get()){
            Log.e(TAG, "getHandle not connected");
            connectToSDKProviderService();
        }
        try{
            return ClientSDK.SDKProviderServiceInterface.getHandle(functionSignature, input, getApplicationContext().getApplicationInfo(), getHandleCallback);
        } catch (RemoteException e) {
            Log.e(TAG, "getHandle RemoteException Caught: " + e.toString());
            return - 1;
        }
    }

    public static int sendSensitiveData(int handle, String host, String sensitiveModuleName, ISDKProviderServiceCallbackInterface getHandleCallback){
        if(!ClientSDK.isInitialized()) {
            Log.e(TAG, "sendSensitiveData not initialized");
            return -1;
        }
        if (!ClientSDK.connected.get()){
            Log.e(TAG, "sendSensitiveData not connected");
            connectToSDKProviderService();
        }
        try{
            return ClientSDK.SDKProviderServiceInterface.sendSensitiveData(handle, host, sensitiveModuleName, getHandleCallback);
        } catch (RemoteException e) {
            Log.e(TAG, "sendSensitiveData RemoteException Caught: " + e.toString());
            return -1;
        }
    }

    public static SurfaceControlViewHost.SurfacePackage launchSensitiveModuleView(int handle, String sensitiveModuleName, ISDKProviderServiceCallbackInterface getHandleCallback) {
        if(!ClientSDK.isInitialized()) {
            Log.e(TAG, "launchSensitiveModuleView not initialized");
            return null;
        }
        if (!ClientSDK.connected.get()){
            Log.e(TAG, "launchSensitiveModuleView not connected");
            connectToSDKProviderService();
        }

        try{
            return ClientSDK.SDKProviderServiceInterface.launchSensitiveModuleView(handle, sensitiveModuleName, new Binder(), getHandleCallback);
        } catch (RemoteException e) {
            Log.e(TAG, "launchSensitiveModuleView RemoteException Caught: " + e.toString());
            return null;
        }
    }

}
