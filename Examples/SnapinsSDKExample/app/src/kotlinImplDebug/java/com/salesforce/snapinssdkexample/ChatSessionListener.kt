/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample

import android.content.Context
import android.widget.Toast
import com.salesforce.android.chat.core.SessionStateListener
import com.salesforce.android.chat.core.model.ChatEndReason
import com.salesforce.android.chat.core.model.ChatSessionState

/**
 * Class which listens for chat session and state and displays toasts.
 */
class ChatSessionListener(private val context: Context) : SessionStateListener {
    override fun onSessionEnded(endReason: ChatEndReason?) {
        showToast(endReason.toString())
    }

    override fun onSessionStateChange(state: ChatSessionState?) {}

    private fun showToast(reason: String) {
        Toast.makeText(context, "Chat state: $reason", Toast.LENGTH_SHORT).show()
    }
}
