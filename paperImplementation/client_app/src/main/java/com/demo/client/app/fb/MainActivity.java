package com.demo.client.app.fb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.client.app.R;
import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.SDKProviderServiceCallback;
import com.demo.client_sdk.SDKProviderServiceCallbackProxy;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    private Button profileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb_activity_main);
        getSupportActionBar().hide();
        final View view = findViewById(R.id.activity_main);
        profileButton = (Button) findViewById(R.id.btn_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("evaluation", "fbprofile_evaluation start");
                Intent loginIntent = new Intent(MainActivity.this, FacebookProfileActivity.class);
                startActivity(loginIntent);
                Log.e("evaluation", "fbprofile_evaluation start");
            }
        });

        if (isFBLoggedIn()) {
//            Log.e("evaluation", "fblogin_evaluation end");
            Snackbar snackbar = Snackbar.make(view, "User Logged in", 4);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(view, "User not Logged in", 4);
            snackbar.show();
//            Intent loginIntent = new Intent(MainActivity.this, FacebookLoginActivity.class);
//            startActivity(loginIntent);
        }
    }

    public static OkHttpClient client = new OkHttpClient();
    public static String run(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e){
            return e.toString();
        }
    }
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    String post(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e){
            return e.toString();
        }
    }


    private boolean isFBLoggedIn() {
        int opaqueHandle = ClientSDK.getHandle("getFBLoginStatus", new Bundle(), new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
            @Override
            public void onSuccess(Bundle bundle) {
                Log.e(TAG, "getFBLoginStatus developer app onSuccess");
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "isFBLoggedIn onError");
            }
        }));
        Log.e(TAG, "getFBLoginStatus got opaqueHandle is :" + opaqueHandle);
        if (opaqueHandle < 0) {
            Log.e(TAG, "getFBLoginStatus opaqueHandle negative, maybe a trustedAPIService Error");
            return false;
        }
//        int opaqueHandle2
        final Boolean[] userLoggedIn = {false};
        int opaqueHandle2 = ClientSDK.sendSensitiveData(opaqueHandle, "https://developer.com","FBLoginSensitiveModule", new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
            @Override
            public void onSuccess(Bundle bundle) {
                Log.e(TAG, "developer app sendSensitiveData onSuccess");
                Log.e(TAG, "requesting http://127.0.0.1:8080/getLoginStatus");
                ClientSDK.executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://127.0.0.1:8080/getLoginStatus";
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            String res = response.body().string();
                            userLoggedIn[0] = true;
                            Log.e(TAG, "request returned " + res);
                            Log.e("evaluation", "fblogin_evaluation end");
                        } catch (IOException e){
                            userLoggedIn[0] = false;
                            Log.e(TAG, "request failed " + e);
                        }
                    }
                });
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "developer app sendSensitiveData onError");
            }
        }));

        if (opaqueHandle2 < 0) {
            Log.e(TAG, "sendSensitiveData opaqueHandle negative, maybe a trustedAPIService Error");
        }

        return userLoggedIn[0];
    }
}