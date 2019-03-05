/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample.activities.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.salesforce.snapinssdkexample.R

/**
 * Activity to display settings for knowledge.
 */
class KnowledgeSettingsActivity : AppCompatActivity() {
    companion object {
        val KEY_COMMUNITY_URL = "pref_kb_community_url"
        val KEY_DATA_CATEGORY_GROUP = "pref_kb_data_category_group"
        val KEY_ROOT_DATA_CATEGORY = "pref_kb_root_data_category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.prefs_knowledge_config)
        }
    }
}
