package com.salesforce.snapinssdkexample.auth

import com.salesforce.android.service.common.http.AuthenticatedUser

class MobileSdkUser(private val userId: String): AuthenticatedUser {
    override fun getUserId(): String { return userId }
}
