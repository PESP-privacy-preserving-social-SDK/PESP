package com.demo.client.app.fb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.demo.client.app.R;
import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.SDKProviderServiceCallback;
import com.demo.client_sdk.SDKProviderServiceCallbackProxy;

public class FacebookProfileActivity extends AppCompatActivity {
    public static String TAG = FacebookProfileActivity.class.getName();
    private SurfaceView mFBSdkViewPlaceHolder;
//    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"launch FBProfileSensitiveModule started-1");
        setContentView(R.layout.activity_facebook_profile);

//        testBtn = findViewById(R.id.fb_profile_test_btn);
        Log.e(TAG,"launch FBProfileSensitiveModule started0");
        mFBSdkViewPlaceHolder = findViewById(R.id.fbsdk_view_placeholder);
        Log.e(TAG,"launch FBProfileSensitiveModule started");
        SurfaceControlViewHost.SurfacePackage surfacePackage = ClientSDK.launchSensitiveModuleView(102, "FBProfileViewSensitiveModule",  new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
            @Override
            public void onSuccess(Bundle bundle) {
                Log.e(TAG, "launchSensitiveModuleView developer app onSuccess");
                SurfaceControlViewHost.SurfacePackage surfacePackage = bundle.getParcelable(ClientSDK.SENSITIVE_BUNDLE_KEY, SurfaceControlViewHost.SurfacePackage.class);
                new Handler(Looper.getMainLooper()).post(() -> {
                    Log.e(TAG, "Setting surface package in the client view");
                    mFBSdkViewPlaceHolder.setChildSurfacePackage(surfacePackage);
                    mFBSdkViewPlaceHolder.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "launchSensitiveModuleView onError");
            }
        }));
        Log.e("evaluation", "fbprofile_evaluation end");
        Log.e(TAG,"launch FBProfileSensitiveModule finished");
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG,"launch FBProfileSensitiveModule started0");
//                mFBSdkViewPlaceHolder = findViewById(R.id.fbsdk_view_placeholder);
//                Log.e(TAG,"launch FBProfileSensitiveModule started");
//                SurfaceControlViewHost.SurfacePackage surfacePackage = ClientSDK.launchSensitiveModuleView(102, "FBProfileViewSensitiveModule",  new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
//                    @Override
//                    public void onSuccess(Bundle bundle) {
//                        Log.e(TAG, "launchSensitiveModuleView developer app onSuccess");
//                        SurfaceControlViewHost.SurfacePackage surfacePackage = bundle.getParcelable(ClientSDK.SENSITIVE_BUNDLE_KEY, SurfaceControlViewHost.SurfacePackage.class);
//                        new Handler(Looper.getMainLooper()).post(() -> {
//                            Log.e(TAG, "Setting surface package in the client view");
//                            mFBSdkViewPlaceHolder.setChildSurfacePackage(surfacePackage);
//                            mFBSdkViewPlaceHolder.setVisibility(View.VISIBLE);
//                        });
//                    }
//
//                    @Override
//                    public void onError(String errorMsg) {
//                        Log.e(TAG, "launchSensitiveModuleView onError");
//                    }
//                }));
//                Log.e(TAG,"launch FBProfileSensitiveModule finished");
//            }
//        });


    }
}