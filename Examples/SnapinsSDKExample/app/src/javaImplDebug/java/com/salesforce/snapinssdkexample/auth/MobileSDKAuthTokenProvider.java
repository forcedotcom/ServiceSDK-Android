package com.salesforce.snapinssdkexample.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.salesforce.android.service.common.http.AuthTokenProvider;
import com.salesforce.android.service.common.http.ResponseSummary;
import com.salesforce.androidsdk.rest.ClientManager;

public class MobileSDKAuthTokenProvider implements AuthTokenProvider {
    private final ClientManager.AccMgrAuthTokenProvider tokenProvider;
    private String authToken;

    public MobileSDKAuthTokenProvider(ClientManager.AccMgrAuthTokenProvider tokenProvider, String authToken) {
        this.tokenProvider = tokenProvider;
        this.authToken = authToken;
    }

    @Nullable
    @Override
    public String getToken() {
        return authToken;
    }

    @Nullable
    @Override
    public String getTokenType() {
        return "Bearer";
    }

    @Override
    public boolean canRefresh() {
        return true;
    }

    @Override
    public void refreshToken(@NonNull ResponseSummary responseSummary) {
        authToken = tokenProvider.getNewAuthToken();
    }
}
