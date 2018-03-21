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
 * Activity to display settings for cases.
 */
class CaseSettingsActivity : AppCompatActivity() {
    companion object {
        val KEY_COMMUNITY_URL = "pref_case_community_url"
        val KEY_CREATE_CASE_ACTION_NAME = "pref_case_action_name"
        val KEY_CASE_LIST_NAME = "pref_case_list_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment: PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.prefs_case_config)
        }
    }
}
