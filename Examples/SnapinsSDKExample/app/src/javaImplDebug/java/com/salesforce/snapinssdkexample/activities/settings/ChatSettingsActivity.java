package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.salesforce.snapinssdkexample.R;


/**
 * Activity to display settings for live agent chat.
 */

public class ChatSettingsActivity extends AppCompatActivity {
    public static final String KEY_ORG_ID = "pref_chat_org_id";
    public static final String KEY_DEVELOPMENT_ID = "pref_chat_deployment_id";
    public static final String KEY_BUTTON_ID = "pref_chat_button_id";
    public static final String KEY_LIVE_AGENT_POD = "pref_chat_la_pod";

    public static final String KEY_PRECHAT_ENABLED = "pref_chat_prechat_enabled";
    public static final String KEY_CHATBOT_BANNER_ENABLED = "pref_chatbot_banner_enabled";
    public static final String KEY_CHATBOT_AVATAR_ENABLED = "pref_chatbot_avatar_enabled";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs_chat_config);
        }
    }
}
