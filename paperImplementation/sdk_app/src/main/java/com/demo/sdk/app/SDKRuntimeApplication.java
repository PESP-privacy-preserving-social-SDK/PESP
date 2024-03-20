package com.demo.sdk.app;

import android.app.Application;
import android.util.Log;

public class SDKRuntimeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        FacebookSdk.setApplicationId("3040888906125350");
//        FacebookSdk.setClientToken("99efe166f74e9a25f291466abd56827a");
//        FacebookSdk.sdkInitialize(this);
        Log.e("SDKRuntimeApplication", String.valueOf(getApplicationContext()));
        SDKRuntime.initialize(getApplicationContext());
    }
}

