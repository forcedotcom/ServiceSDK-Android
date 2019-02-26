package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.salesforce.snapinssdkexample.R;

public class CaseSettingsActivity extends AppCompatActivity {
    public static final String KEY_COMMUNITY_URL = "pref_case_community_url";
    public static final String KEY_CREATE_CASE_ACTION_NAME = "pref_case_action_name";
    public static final String KEY_CASE_LIST_NAME = "pref_case_list_name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.prefs_case_config);
        }
    }
}
