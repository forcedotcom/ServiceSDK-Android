package com.salesforce.snapinssdkexample.auth

import com.salesforce.android.service.common.http.AuthTokenProvider
import com.salesforce.android.service.common.http.ResponseSummary
import com.salesforce.androidsdk.rest.ClientManager

class MobileSDKAuthTokenProvider(
        private val tokenProvider: ClientManager.AccMgrAuthTokenProvider,
        private var authToken: String
): AuthTokenProvider {

    override fun canRefresh(): Boolean { return true; }

    override fun refreshToken(responseSummary: ResponseSummary) {
        authToken = tokenProvider.newAuthToken
    }

    override fun getTokenType(): String? { return "Bearer" }

    override fun getToken(): String? { return authToken }

}
