package com.salesforce.snapinssdkexample.utils;

import android.content.Context;

import com.salesforce.android.cases.core.CaseClientCallbacks;
import com.salesforce.android.cases.core.CaseConfiguration;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.ui.ChatUIConfiguration;
import com.salesforce.android.knowledge.core.KnowledgeConfiguration;
import com.salesforce.android.knowledge.ui.KnowledgeUI;
import com.salesforce.android.knowledge.ui.KnowledgeUIConfiguration;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.snapinssdkexample.R;
import com.salesforce.snapinssdkexample.activities.settings.CaseSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.KnowledgeSettingsActivity;
import com.salesforce.snapinssdkexample.auth.MobileSDKAuthTokenProvider;
import com.salesforce.snapinssdkexample.auth.MobileSdkUser;

import static com.salesforce.snapinssdkexample.utils.Utils.getBooleanPref;
import static com.salesforce.snapinssdkexample.utils.Utils.getStringPref;

/**
 * Helper object to encapsulate building configurations of some of the Snap-ins SDKs
 */
public class ServiceSDKUtils {
    /**
     * Creates a chat configuration based on chat settings or uses sensible default values.
     */
    public static ChatConfiguration.Builder getChatConfigurationBuilder(Context context) {
        return new ChatConfiguration.Builder(
                getStringPref(context, ChatSettingsActivity.KEY_ORG_ID,
                        context.getString(R.string.pref_chat_org_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_BUTTON_ID,
                        context.getString(R.string.pref_chat_button_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_DEPLOYMENT_ID,
                        context.getString(R.string.pref_chat_deployment_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_LIVE_AGENT_POD,
                        context.getString(R.string.pref_chat_button_id_default))
        );
    }

    /**
     * Return ChatUIConfigurationBuilder configured with optional ChatBot banner and avatar.
     */
    public static ChatUIConfiguration.Builder getChatUIConfigurationBuilder(Context context, ChatConfiguration chatConfiguration) {
        // Check if the banner and/or avatar are enabled in settings
        final boolean chatbotBannerEnabled = getBooleanPref(context, ChatSettingsActivity.KEY_CHATBOT_BANNER_ENABLED);
        final boolean chatbotAvatarEnabled = getBooleanPref(context, ChatSettingsActivity.KEY_CHATBOT_AVATAR_ENABLED);
        final boolean defaultToMinimized = getBooleanPref(context, ChatSettingsActivity.KEY_DEFAULT_TO_MINIMIZED_ENABLED);

        final ChatUIConfiguration.Builder chatUIConfigurationBuilder = new ChatUIConfiguration.Builder();

        chatUIConfigurationBuilder.chatConfiguration(chatConfiguration);

        if (chatbotBannerEnabled) {
            // Set the ChatBot banner to use via layout ID
            chatUIConfigurationBuilder.enableChatBotBanner(R.layout.chatbot_banner);
        }

        if (chatbotAvatarEnabled) {
            // Set the ChatBot avatar to use via drawable ID
            chatUIConfigurationBuilder.chatBotAvatar(R.drawable.ic_chatbot_avatar);
        }

        if (!defaultToMinimized) {
            chatUIConfigurationBuilder.defaultToMinimized(false);
        }

        return chatUIConfigurationBuilder;
    }

    public static KnowledgeUI getKnowledgeUI(Context context, UserAccount userAccount) {
        // Create a core configuration instance
        KnowledgeConfiguration coreConfiguration = KnowledgeConfiguration.create(
                getStringPref(context, KnowledgeSettingsActivity.KEY_COMMUNITY_URL,
                        context.getString(R.string.pref_case_community_url_default)));

        // If a user account is provided, configure knowledge as an authenticated user
        if (userAccount != null) {
            // Create a KnowledgeConfiguration object using the community URL and current user
            coreConfiguration = KnowledgeConfiguration.builder(
                    getStringPref(context, KnowledgeSettingsActivity.KEY_COMMUNITY_URL,
                            context.getString(R.string.pref_case_community_url_default)))
                    .withAuthConfig(authTokenProvider(userAccount), new MobileSdkUser(userAccount.getUserId()))
                    .build();
        }

        // Create a UI configuration instance from core instance
        KnowledgeUIConfiguration uiConfiguration = KnowledgeUIConfiguration.create(coreConfiguration,
                getStringPref(context, KnowledgeSettingsActivity.KEY_DATA_CATEGORY_GROUP,
                        context.getString(R.string.pref_kb_data_category_group_default)),
                getStringPref(context, KnowledgeSettingsActivity.KEY_ROOT_DATA_CATEGORY,
                        context.getString(R.string.pref_kb_root_data_category_default)));

        return KnowledgeUI.configure(uiConfiguration);
    }

    /**
     * Creates a case configuration based on case settings or uses sensible default values
     */
    public static CaseConfiguration getCaseConfiguration(Context context,
                                                         CaseClientCallbacks caseClientCallbacks,
                                                         UserAccount userAccount) {
        // Create a core configuration instance
        final CaseConfiguration.Builder caseConfigurationBuilder = new CaseConfiguration.Builder(
                getStringPref(context, CaseSettingsActivity.KEY_COMMUNITY_URL,
                        context.getString(R.string.pref_case_community_url_default)),
                getStringPref(context, CaseSettingsActivity.KEY_CREATE_CASE_ACTION_NAME,
                        context.getString(R.string.pref_case_action_name_default_no_auth)));

        // Add case client callbacks
        if (caseClientCallbacks != null)
            caseConfigurationBuilder.callbacks(caseClientCallbacks);

        // If authenticated configure cases with the user account
        if (userAccount != null) {
            caseConfigurationBuilder.caseListName(getStringPref(context,
                    CaseSettingsActivity.KEY_CASE_LIST_NAME,
                    context.getString(R.string.pref_case_list_name_default)));

            caseConfigurationBuilder.withAuthConfig(
                    authTokenProvider(userAccount),
                    new MobileSdkUser(userAccount.getUserId()));

        }

        return caseConfigurationBuilder.build();
    }

    /**
     * Returns a nullable value of the current authenticated user
     */
    public static UserAccount authenticatedUser() {
        return SalesforceSDKManager.getInstance().getUserAccountManager().getCurrentUser();
    }

    /**
     * Returns an MobileSDKAuthTokenProvider for the provided UserAccount.
     */
    private static MobileSDKAuthTokenProvider authTokenProvider(UserAccount user) {
        ClientManager.AccMgrAuthTokenProvider accMgrAuthTokenProvider =
                new ClientManager.AccMgrAuthTokenProvider(
                        SalesforceSDKManager.getInstance().getClientManager(),
                        user.getInstanceServer(),
                        user.getAuthToken(),
                        user.getRefreshToken());

        return new MobileSDKAuthTokenProvider(accMgrAuthTokenProvider, user.getAuthToken());
    }
}
