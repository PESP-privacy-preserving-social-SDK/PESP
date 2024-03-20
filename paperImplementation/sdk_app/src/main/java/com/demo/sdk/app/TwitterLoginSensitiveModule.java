package com.demo.sdk.app;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.SensitiveModule;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TwitterLoginSensitiveModule extends SensitiveModule {
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public TwitterLoginSensitiveModule(){
        client = new OkHttpClient();
    }

    @Override
    public void execute(String sensitiveInformation) {
        Log.e(TAG, "TwitterLoginSensitiveModule modified Logging got SensitiveInformation: " + sensitiveInformation);
        ClientSDK.executorService.execute(new Runnable() {
            @Override
            public void run() {
                String url = "http://127.0.0.1:8080/sendLoginStatus";
                RequestBody body = RequestBody.create("{\"LoggedIn\":\"true\"}", JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    Log.e(TAG, "sendLoginStatus response: " + response.body().string());
                } catch (IOException e){
                    Log.e(TAG, "sendLoginStatus error: " + e);
                }
            }
        });
    }

    @Override
    public View execute(String sensitiveInformation, Context context) {
        return null;
    }
}
