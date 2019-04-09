package com.salesforce.snapinssdkexample;

import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.model.ChatEntity;
import com.salesforce.android.chat.core.model.ChatEntityField;
import com.salesforce.android.chat.core.model.ChatUserData;
import com.salesforce.android.chat.ui.ChatUI;
import com.salesforce.android.chat.ui.ChatUIClient;
import com.salesforce.android.chat.ui.model.PreChatPickListField;
import com.salesforce.android.chat.ui.model.PreChatTextInputField;
import com.salesforce.android.service.common.utilities.control.Async;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils;
import com.salesforce.snapinssdkexample.utils.Utils;

import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import static com.salesforce.snapinssdkexample.utils.Utils.getBooleanPref;

public class ChatLauncher {
    private Context context;
    private List<ChatUserData> chatUserData;
    private List<ChatEntity> chatEntities;

    /**
     * Configures and launches Live Agent Chat
     */
    public void launchChat(final Context context) {
        this.context = context;

        ChatConfiguration chatConfiguration;

        initializePreChatData();

        // Create a UI configuration instance from a core config object
        // Show an alert if any argument is invalid
        try {
            chatConfiguration = ServiceSDKUtils.getChatConfigurationBuilder(context)
                    .chatUserData(chatUserData)
                    .chatEntities(chatEntities)
                    .build();
        } catch (IllegalArgumentException e) {
            showConfigurationErrorAlertDialog(e.getMessage());
            return;
        }

        // Configure chat session listener
        ServiceSDKApplication serviceSDKApplication = (ServiceSDKApplication) context.getApplicationContext();
        final ChatSessionListener chatListener = serviceSDKApplication.getChatSessionListener();

        // Create the chat UI from the ChatUIConfiguration object
        ChatUI.configure(ServiceSDKUtils.getChatUIConfigurationBuilder(context, chatConfiguration).build())
                .createClient(context)
                .onResult(new Async.ResultHandler<ChatUIClient>() {
                    @Override
                    public void handleResult(Async<?> async, @NonNull ChatUIClient chatUIClient) {
                        // Add the configured chat session listener to the Chat UI client
                        chatUIClient.addSessionStateListener(chatListener);
                        // Start the live agent chat session
                        chatUIClient.startChatSession((FragmentActivity) context);
                    }
                });
    }

    /**
     * Configures pre-chat fields if pre-chat is enabled in settings
     */
    private void initializePreChatData() {

        if (!preChatEnabled()) {
            chatUserData = Collections.<ChatUserData>emptyList();
            chatEntities = Collections.<ChatEntity>emptyList();
            return;
        }

        // Create some basic pre-chat fields (with user input)
        PreChatTextInputField firstName = new PreChatTextInputField.Builder()
                .required(true)
                .build("Please enter your first name", "First Name");

        PreChatTextInputField lastName = new PreChatTextInputField.Builder()
                .required(true)
                .build("Please enter your last name", "Last Name");

        PreChatTextInputField email = new PreChatTextInputField.Builder()
                .required(true)
                .inputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .mapToChatTranscriptFieldName("Email__c")
                .build("Please enter your email", "Email Address");

        // Create a pre-chat field without user input
        // (This illustrates a good way to directly send data to your org.)
        ChatUserData subject = new ChatUserData(
                "Hidden Subject Field",
                "Chat case created by sample Android app",
                false);

        // Update chat user data list
        chatUserData = Utils.asMutableList(firstName, lastName, email, subject);

        // Create an entity mapping for a Case record type
        // (All this entity stuff is only required if you
        // want to map transcript fields to other Salesforce records.)
        ChatEntity caseEntity = new ChatEntity.Builder()
                .showOnCreate(true)
                .linkToTranscriptField("Case")
                .addChatEntityField(
                        new ChatEntityField.Builder()
                                .doFind(true)
                                .isExactMatch(true)
                                .doCreate(true)
                                .build("Subject", subject))
                .build("Case");

        // Create an entity mapping for a Contact record type
        ChatEntity contactEntity = new ChatEntity.Builder()
                .showOnCreate(true)
                .linkToTranscriptField("Contact")
                .linkToAnotherSalesforceObject(caseEntity, "ContactId")
                .addChatEntityField(
                        new ChatEntityField.Builder()
                                .doFind(true)
                                .isExactMatch(true)
                                .doCreate(true)
                                .build("FirstName", firstName))
                .addChatEntityField(
                        new ChatEntityField.Builder()
                                .doFind(true)
                                .isExactMatch(true)
                                .doCreate(true)
                                .build("LastName", lastName))
                .addChatEntityField(
                        new ChatEntityField.Builder()
                                .doFind(true)
                                .isExactMatch(true)
                                .doCreate(true)
                                .build("Email", email))
                .build("Contact");

        // Update chat entity mapping list
        // (This is only required if you want to map transcript
        // fields to other Salesforce records.)
        chatEntities = Utils.asMutableList(caseEntity, contactEntity);
    }

    /**
     * Helper method to determine if pre chat is enabled
     */
    private boolean preChatEnabled() {
        return getBooleanPref(
                context, ChatSettingsActivity.KEY_PRECHAT_ENABLED);
    }

    private void showConfigurationErrorAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(context.getString(R.string.ok), null)
                .setMessage(context.getString(R.string.config_error_prefix, message))
                .create();

        dialog.show();
    }
}
