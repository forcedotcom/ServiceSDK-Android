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
 * Activity to display settings for live agent chat.
 */
class ChatSettingsActivity : AppCompatActivity() {
    companion object {
        // String
        val KEY_ORG_ID = "pref_chat_org_id"
        val KEY_DEPLOYMENT_ID = "pref_chat_deployment_id"
        val KEY_BUTTON_ID = "pref_chat_button_id"
        val KEY_LIVE_AGENT_POD = "pref_chat_la_pod"

        // Boolean
        val KEY_PRECHAT_ENABLED = "pref_chat_prechat_enabled"
        val KEY_CHATBOT_BANNER_ENABLED = "pref_chatbot_banner_enabled"
        val KEY_CHATBOT_AVATAR_ENABLED = "pref_chatbot_avatar_enabled"
        val KEY_DEFAULT_TO_MINIMIZED_ENABLED = "pref_default_to_minimized_enabled"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment: PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.prefs_chat_config)
        }
    }
}
