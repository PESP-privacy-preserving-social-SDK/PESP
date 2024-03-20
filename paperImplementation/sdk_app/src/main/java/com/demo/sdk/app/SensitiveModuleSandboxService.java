package com.demo.sdk.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.ISDKProviderServiceCallbackInterface;
import com.demo.client_sdk.SensitiveModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SensitiveModuleSandboxService extends Service {
    public static final String TAG = "SensitiveModuleSandboxService";
    public SensitiveModuleSandboxService() {
    }

    private final SensitiveModuleSandboxServiceInterface.Stub binder = new SensitiveModuleSandboxServiceInterface.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return android.os.Process.myPid();
        }

        @Override
        public int execSensitiveModule(int handle, String sensitiveModuleName) throws RemoteException {
            String sensitiveInformation = SDKProviderService.getSensitive(handle, sensitiveModuleName);

            try {
                Class[] param = new Class[1];
                param[0] = String.class;
                sensitiveModuleName = "com.demo.sdk.app." + sensitiveModuleName;
                Log.e(TAG, "execSensitiveModule input sensitiveModuleName is " + sensitiveModuleName);
                Class<?> clz = Class.forName(sensitiveModuleName);
                Constructor<?> ctor = clz.getConstructor(null);
                Object object = ctor.newInstance(new Object[] {});
                Method meth = clz.getDeclaredMethod("execute", param);
                meth.invoke(object, sensitiveInformation);
            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                Log.e(TAG, sensitiveModuleName + e.toString());
            }

            return 0;

        }

        @Override
        public SurfaceControlViewHost.SurfacePackage launchViewExecSensitiveModule(int handle, String sensitiveModuleName, IBinder hostToken, ISensitiveModuleSandboxServiceCallbackInterface getHandleCallback) throws RemoteException {
            String sensitiveInformaiton = SDKProviderService.getSensitive(handle, sensitiveModuleName);
            try {

                Class[] param = new Class[2];
                param[0] = String.class;
                param[1] = Context.class;
                sensitiveModuleName = "com.demo.sdk.app." + sensitiveModuleName;
                Log.e(TAG, "launchViewExecSensitiveModule input sensitiveModuleName is " + sensitiveModuleName);
                Class<?> clz = Class.forName(sensitiveModuleName);
                Constructor<?> ctor = clz.getConstructor(null);
                Object object = ctor.newInstance(new Object[] {});
                Method meth = clz.getDeclaredMethod("execute", param);



                DisplayManager mDisplayManager = getApplicationContext().getSystemService(DisplayManager.class);
                Context createWindowContext = getApplicationContext().createDisplayContext(mDisplayManager.getDisplay(0)).createWindowContext(
                        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, null
                );
                View parent = (View) meth.invoke(object,sensitiveInformaiton, getApplicationContext());


                SurfaceControlViewHost[] surfaceControlViewHost = new SurfaceControlViewHost[1];
                final SurfaceControlViewHost.SurfacePackage[] sp = new SurfaceControlViewHost.SurfacePackage[1];

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        surfaceControlViewHost[0] = new SurfaceControlViewHost(
                                createWindowContext,
                                mDisplayManager.getDisplay(0),
                                hostToken
                        );
                        Log.e(TAG, "surfacecontrolviewhost logging");
                        surfaceControlViewHost[0].setView(parent, 500, 800);
                        sp[0] = surfaceControlViewHost[0].getSurfacePackage();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ClientSDK.SENSITIVE_BUNDLE_KEY, sp[0]);
                        try {
                            getHandleCallback.onSuccess(bundle);
                        } catch (RemoteException e) {
                            Log.e(TAG, "returning onSuccess error");
                        }
                    }
                });


                return sp[0];

            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                Log.e(TAG, "reflection error: " + sensitiveModuleName + e.toString());
            }



            return null;
        }

    };


    @Override
    public IBinder onBind(Intent intent) {
       return binder.asBinder();
    }
}