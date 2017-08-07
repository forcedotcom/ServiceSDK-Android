package com.example.salesforce.SnapinsChatSurvey;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.ui.ChatUI;
import com.salesforce.android.chat.ui.ChatUIClient;
import com.salesforce.android.chat.ui.ChatUIConfiguration;
import com.salesforce.android.service.common.utilities.control.Async;

public class LaunchChatActivity extends AppCompatActivity {

  // This value is used to flag any incomplete org settings below.
  // Be sure to replace references to this variable with valid settings.
  private static final String INCOMPLETE_ORG_SETTING = "TODO_UPDATE_THIS_SETTING";

  // TODO: Specify chat configuration information
  // For more help, see the Snap-ins Developer's Guide:
  // https://developer.salesforce.com/docs/atlas.en-us.service_sdk_android.meta/service_sdk_android/servicesdk_android_dev_guide.htm

  private static final String ORG_ID = INCOMPLETE_ORG_SETTING;
  // e.g. "00BC00000003Lqz"
  private static final String LIVE_AGENT_POD = INCOMPLETE_ORG_SETTING;
  // e.g. "d.la.POD_NAME.salesforce.com"
  private static final String DEPLOYMENT_ID = INCOMPLETE_ORG_SETTING;
  // e.g. "0BNW0000000003F"
  private static final String BUTTON_ID = INCOMPLETE_ORG_SETTING;
  // e.g. "357200000009MCq"


  /**
   * Starts a Live Agent Chat session.
   */
  private void startChat() {

    // Check that we have valid org settings
    if (ORG_ID == INCOMPLETE_ORG_SETTING ||
        LIVE_AGENT_POD == INCOMPLETE_ORG_SETTING ||
        DEPLOYMENT_ID == INCOMPLETE_ORG_SETTING ||
        BUTTON_ID == INCOMPLETE_ORG_SETTING) {
      Toast.makeText(LaunchChatActivity.this, "Chat org settings not configured. Update constants in LaunchChatActivity.java.",
          Toast.LENGTH_LONG).show();
      return;
    }

    // Create a chat configuration instance
    final ChatConfiguration chatConfiguration =
        new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
            DEPLOYMENT_ID, LIVE_AGENT_POD)
            .build();
    try {
      // Create a UI configuration instance from a chat config object
      // and start session!
      ChatUI.configure(ChatUIConfiguration.create(chatConfiguration))
          .createClient(this)
          .onResult(
              new Async.ResultHandler<ChatUIClient>() {
                @Override
                public void handleResult(Async<?> operation,
                                         @NonNull final ChatUIClient chatUIClient) {
                  /*
                  Add a chat SessionStateListener to launch activities at the end of a session

                  It is typically not good form to pass a strong reference to an activity,
                  as it may cause a memory leak. However, launching an alertDialog in a
                  listener requires an activity context
                  */

                  chatUIClient.addSessionStateListener
                      (new MyChatListener(chatConfiguration, LaunchChatActivity.this));

                  // Start session
                  chatUIClient.startChatSession(LaunchChatActivity.this);
                }
              }
          );
    } catch (Exception e) {
      Log.d("LaunchChatActivity", e.getLocalizedMessage());
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chat_layout);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    // Handle button tap
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // Start a chat session
        startChat();
      }
    });
  }
}




