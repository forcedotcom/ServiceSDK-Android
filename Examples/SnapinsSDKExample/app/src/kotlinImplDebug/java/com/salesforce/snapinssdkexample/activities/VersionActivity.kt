/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.salesforce.snapinssdkexample.R

/**
 * Activity to display the versions of the snap-ins SDKs
 */
class VersionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version)

        populateVersionText()
    }

    fun populateVersionText() {
        findViewById<TextView>(R.id.chat_version).text =
            com.salesforce.android.chat.core.BuildConfig.VERSION_NAME
        findViewById<TextView>(R.id.kb_version).text =
            com.salesforce.android.knowledge.core.BuildConfig.VERSION_NAME
        findViewById<TextView>(R.id.cases_version).text =
            com.salesforce.android.cases.BuildConfig.VERSION_NAME
        findViewById<TextView>(R.id.common_version).text =
            com.salesforce.android.service.common.ui.BuildConfig.VERSION_NAME
    }
}
