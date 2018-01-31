/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample

import android.app.Application

class ServiceSDKApplication: Application() {
    lateinit var chatSessionListener: ChatSessionListener

    override fun onCreate() {
        super.onCreate()

        // Optional: Set up live agent chat session listener
        chatSessionListener = ChatSessionListener(applicationContext)
    }
}