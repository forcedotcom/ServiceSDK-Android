/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample.activities.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import com.salesforce.snapinssdkexample.R

/**
 * Activity to display settings for SOS.
 */
class SosSettingsActivity : AppCompatActivity() {
    companion object {
        val KEY_POD_URL = "pref_sos_pod_url"
        val KEY_ORG_ID = "pref_sos_org_id"
        val KEY_DEPLOY_ID = "pref_sos_deployment_id"

        val KEY_SOS_PERMISSIONS_SETTING = "pref_sos_permission_ui_on"
        val KEY_SOS_ONBOARDING_SETTING = "pref_sos_onboarding_ui_on"
        val KEY_SOS_NETWORK_TEST_SETTING = "pref_sos_network_test_on"
        val KEY_SOS_TWO_WAY_VIDEO_SETTING = "pref_sos_two_way_video_on"
        val KEY_SOS_FS_SETTING = "pref_sos_field_service_on"
        val KEY_SOS_JOIN_SOUND_SETTING = "pref_sos_agent_join_sound_on"
        val KEY_SOS_AUDIO_SETTING = "pref_sos_local_audio_on"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment: PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.prefs_sos_config)
        }
    }
}
