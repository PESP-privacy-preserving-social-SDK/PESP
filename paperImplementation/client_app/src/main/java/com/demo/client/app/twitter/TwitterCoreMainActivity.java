package com.demo.client.app.twitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.ActionBar;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.client.app.R;
//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterAuthClient;
//import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TwitterCoreMainActivity extends AppCompatActivity {

    private Button loginButton;

    /**
     * Constructs an intent for starting an instance of this activity.
     * @param packageContext A context from the same package as this activity.
     * @return Intent for starting an instance of this activity.
     */
    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, TwitterCoreMainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twittercore_activity);

    }

}

