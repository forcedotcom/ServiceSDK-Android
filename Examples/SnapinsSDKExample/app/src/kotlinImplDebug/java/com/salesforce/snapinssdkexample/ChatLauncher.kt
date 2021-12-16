package com.salesforce.snapinssdkexample

import android.app.AlertDialog
import android.content.Context
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.FragmentActivity
import com.salesforce.android.chat.core.ChatConfiguration
import com.salesforce.android.chat.core.model.ChatEntity
import com.salesforce.android.chat.core.model.ChatEntityField
import com.salesforce.android.chat.core.model.ChatUserData
import com.salesforce.android.chat.ui.ChatUI
import com.salesforce.android.chat.ui.ChatUIClient
import com.salesforce.android.chat.ui.model.PreChatTextInputField
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils
import com.salesforce.snapinssdkexample.utils.Utils
import java.util.*

class ChatLauncher {
    lateinit var context: Context

    private var chatUserDataList: MutableList<ChatUserData> = Collections.emptyList()
    private var chatEntitiesList: MutableList<ChatEntity> = Collections.emptyList()
    private var chatClient: ChatUIClient? = null;

    /**
     * Configures and launches Live Agent Chat
     */
    fun launchChat(context: Context) {
        this.context = context

        // Create a UI configuration instance from a core config object
        var chatConfiguration: ChatConfiguration? = null

        initializePreChatData()

        // Try to build a chat configuration, show an alert if any argument is invalid
        try {
            chatConfiguration = ServiceSDKUtils.getChatConfigurationBuilder(context)
                .chatUserData(chatUserDataList)
                .chatEntities(chatEntitiesList)
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
                        chatClient = chatUIClient
                        // Add the configured chat session listener to the Chat UI client
                        chatUIClient.addSessionStateListener(chatListener)
                        // Start the live agent chat session
                        chatUIClient.startChatSession(context as FragmentActivity)
                    }
                }
        }
    }

    /**
     * Configures pre-chat fields if pre-chat is enabled in settings
     */
    private fun initializePreChatData() {

        if (!preChatEnabled()) {
            return
        }

        // Create some basic pre-chat fields (with user input)
        val firstName = PreChatTextInputField.Builder()
            .required(true)
            .build("Please enter your first name", "First Name")

        val lastName = PreChatTextInputField.Builder()
            .required(true)
            .build("Please enter your last name", "Last Name")

        val email = PreChatTextInputField.Builder()
            .required(true)
            .inputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            .mapToChatTranscriptFieldName("Email__c")
            .build("Please enter your email", "Email Address")

        // Create a pre-chat field without user input
        // (This illustrates a good way to directly send data to your org.)
        val subject = ChatUserData(
            "Hidden Subject Field",
            "Chat case created by sample Android Kotlin app",
            false
        )

        // Update chat user data list
        chatUserDataList = Utils.asMutableList(firstName, lastName, email, subject)

        // Create an entity mapping for a Case record type
        // (All this entity stuff is only required if you
        // want to map pre-chat fields to other Salesforce records.)
        val caseEntity = ChatEntity.Builder()
            .showOnCreate(true)
            .linkToTranscriptField("Case")
            .addChatEntityField(
                ChatEntityField.Builder()
                    .doFind(true)
                    .isExactMatch(true)
                    .doCreate(true)
                    .build("Subject", subject)
            )
            .build("Case")

        // Create an entity mapping for a Contact record type
        val contactEntity = ChatEntity.Builder()
            .showOnCreate(true)
            .linkToTranscriptField("Contact")
            .linkToAnotherSalesforceObject(caseEntity, "ContactId")
            .addChatEntityField(
                ChatEntityField.Builder()
                    .doFind(true)
                    .isExactMatch(true)
                    .doCreate(true)
                    .build("FirstName", firstName)
            )
            .addChatEntityField(
                ChatEntityField.Builder()
                    .doFind(true)
                    .isExactMatch(true)
                    .doCreate(true)
                    .build("LastName", lastName)
            )
            .addChatEntityField(
                ChatEntityField.Builder()
                    .doFind(true)
                    .isExactMatch(true)
                    .doCreate(true)
                    .build("Email", email)
            )
            .build("Contact")

        // Update chat entity mapping list
        // (This is only required if you want to map pre-chat
        // fields to other Salesforce records.)
        chatEntitiesList = Utils.asMutableList(caseEntity, contactEntity)
    }

    /**
     * Helper method to determine if pre chat is enabled
     */
    private fun preChatEnabled(): Boolean {
        return Utils.getBooleanPref(
            context, ChatSettingsActivity.KEY_PRECHAT_ENABLED
        )
    }

    private fun showConfigurationErrorAlertDialog(message: String?) {
        message.let {
            AlertDialog.Builder(context).setPositiveButton(context.getString(R.string.ok), null)
                .setMessage(context.getString(R.string.config_error_prefix, message))
                .create().show()
        }
    }

}
