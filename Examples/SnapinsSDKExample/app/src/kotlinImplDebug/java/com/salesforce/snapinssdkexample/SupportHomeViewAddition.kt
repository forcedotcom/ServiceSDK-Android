/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.salesforce.android.cases.core.CaseClientCallbacks
import com.salesforce.android.cases.ui.CaseUI
import com.salesforce.android.cases.ui.CaseUIConfiguration
import com.salesforce.android.knowledge.ui.KnowledgeScene
import com.salesforce.android.knowledge.ui.KnowledgeViewAddition
import com.salesforce.android.sos.api.Sos
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter

/**
 * An addition to display Knowledge. Cases, Chat and SOS are launched from here as an example of
 * how these snap-ins can be displayed within an existing application.
 */
class SupportHomeViewAddition : KnowledgeViewAddition {
    lateinit var context: Context

    /**
     * Creates the view addition.
     */
    override fun createView(viewGroup: ViewGroup, context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.content_support_home, viewGroup, false)
    }

    /**
     * Initializes the view addition and listens for menu activity.
     */
    override fun initView(view: View, visible: Boolean) {
        val fab = view.findViewById<FabSpeedDial>(R.id.support_home_fab)
        fab.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_case -> launchCases()
                    R.id.action_chat -> launchChat()
                    R.id.action_sos -> launchSos()
                }

                return true
            }
        })

        if (!visible) {
            view.scaleX = 0f
            view.scaleY = 0f
        }

        context = view.context
    }

    /**
     * Determines which scenes should have the view addition.
     */
    override fun visibleFor(knowledgeScene: KnowledgeScene): Boolean {
        // Limit visibility of the FAB to the knowledge home page
        return (knowledgeScene == KnowledgeScene.SCENE_HOME)
    }

    /**
     * Returns the entrance animator object.
     */
    override fun getEnterAnimator(view: View): Animator {
        return getAnimatorSet(view, 1f)
    }

    /**
     * Returns the exit animator object.
     */
    override fun getExitAnimator(view: View): Animator {
        return getAnimatorSet(view, 0f)
    }

    /**
     * Helper method to provide an {@link AnimatorSet} for #getEnterAnimtor() and #getExitAnimator()
     */
    fun getAnimatorSet(view: View, value: Float): Animator {
        val set = AnimatorSet()
        set.playTogether(
                ObjectAnimator.ofFloat(view, View.SCALE_X, value),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, value))
        return set
    }

    /**
     * Configures and launches Live Agent Chat
     */
    private fun launchChat() {
        val chat = ChatLauncher()
        chat.launchChat(context)
    }

    /**
     * Configures and launches SOS
     */
    private fun launchSos(): Boolean {
        // Try to start an SOS session, show an alert if any argument is invalid
        try {
            Sos.session(ServiceSDKUtils.getSosOptions(context))
                    .configuration(ServiceSDKUtils.getSosConfiguration(context))
                    .start(context as Activity)
        } catch (e: Exception) {
            showConfigurationErrorAlertDialog(e.message)
        }

        return true
    }

    /**
     * Configures and launches Cases
     */
    private fun launchCases(): Boolean {
        // Create configuration callback function
        val caseClientCallbacks: CaseClientCallbacks = object : CaseClientCallbacks {
            // Populate hidden fields
            override fun getHiddenFields(): MutableMap<String, String> {
                return mutableMapOf("Name__c" to "Jimmy Jester")
            }
        }

        // Create a UI configuration instance from a core instance
        CaseUI.with(context).configure(
                CaseUIConfiguration.create(
                        ServiceSDKUtils.getCaseConfiguration(
                                context,
                                caseClientCallbacks,
                                ServiceSDKUtils.getAuthenticatedUser())))

        // Create a UI client UI asynchronously
        CaseUI.with(context).uiClient().onResult { _, caseUIClient ->
            run {
                // Launch the cases client
                caseUIClient.launch(context)
            }
        }

        return true
    }

    private fun showConfigurationErrorAlertDialog(message: String?) {
        message.let {
            AlertDialog.Builder(context).setPositiveButton(context.getString(R.string.ok), null)
                    .setMessage(context.getString(R.string.config_error_prefix, message))
                    .create().show()
        }
    }
}
