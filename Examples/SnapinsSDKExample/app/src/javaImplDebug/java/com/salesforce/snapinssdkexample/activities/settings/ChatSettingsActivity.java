package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.salesforce.snapinssdkexample.R;


/**
 * Activity to display settings for live agent chat.
 */

public class ChatSettingsActivity extends AppCompatActivity {
    public static final String KEY_ORG_ID = "pref_chat_org_id";
    public static final String KEY_DEPLOYMENT_ID = "pref_chat_deployment_id";
    public static final String KEY_BUTTON_ID = "pref_chat_button_id";
    public static final String KEY_LIVE_AGENT_POD = "pref_chat_la_pod";

    public static final String KEY_PRECHAT_ENABLED = "pref_chat_prechat_enabled";
    public static final String KEY_CHATBOT_BANNER_ENABLED = "pref_chatbot_banner_enabled";
    public static final String KEY_CHATBOT_AVATAR_ENABLED = "pref_chatbot_avatar_enabled";
    public static final String KEY_DEFAULT_TO_MINIMIZED_ENABLED = "pref_default_to_minimized_enabled";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.prefs_chat_config);
        }
    }
}
