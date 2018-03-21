package com.salesforce.snapinssdkexample.utils;

import android.content.Context;

import com.salesforce.android.cases.core.CaseClientCallbacks;
import com.salesforce.android.cases.core.CaseConfiguration;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.knowledge.core.KnowledgeConfiguration;
import com.salesforce.android.knowledge.ui.KnowledgeUI;
import com.salesforce.android.knowledge.ui.KnowledgeUIConfiguration;
import com.salesforce.android.sos.api.SosAvailability;
import com.salesforce.android.sos.api.SosConfiguration;
import com.salesforce.android.sos.api.SosOptions;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.snapinssdkexample.R;
import com.salesforce.snapinssdkexample.activities.settings.CaseSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.KnowledgeSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.SosSettingsActivity;
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
                getStringPref(context, ChatSettingsActivity.KEY_DEVELOPMENT_ID,
                        context.getString(R.string.pref_chat_deployment_id_default)),
                getStringPref(context, ChatSettingsActivity.KEY_LIVE_AGENT_POD,
                        context.getString(R.string.pref_chat_button_id_default))
        );
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
     * Creates SOS Options based on SOS settings or uses sensible default values.
     */
    public static SosOptions getSosOptions(Context context) {
        // Create an SOS Options Object
        return new SosOptions(
                getStringPref(context, SosSettingsActivity.KEY_POD_URL,
                        context.getString(R.string.pref_sos_pod_url_default)),
                getStringPref(context, SosSettingsActivity.KEY_ORG_ID,
                        context.getString(R.string.pref_sos_org_id_default)),
                getStringPref(context, SosSettingsActivity.KEY_DEPLOY_ID,
                        context.getString(R.string.pref_sos_deployment_id_default))
        );
    }

    /**
     * Creates a SOS configuration based on SOS settings or uses sensible default values.
     */
    public static SosConfiguration getSosConfiguration(Context context) {
        // Create an SOS Configuration
        return new SosConfiguration.Builder()
                .permissionUi(getBooleanPref(context, SosSettingsActivity.KEY_SOS_PERMISSIONS_SETTING))
                .onboardingUi(getBooleanPref(context, SosSettingsActivity.KEY_SOS_ONBOARDING_SETTING))
                .networkTestEnabled(getBooleanPref(context, SosSettingsActivity.KEY_SOS_NETWORK_TEST_SETTING))
                .twoWayVideo(getBooleanPref(context, SosSettingsActivity.KEY_SOS_TWO_WAY_VIDEO_SETTING))
                .fieldServices(getBooleanPref(context, SosSettingsActivity.KEY_SOS_FS_SETTING))
                .audio(getBooleanPref(context, SosSettingsActivity.KEY_SOS_AUDIO_SETTING))
                .build();
    }

    /**
     * Helper method to enable SOS polling based on SOS settings or uses sensible default values.
     */
    public static void startSosPolling(Context context) {
        // Start polling for SOS status update (UNKNOWN, AVAILABLE, or UNAVAILABLE)
        if (!SosAvailability.isPolling()) {
            SosAvailability.startPolling(context,
                    getStringPref(context, SosSettingsActivity.KEY_ORG_ID,
                            context.getString(R.string.pref_sos_org_id_default)),
                    getStringPref(context, SosSettingsActivity.KEY_DEPLOY_ID,
                            context.getString(R.string.pref_sos_deployment_id_default)),
                    getStringPref(context, SosSettingsActivity.KEY_POD_URL,
                            context.getString(R.string.pref_sos_pod_url_default))
            );
        }
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
