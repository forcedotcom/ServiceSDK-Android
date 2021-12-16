package com.salesforce.snapinssdkexample;

import android.app.Activity;
import android.app.Application;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.snapinssdkexample.activities.MainActivity;

public class ServiceSDKApplication extends Application {
    private ChatSessionListener chatSessionListener;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Salesforce Mobile SDK for authentication
        initializeSalesforceSDKManager(MainActivity.class);

        chatSessionListener = new ChatSessionListener(getApplicationContext());
    }

    private void initializeSalesforceSDKManager(Class<? extends Activity> activity) {
        SalesforceSDKManager.initNative(this, activity);
    }

    public ChatSessionListener getChatSessionListener() {
        return chatSessionListener;
    }


}
