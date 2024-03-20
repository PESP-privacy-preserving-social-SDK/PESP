package com.demo.client.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.client_sdk.ClientSDK;

public class MainActivity extends AppCompatActivity {
    Button jmp_fb_demo_btn;
    Button jmp_fb_profile_btn;
    Button jmp_twitter_demo_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_app_activity_main);
        getSupportActionBar().hide();
        jmp_fb_demo_btn = (Button) findViewById(R.id.jump_fb_demo_btn);
        jmp_fb_profile_btn = (Button) findViewById(R.id.jump_fb_profile_btn);
        jmp_twitter_demo_btn = (Button) findViewById(R.id.jump_twitter_login_btn);
        initializeButton();
    }

    private void initializeButton() {
        jmp_fb_demo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("evaluation", "fblogin_evaluation start");
                ClientSDK.sdkInitialize(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, com.demo.client.app.fb.MainActivity.class);
                startActivity(intent);
            }
        });

        jmp_fb_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("evaluation", "fbprofile_evaluation start");
                ClientSDK.sdkInitialize(getApplicationContext());
                Intent loginIntent = new Intent(MainActivity.this, com.demo.client.app.fb.FacebookProfileActivity.class);
                startActivity(loginIntent);
            }
        });

        jmp_twitter_demo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("evaluation", "twitterlogin_evaluation start");
                ClientSDK.sdkInitialize(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, com.demo.client.app.twitter.TwitterSampleActivity.class);
                startActivity(intent);
            }
        });
    }
}