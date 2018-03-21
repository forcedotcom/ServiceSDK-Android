package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.salesforce.snapinssdkexample.R;

public class KnowledgeSettingsActivity extends AppCompatActivity {
    public static final String KEY_COMMUNITY_URL = "pref_kb_community_url";
    public static final String KEY_DATA_CATEGORY_GROUP = "pref_kb_data_category_group";
    public static final String KEY_ROOT_DATA_CATEGORY = "pref_kb_root_data_category";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs_knowledge_config);
        }
    }
}
