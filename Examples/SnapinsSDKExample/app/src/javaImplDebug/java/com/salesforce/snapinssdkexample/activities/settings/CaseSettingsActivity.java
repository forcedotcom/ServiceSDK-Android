package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.salesforce.snapinssdkexample.R;

public class CaseSettingsActivity extends AppCompatActivity {
    public static final String KEY_COMMUNITY_URL = "pref_case_community_url";
    public static final String KEY_CREATE_CASE_ACTION_NAME = "pref_case_action_name";
    public static final String KEY_CASE_LIST_NAME = "pref_case_list_name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs_case_config);
        }
    }
}
