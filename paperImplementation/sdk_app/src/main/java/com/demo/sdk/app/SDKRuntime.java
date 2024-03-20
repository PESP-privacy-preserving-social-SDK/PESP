package com.demo.sdk.app;

import android.app.sdksandbox.SdkSandboxManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//import com.facebook.FacebookSdk;

import java.util.concurrent.atomic.AtomicBoolean;

public final class SDKRuntime {
    private static final String TAG = SDKRuntime.class.getCanonicalName();
    private static volatile SDKRuntime instance;

    private static final AtomicBoolean sdkruntimeInitialized = new AtomicBoolean(false);
    public static Context applicationContext;
//    private static SdkSandboxManager sdkSandboxManager;
//    public static final String SDK_NAME = "com.demo.fbsdk.provider";
//
//    private static ISdkProviderPublicServiceInterface SdkProviderPublicServiceInterface;
//    public static boolean connected;
    public static SensitiveModuleSandboxServiceInterface sanboxservice;

    static SDKRuntime getInstance() {
        if (instance == null) {
            synchronized (SDKRuntime.class) {
                if (instance == null) {
//                    Context applicationContext = .getApplicationContext();
                    instance = new SDKRuntime();
                }
            }
        }
        return instance;
    }

    SDKRuntime (){
    }

    public static synchronized void initialize(final Context applicationContext) {
        if (sdkruntimeInitialized.get()) {return;}
        SDKRuntime.applicationContext = applicationContext;
        connectToSensitiveModuleSanboxService();
        sdkruntimeInitialized.set(true);

//        FacebookSdk.setApplicationId("3040888906125350");
//        FacebookSdk.setClientToken("99efe166f74e9a25f291466abd56827a");
//        FacebookSdk.sdkInitialize(applicationContext);
    }


    public static void connectToSensitiveModuleSanboxService() {
        Log.e(TAG, "start connecting to SensitiveModuleSandboxService");
//        Intent intent = new Intent();
//        intent.setPackage(this.getPackageName());
//        intent.setClassName("com.demo.fbsdk.provider.app", "com.example.provider.SensitiveModuleSandbox");
        Intent intent = new Intent(applicationContext, SensitiveModuleSandboxService.class);

        applicationContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE | Context.BIND_INCLUDE_CAPABILITIES );
//        bindService(intent, serviceConnection, BIND_IMPORTANT );
        Log.e(TAG, "end connecting to sensitive module");
    }

    private static final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sanboxservice = SensitiveModuleSandboxServiceInterface.Stub.asInterface(service);
            Log.e(TAG, "sensitiveModuleSandbox onservice connected");
//            Toast.makeText(getApplicationContext() , "sdk-app service connected", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            sanboxservice = null;
            Log.e(TAG, "sensitiveModuleSandbox onservice disconnected ");
//            Toast.makeText(SdkProviderPublicService., "sdk-app service disconnected", Toast.LENGTH_SHORT).show();
        }
    };
}
