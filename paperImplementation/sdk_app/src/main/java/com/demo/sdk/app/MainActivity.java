package com.demo.sdk.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.SDKProviderServiceCallback;
import com.demo.client_sdk.SDKProviderServiceCallbackProxy;

public class MainActivity extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SDKRuntime.getInstance().connectToSensitiveModuleSanboxService();
                try {
                    SDKRuntime.getInstance().sanboxservice.getPid();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
//                ClientSDK.sdkInitialize(getApplicationContext());
//                ClientSDK.getHandle("cccc",new Bundle(), new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
//                    @Override
//                    public void onSuccess(Bundle bundle) {
//                        Log.e("ccccccc", "isFBLoggedIn onSuccess");
//
//
//                    }
//
//                    @Override
//                    public void onError(String errorMsg) {
//                        Log.e("cccccc", "isFBLoggedIn onError");
//                    }
//                }));
//                Intent intent = new Intent(MainActivity.this, com.demo.client.app.fb.MainActivity.class);
//                startActivity(intent);
            }
        });

    }
}