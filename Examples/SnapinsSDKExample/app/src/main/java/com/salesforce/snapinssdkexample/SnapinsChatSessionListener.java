package com.salesforce.snapinssdkexample;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.salesforce.android.chat.core.SessionStateListener;
import com.salesforce.android.chat.core.model.ChatEndReason;
import com.salesforce.android.chat.core.model.ChatSessionState;

public class SnapinsChatSessionListener implements SessionStateListener {

  private Activity mActivity = null;

  public SnapinsChatSessionListener(Activity activity) {
    assert (activity != null);

    this.mActivity = activity;
  }

  @Override
  public void onSessionStateChange(ChatSessionState state) {
    // TODO: Handle chat session changed
  }

  @Override
  public void onSessionEnded(ChatEndReason endReason) {
    Log.i("Snapins Example","onSessionEnded: " + endReason.name());

    switch (endReason) {
      case EndedByAgent:
        // TODO: Tell user that the agent ended the session
        break;

      case NoAgentsAvailable:
        // TODO: Tell user that there aren't any agents available and give them an alternative
        Toast.makeText(mActivity, "No agents available. Consider taking a nap instead.",
                Toast.LENGTH_LONG).show();
        break;

    }
  }
}
