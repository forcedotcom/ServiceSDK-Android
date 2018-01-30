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

package com.example.salesforce.SnapinsChatSurvey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.SessionStateListener;
import com.salesforce.android.chat.core.model.ChatEndReason;
import com.salesforce.android.chat.core.model.ChatSessionState;


/**
 * Listens for chat session state events.
 * This is the class where you can launch a survey
 * from the chat's onSessionEnded method.
 */
public class MyChatListener implements SessionStateListener {
  private Context mContext;
  private ChatConfiguration mChatConfiguration;

  // Flag to choose whether the survey is a web view or a local UI.
  // TODO: Change this flag depending on whether you want
  // to show the local UI survey or the web view survey...
  private boolean mWebviewSurveyFlag = true;

  /*
    * Bug: listener callback is executed more than once
    * known bug in the lifecycle state is causing this.
    * workaround: make boolean flag that
    * returns extra callbacks to the listener
    *
    * Bug will be fixed in October 2017 release
    */

  private boolean mSessionEnded = false;

  public MyChatListener(ChatConfiguration chatConfiguration, Context context) {
    mChatConfiguration = chatConfiguration;
    mContext = context;
  }

  @Override
  public void onSessionStateChange(ChatSessionState state) {
    if (state == ChatSessionState.Disconnected) {
      // TODO: Handle the disconnected state change
    }
  }

  @Override
  public void onSessionEnded(ChatEndReason endReason) {
    /*
    TODO: In production, you should handle end reasons differently.
    For example:
        if (endReason == ChatEndReason.EndedByAgent) { ...show survey... }
    For this example, we're displaying a survey in all circumstances
    */

    // Test whether onSessionEnded has already been called
    if (mSessionEnded) {
      return;
    }
    mSessionEnded = true;

    // Launch the alert dialog asking if the user wants to complete a survey
    AlertDialog.Builder surveyPrompt = new AlertDialog.Builder(mContext, R.style.AlertDialog);
    surveyPrompt.setTitle(R.string.survey_prompt_title);
    surveyPrompt.setMessage(R.string.survey_prompt_question);
    surveyPrompt.setPositiveButton(R.string.survey_prompt_positive, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Intent intent;
            // Determine whether we should show a web view or a local UI
            // for the survey...
            if (mWebviewSurveyFlag) {

              // You can launch a web view to display
              // a survey from the web. For example:
              intent = new Intent(mContext, WebViewSurveyActivity.class);
            } else {

              // Alternatively, you can launch code that
              // displays a survey with a local UI:
              intent = new Intent(mContext, LocalSurveyActivity.class);

            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("visitor_name", mChatConfiguration.getVisitorName());
            intent.putExtra("button_id", mChatConfiguration.getButtonId());
            intent.putExtra("live_agent_pod", mChatConfiguration.getLiveAgentPod());
            mContext.startActivity(intent);
          }
        }
    );
    surveyPrompt.setNegativeButton(R.string.survey_prompt_negative, null);
    surveyPrompt.show();
  }
}


