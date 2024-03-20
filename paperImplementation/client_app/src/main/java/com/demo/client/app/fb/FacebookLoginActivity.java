package com.demo.client.app.fb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.demo.client.app.R;

public class FacebookLoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
    }
}