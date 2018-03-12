/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample

import android.app.Application
import android.support.v7.app.AppCompatActivity
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.salesforce.snapinssdkexample.activities.MainActivity
import kotlin.reflect.KClass

class ServiceSDKApplication: Application() {
    lateinit var chatSessionListener: ChatSessionListener

    override fun onCreate() {
        super.onCreate()

        initializeSalesforceSDKManager(MainActivity::class)

        // Optional: Set up live agent chat session listener
        chatSessionListener = ChatSessionListener(applicationContext)
    }

    private fun initializeSalesforceSDKManager(activityClass: KClass<out AppCompatActivity>) {
        SalesforceSDKManager.initNative(this, null, activityClass.java)
    }
}