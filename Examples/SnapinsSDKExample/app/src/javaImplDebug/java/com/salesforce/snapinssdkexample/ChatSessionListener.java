package com.salesforce.snapinssdkexample;

import android.content.Context;
import android.widget.Toast;

import com.salesforce.android.chat.core.SessionStateListener;
import com.salesforce.android.chat.core.internal.liveagent.ChatStateListener;
import com.salesforce.android.chat.core.model.ChatEndReason;
import com.salesforce.android.chat.core.model.ChatSessionState;

/**
 * Class which listens for chat session and state and displays toasts.
 */

public class ChatSessionListener implements SessionStateListener {
    private Context context;

    public ChatSessionListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSessionStateChange(ChatSessionState chatSessionState) {

    }

    @Override
    public void onSessionEnded(ChatEndReason chatEndReason) {
        showToast(chatEndReason.toString());

    }

    private void showToast(String reason) {
        Toast.makeText(context,"Chat state:" + reason, Toast.LENGTH_SHORT).show();
    }

}
