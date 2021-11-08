package com.example.mamman.PushKit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.mamman.R;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;

public class PushKitActivity extends AppCompatActivity {
    private static final String TAG = "PushKitActivity";
    Button btn_getToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_kit);
        btn_getToken = (Button)findViewById(R.id.btn_getToken);
        btn_getToken.setOnClickListener(v -> {
            getToken();
        });
    }

    private void getToken() {
        // Create a thread.
        new Thread() {
            @Override
            public void run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    String appId = AGConnectServicesConfig.fromContext(PushKitActivity.this).getString("client/app_id");
                    // Set tokenScope to HCM.
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(PushKitActivity.this).getToken(appId, tokenScope);
                    Log.i(TAG, "get token: " + token);

                    // Check whether the token is empty.
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }
}