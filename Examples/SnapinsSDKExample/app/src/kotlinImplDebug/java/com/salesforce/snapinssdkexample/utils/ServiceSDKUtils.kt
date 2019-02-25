/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample.utils

import android.content.Context
import com.salesforce.snapinssdkexample.R
import com.salesforce.snapinssdkexample.activities.settings.CaseSettingsActivity
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity
import com.salesforce.snapinssdkexample.activities.settings.KnowledgeSettingsActivity
import com.salesforce.snapinssdkexample.activities.settings.SosSettingsActivity
import com.salesforce.snapinssdkexample.utils.Utils.getBooleanPref
import com.salesforce.snapinssdkexample.utils.Utils.getStringPref
import com.salesforce.android.cases.core.CaseClientCallbacks
import com.salesforce.android.cases.core.CaseConfiguration
import com.salesforce.android.chat.core.ChatConfiguration
import com.salesforce.android.chat.ui.ChatUIConfiguration
import com.salesforce.android.knowledge.core.KnowledgeConfiguration
import com.salesforce.android.knowledge.ui.KnowledgeUI
import com.salesforce.android.knowledge.ui.KnowledgeUIConfiguration
import com.salesforce.android.sos.api.SosAvailability
import com.salesforce.android.sos.api.SosConfiguration
import com.salesforce.android.sos.api.SosOptions
import com.salesforce.androidsdk.accounts.UserAccount
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.salesforce.androidsdk.rest.ClientManager
import com.salesforce.snapinssdkexample.auth.MobileSDKAuthTokenProvider
import com.salesforce.snapinssdkexample.auth.MobileSdkUser

/**
 * Helper object to encapsulate building configurations of some of the Snap-ins SDKs
 */
