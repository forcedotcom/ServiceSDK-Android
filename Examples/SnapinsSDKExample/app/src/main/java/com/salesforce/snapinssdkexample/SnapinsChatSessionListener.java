/*
 * Copyright (c) 2018, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. Redistributions in
 * binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Salesforce.com nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
