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
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.salesforce.snapinssdkexample.R
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils
import com.salesforce.snapinssdkexample.utils.Utils
import com.salesforce.android.cases.core.CaseClientCallbacks
import com.salesforce.android.cases.ui.CaseUI
import com.salesforce.android.cases.ui.CaseUIConfiguration
import com.salesforce.android.chat.core.model.PreChatField
import com.salesforce.android.chat.ui.ChatUI
import com.salesforce.android.chat.ui.ChatUIClient
import com.salesforce.android.chat.ui.ChatUIConfiguration
import com.salesforce.android.knowledge.ui.KnowledgeScene
import com.salesforce.android.knowledge.ui.KnowledgeViewAddition
import com.salesforce.android.sos.api.Sos
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import java.util.*

/**
 * An addition to display Knowledge. Cases, Chat and SOS are launched from here as an example of
 * how these snap-ins can be displayed within an existing application.
 */
class SupportHomeViewAddition: KnowledgeViewAddition {
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
    // Create a UI configuration instance from a core config object
        val chatConfiguration = ServiceSDKUtils.getChatConfigurationBuilder(context)
            .preChatFields(buildPreChatFields())
            .build()

        // Configure chat session listener
        val serviceSDKApplication = context.applicationContext as ServiceSDKApplication
        val chatListener = serviceSDKApplication.chatSessionListener

        ChatUI.configure(ChatUIConfiguration.create(chatConfiguration))
                .createClient(context)
                .onResult({ _, chatUIClient: ChatUIClient ->
                    run {
                        // Add the configued chat session listener to the Chat UI client
                        chatUIClient.addSessionStateListener(chatListener)
                        // Start the live agent chat session
                        chatUIClient.startChatSession(context as FragmentActivity)
                    }
                })
    }

    /**
     * Configures pre chat fields if prechat is enabled in settings
     */
    private fun buildPreChatFields(): MutableList<PreChatField>? {
        return if (preChatEnabled())
            Utils.asMutableList(
                    PreChatField.Builder()
                            .build(context.getString(R.string.prechat_agent_info_label),
                                    context.getString(R.string.prechat_enter_name_label),
                                    PreChatField.STRING),
                    // A required picklist field
                    PreChatField.Builder()
                            .required(true)
                            .addPickListOption(PreChatField.PickListOption(
                                    String.format(context.getString(R.string.prechat_example_id), 1),
                                    context.getString(R.string.prechat_example_selection_one)))
                            .addPickListOption(PreChatField.PickListOption(
                                    String.format(context.getString(R.string.prechat_example_id), 2),
                                    context.getString(R.string.prechat_example_selection_two)))
                            .build(context.getString(R.string.prechat_agent_info_label),
                                    context.getString(R.string.prechat_selection_label_title),
                                    PreChatField.PICKLIST)
            )
        else Collections.emptyList()
    }

    /**
     * Helper method to determine if pre chat is enabled
     */
    private fun preChatEnabled(): Boolean {
        return Utils.getBooleanPref(
                context, ChatSettingsActivity.KEY_PRECHAT_ENABLED)
    }

    /**
     * Configures and launches SOS
     */
    private fun launchSos(): Boolean {
        // Start an SOS session
        Sos.session(ServiceSDKUtils.getSosOptions(context))
                .configuration(ServiceSDKUtils.getSosConfiguration(context))
                .start(context as Activity)
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
        CaseUI.with(context).uiClient().onResult({ _, caseUIClient -> run {
            // Launch the cases client
            caseUIClient.launch(context)
        }})

        return true
    }
}
