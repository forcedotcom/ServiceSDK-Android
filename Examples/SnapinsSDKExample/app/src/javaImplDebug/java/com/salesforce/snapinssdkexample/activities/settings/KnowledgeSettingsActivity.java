package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.salesforce.snapinssdkexample.R;

public class KnowledgeSettingsActivity extends AppCompatActivity {
    public static final String KEY_COMMUNITY_URL = "pref_kb_community_url";
    public static final String KEY_DATA_CATEGORY_GROUP = "pref_kb_data_category_group";
    public static final String KEY_ROOT_DATA_CATEGORY = "pref_kb_root_data_category";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.prefs_knowledge_config);
        }
    }
}
