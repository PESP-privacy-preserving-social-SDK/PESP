package com.demo.client.app.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.client.app.MainActivity;
import com.demo.client.app.R;
import com.demo.client.app.fb.FacebookLoginActivity;
import com.demo.client_sdk.ClientSDK;
import com.demo.client_sdk.SDKProviderServiceCallback;
import com.demo.client_sdk.SDKProviderServiceCallbackProxy;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.example.app.tweetcomposer.TweetComposerMainActivity;
//import com.example.app.tweetui.TweetUiMainActivity;
//import com.example.app.twittercore.TwitterCoreMainActivity;
public class TwitterSampleActivity extends AppCompatActivity {

    public static final String TAG = TwitterSampleActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_activity);
        isTwitterLoggedIn();

//        if (isTwitterLoggedIn()) {
//            Log.e("evaluation", "tiwtterlogin_evaluation end");
//        } else {
//            Intent loginIntent = new Intent(TwitterSampleActivity.this, MainActivity.class);
//            startActivity(loginIntent);
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        final MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.twitter_activity_actions, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    public void onTwitterCore(View view) {
        startActivity(new Intent(this, TwitterCoreMainActivity.class));
    }

    private boolean isTwitterLoggedIn() {
        int opaqueHandle = ClientSDK.getHandle("getTwitterLoginStatus", new Bundle(), new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
            @Override
            public void onSuccess(Bundle bundle) {
                Log.e(TAG, "getTwitterLoginStatus developer app onSuccess");
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "isTwitterLoggedIn onError");
            }
        }));
        Log.e(TAG, "getTwitterLoginStatus got opaqueHandle is :" + opaqueHandle);
        if (opaqueHandle < 0) {
            Log.e(TAG, "getTwitterLoginStatus opaqueHandle negative, maybe a trustedAPIService Error");
            return false;
        }
//        int opaqueHandle2
        final Boolean[] userLoggedIn = {false};
        int opaqueHandle2 = ClientSDK.sendSensitiveData(opaqueHandle, "https://developer.com","TwitterLoginSensitiveModule", new SDKProviderServiceCallbackProxy(Runnable::run, new SDKProviderServiceCallback() {
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
                        OkHttpClient client = new OkHttpClient();
                        try (Response response = client.newCall(request).execute()) {
                            String res = response.body().string();
                            userLoggedIn[0] = true;
                            Log.e(TAG, "request returned " + res);
                            Log.e("evaluation", "twitterlogin_evaluation end");
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

//    public void onTweetComposer(View view) {
//        startActivity(new Intent(this, TweetComposerMainActivity.class));
//    }

//    public void onTweetUi(View view) {
//        startActivity(new Intent(this, TweetUiMainActivity.class));
//    }
}
