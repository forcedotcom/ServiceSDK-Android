package com.salesforce.snapinssdkexample.auth;

import android.support.annotation.NonNull;

import com.salesforce.android.service.common.http.AuthenticatedUser;

public class MobileSdkUser implements AuthenticatedUser {
    private String userId;

    public MobileSdkUser(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String getUserId() {
        return userId;
    }

}
