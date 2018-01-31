/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample.utils

import android.content.Context
import android.preference.PreferenceManager

object Utils {
    fun<T> asMutableList(vararg t: T): MutableList<T>? {
        val result = ArrayList<T>()
        result += t
        return result
    }

    fun getStringPref(context: Context, key: String, default: String): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, default)
    }

    fun getBooleanPref(context: Context, key:String): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }
}