object ServiceSDKUtils {
    /**
     * Creates a chat configuration based on chat settings or uses sensible default values.
     */
    fun getChatConfigurationBuilder(context: Context): ChatConfiguration.Builder {
        // Create a core configuration instance
        return ChatConfiguration.Builder(
                getStringPref(context, ChatSettingsActivity.KEY_ORG_ID,
                        context.getString(R.string.pref_chat_org_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_BUTTON_ID,
                        context.getString(R.string.pref_chat_button_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_DEPLOYMENT_ID,
                        context.getString(R.string.pref_chat_deployment_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_LIVE_AGENT_POD,
                        context.getString(R.string.pref_chat_la_pod_default))
        )
    }

    /**
     * Return ChatUIConfigurationBuilder configured with optional ChatBot banner and avatar.
     */
    fun getChatUIConfigurationBuilder(context: Context, chatConfiguration: ChatConfiguration): ChatUIConfiguration.Builder {
        // Check if the banner and/or avatar are enabled in settings
        val chatbotBannerEnabled = getBooleanPref(context, ChatSettingsActivity.KEY_CHATBOT_BANNER_ENABLED)
        val chatbotAvatarEnabled = getBooleanPref(context, ChatSettingsActivity.KEY_CHATBOT_AVATAR_ENABLED)
        val defaultToMinimized = getBooleanPref(context, ChatSettingsActivity.KEY_DEFAULT_TO_MINIMIZED_ENABLED)

        val chatUIConfigurationBuilder: ChatUIConfiguration.Builder = ChatUIConfiguration.Builder().chatConfiguration(chatConfiguration)

        if (chatbotBannerEnabled) {
            // Set the ChatBot banner to use via layout ID
            chatUIConfigurationBuilder.enableChatBotBanner(R.layout.chatbot_banner)
        }

        if (chatbotAvatarEnabled) {
            // Set the ChatBot avatar to use via drawable ID
            chatUIConfigurationBuilder.chatBotAvatar(R.drawable.ic_chatbot_avatar)
        }

        if (!defaultToMinimized) {
            chatUIConfigurationBuilder.defaultToMinimized(false)
        }

        return chatUIConfigurationBuilder
    }

    /**
     * Creates a knowledge ui configuration based on knowledge settings or uses sensible default values.
     */
    fun getKnowledgeUI(context: Context, userAccount: UserAccount?): KnowledgeUI {
        // Create a core configuration instance
        var coreConfiguration = KnowledgeConfiguration.create(
                getStringPref(context, KnowledgeSettingsActivity.KEY_COMMUNITY_URL,
                        context.getString(R.string.pref_case_community_url_default)))

        // If a user account is provided, configure knowledge as an authenticated user
        userAccount?.let { user ->
            // Create a KnowledgeConfiguration object using the community URL and current user
            coreConfiguration = KnowledgeConfiguration.builder(
                getStringPref(context, KnowledgeSettingsActivity.KEY_COMMUNITY_URL,
                context.getString(R.string.pref_case_community_url_default)))
                    .withAuthConfig(getAuthTokenProvider(user), MobileSdkUser(user.userId))
                    .build()
        }

        // Create a UI configuration instance from core instance
        val uiConfiguration = KnowledgeUIConfiguration.create(coreConfiguration,
                getStringPref(context, KnowledgeSettingsActivity.KEY_DATA_CATEGORY_GROUP,
                        context.getString(R.string.pref_kb_data_category_group_default)),
                getStringPref(context, KnowledgeSettingsActivity.KEY_ROOT_DATA_CATEGORY,
                        context.getString(R.string.pref_kb_root_data_category_default)))

        // Create a UI instance
        return KnowledgeUI.configure(uiConfiguration)
    }

    /**
     * Creates SOS Options based on SOS settings or uses sensible default values.
     */
    fun getSosOptions(context: Context): SosOptions {
        // Create an Sos Options object
        return SosOptions(
                getStringPref(context, SosSettingsActivity.KEY_POD_URL,
                        context.getString(R.string.pref_sos_pod_url_default)),
                getStringPref(context, SosSettingsActivity.KEY_ORG_ID,
                        context.getString(R.string.pref_sos_org_id_default)),
                getStringPref(context, SosSettingsActivity.KEY_DEPLOY_ID,
                        context.getString(R.string.pref_sos_deployment_id_default))
        )
    }

    /**
     * Creates a SOS configuration based on SOS settings or uses sensible default values.
     */
    fun getSosConfiguration(context: Context): SosConfiguration {
        // Create an SOS configuration
        return SosConfiguration.Builder()
                .permissionUi(getBooleanPref(context, SosSettingsActivity.KEY_SOS_PERMISSIONS_SETTING))
                .onboardingUi(getBooleanPref(context, SosSettingsActivity.KEY_SOS_ONBOARDING_SETTING))
                .networkTestEnabled(getBooleanPref(context, SosSettingsActivity.KEY_SOS_NETWORK_TEST_SETTING))
                .twoWayVideo(getBooleanPref(context, SosSettingsActivity.KEY_SOS_TWO_WAY_VIDEO_SETTING))
                .fieldServices(getBooleanPref(context, SosSettingsActivity.KEY_SOS_FS_SETTING))
//                .agentJoinSound() // Optional: define a sound for agent join event
                .audio(getBooleanPref(context, SosSettingsActivity.KEY_SOS_AUDIO_SETTING))
                .build()
    }

    /**
     * Helper method to enable SOS polling based on SOS settings or uses sensible default values.
     */
    fun startSosPolling(context: Context) {
        // Start polling for SOS status updates (UNKNOWN, AVAILABLE, or UNAVAILABLE)
        if (!SosAvailability.isPolling())
            SosAvailability.startPolling(context,
                    getStringPref(context, SosSettingsActivity.KEY_ORG_ID,
                            context.getString(R.string.pref_sos_org_id_default)),
                    getStringPref(context, SosSettingsActivity.KEY_DEPLOY_ID,
                            context.getString(R.string.pref_sos_deployment_id_default)),
                    getStringPref(context, SosSettingsActivity.KEY_POD_URL,
                            context.getString(R.string.pref_sos_pod_url_default)))
    }

    /**
     * Creates a case configuration based on case settings or uses sensible default values
      */
    fun getCaseConfiguration(context: Context,
                             caseClientCallbacks: CaseClientCallbacks?,
                             userAccount: UserAccount?): CaseConfiguration {

        // Create a core configuration instance
        val caseConfigurationBuilder = CaseConfiguration.Builder(
                getStringPref(context, CaseSettingsActivity.KEY_COMMUNITY_URL,
                        context.getString(R.string.pref_case_community_url_default)),
                getStringPref(context, CaseSettingsActivity.KEY_CREATE_CASE_ACTION_NAME,
                        context.getString(R.string.pref_case_action_name_default_no_auth)))

        // Add case client callbacks
        caseClientCallbacks.let { caseConfigurationBuilder.callbacks(caseClientCallbacks) }

        // If authenticated configure cases with the user account
        userAccount?.let { user ->
            caseConfigurationBuilder.caseListName(getStringPref(context,
                    CaseSettingsActivity.KEY_CASE_LIST_NAME,
                    context.getString(R.string.pref_case_list_name_default)))

            caseConfigurationBuilder.withAuthConfig(
                    getAuthTokenProvider(user),
                    MobileSdkUser(user.userId))
        }

        return caseConfigurationBuilder.build()
    }

    /**
     * Returns a nullable value of the current authenticated user
     */
    fun getAuthenticatedUser(): UserAccount? {
        return SalesforceSDKManager.getInstance().userAccountManager.currentUser
    }

    /**
     * Returns an MobileSDKAuthTokenProvider for the provided UserAccount.
     */
    private fun getAuthTokenProvider(user: UserAccount): MobileSDKAuthTokenProvider {
        val accMgrAuthTokenProvider = ClientManager.AccMgrAuthTokenProvider(
                SalesforceSDKManager.getInstance().clientManager,
                user.instanceServer,
                user.authToken,
                user.refreshToken)

        return MobileSDKAuthTokenProvider(accMgrAuthTokenProvider, user.authToken)
    }
}
