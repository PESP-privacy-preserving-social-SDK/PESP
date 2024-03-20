package com.demo.sdk.app;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.client_sdk.SensitiveModule;

import java.util.Arrays;

public class FBProfileViewSensitiveModule extends  SensitiveModule{
    private static final String TAG = FBProfileViewSensitiveModule.class.toString();
    public FBProfileViewSensitiveModule(){
        Log.e(TAG, "FBProfileViewSensitiveModule modified Logging init method called");
    }

    @Override
    public void execute(String sensitiveInformation) {

    }

    public View execute(String sensitiveInformation, Context createWindowContext) {

        Log.e(TAG, "FBProfileViewSensitiveModule modified Logging got SensitiveInformation: " + sensitiveInformation);
        String[] list = sensitiveInformation.split("\n");
        Log.e(TAG, Arrays.asList(list).toString());
        String name = list[0];
        String id = list[1];
        String email = list[2];
        // Build permissions display string
        StringBuilder builder = new StringBuilder();
        builder.append("Permissions: " + list[3] + "\n");
        builder.append("user_posts: " + list[4] + "\n");
        builder.append("email: " + list[5] + "\n");
        builder.append("openid: " + list[6] + "\n");
        builder.append("public_profile: " + list[7] + "\n");
        String permissions = builder.toString();

        LinearLayout parent = new LinearLayout(createWindowContext);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setBackgroundColor(Color.LTGRAY);

//children of parent linearlayout

        ImageView iv = new ImageView(createWindowContext);
        iv.setImageResource(R.drawable.blank_profile_img_128x128);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(128, 128);
        iv.setLayoutParams(lp);
        parent.addView(iv);

        TextView tv1 = new TextView(createWindowContext);
        TextView tv2 = new TextView(createWindowContext);
        TextView tv3 = new TextView(createWindowContext);
        TextView tv4 = new TextView(createWindowContext);

        tv1.setText(name);
        tv2.setText(id);
        tv3.setText(email);
        tv4.setText(permissions);

        parent.addView(tv1);
        parent.addView(tv2);
        parent.addView(tv3);
        parent.addView(tv4);

        return parent;
    }
}
