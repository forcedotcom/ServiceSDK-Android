package com.salesforce.snapinssdkexample.utils;

import android.content.Context;

import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.ui.ChatUIConfiguration;
import com.salesforce.snapinssdkexample.R;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;

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
}
