package com.demo.client_sdk;

import android.content.Context;
import android.util.Log;
import android.view.View;

public abstract class SensitiveModule {
    public static String TAG = SensitiveModule.class.toString();

    public  abstract void execute(String sensitiveInformation);

    public abstract View execute(String sensitiveInformation, Context context);
}
