package com.salesforce.snapinssdkexample.activities.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.salesforce.snapinssdkexample.R;

/**
 * Activity to display settings for SOS.
 */
public class SosSettingsActivity extends AppCompatActivity {
    public static final String KEY_POD_URL = "pref_sos_pod_url";
    public static final String KEY_ORG_ID = "pref_sos_org_id";
    public static final String KEY_DEPLOY_ID = "pref_sos_deployment_id";

    public static final String KEY_SOS_PERMISSIONS_SETTING = "pref_sos_permission_ui_on";
    public static final String KEY_SOS_ONBOARDING_SETTING = "pref_sos_onboarding_ui_on";
    public static final String KEY_SOS_NETWORK_TEST_SETTING = "pref_sos_network_test_on";
    public static final String KEY_SOS_TWO_WAY_VIDEO_SETTING = "pref_sos_two_way_video_on";
    public static final String KEY_SOS_FS_SETTING = "pref_sos_field_service_on";
    public static final String KEY_SOS_JOIN_SOUND_SETTING = "pref_sos_agent_join_sound_on";
    public static final String KEY_SOS_AUDIO_SETTING = "pref_sos_local_audio_on";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs_sos_config);
        }
    }
}
