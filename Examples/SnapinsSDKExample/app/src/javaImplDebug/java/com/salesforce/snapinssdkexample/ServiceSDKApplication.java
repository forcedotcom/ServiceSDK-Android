package com.salesforce.snapinssdkexample;

import android.app.Application;

public class ServiceSDKApplication extends Application {
    private ChatSessionListener chatSessionListener;

    @Override
    public void onCreate() {
        super.onCreate();

        chatSessionListener = new ChatSessionListener(getApplicationContext());
    }

    public ChatSessionListener getChatSessionListener() {
        return chatSessionListener;
    }
}
