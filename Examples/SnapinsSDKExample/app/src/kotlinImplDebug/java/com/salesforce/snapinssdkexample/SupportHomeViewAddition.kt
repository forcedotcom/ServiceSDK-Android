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
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils
import com.salesforce.snapinssdkexample.utils.Utils
import com.salesforce.android.cases.core.CaseClientCallbacks
import com.salesforce.android.cases.ui.CaseUI
import com.salesforce.android.cases.ui.CaseUIConfiguration
import com.salesforce.android.chat.core.ChatConfiguration
import com.salesforce.android.chat.core.model.ChatUserData
import com.salesforce.android.chat.ui.ChatUI
import com.salesforce.android.chat.ui.ChatUIClient
import com.salesforce.android.chat.ui.model.PreChatPickListField
import com.salesforce.android.chat.ui.model.PreChatTextInputField
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
        var chatConfiguration: ChatConfiguration? = null

        // Try to build a chat configuration, show an alert if any argument is invalid
        try {
            chatConfiguration = ServiceSDKUtils.getChatConfigurationBuilder(context)
                    .chatUserData(buildPreChatFields())
                    .build()
        } catch (e: IllegalArgumentException) {
            showConfigurationErrorAlertDialog(e.message)
        }

        // Configure chat session listener
        val serviceSDKApplication = context.applicationContext as ServiceSDKApplication
        val chatListener = serviceSDKApplication.chatSessionListener

        chatConfiguration?.let {
            ChatUI.configure(ServiceSDKUtils.getChatUIConfigurationBuilder(context, it).build())
                    .createClient(context)
                    .onResult { _, chatUIClient: ChatUIClient ->
                        run {
                            // Add the configued chat session listener to the Chat UI client
                            chatUIClient.addSessionStateListener(chatListener)
                            // Start the live agent chat session
                            chatUIClient.startChatSession(context as FragmentActivity)
                        }
                    }
        }
    }

    /**
     * Configures pre chat fields if prechat is enabled in settings
     */
    private fun buildPreChatFields(): MutableList<ChatUserData> {
        return if (preChatEnabled())
            Utils.asMutableList(
                    PreChatTextInputField.Builder()
                            .build(context.getString(R.string.prechat_agent_info_label),
                                    context.getString(R.string.prechat_enter_name_label)),
                    // A required picklist field
                    PreChatPickListField.Builder()
                            .required(true)
                            .addOption(PreChatPickListField.Option(
                                    context.getString(R.string.prechat_example_selection_one),
                                    context.getString(R.string.prechat_example_id_one)))
                            .addOption(PreChatPickListField.Option(
                                    context.getString(R.string.prechat_example_selection_two),
                                    context.getString(R.string.prechat_example_id_two)))
                            .build(context.getString(R.string.prechat_agent_info_label),
                                    context.getString(R.string.prechat_selection_label_title))
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
        CaseUI.with(context).uiClient().onResult { _, caseUIClient -> run {
            // Launch the cases client
            caseUIClient.launch(context)
        }}

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
