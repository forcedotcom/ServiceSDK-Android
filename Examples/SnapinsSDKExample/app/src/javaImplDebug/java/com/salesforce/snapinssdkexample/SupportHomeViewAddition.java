package com.salesforce.snapinssdkexample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.salesforce.android.cases.core.CaseClientCallbacks;
import com.salesforce.android.cases.ui.CaseUI;
import com.salesforce.android.cases.ui.CaseUIClient;
import com.salesforce.android.cases.ui.CaseUIConfiguration;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.model.PreChatField;
import com.salesforce.android.chat.ui.ChatUI;
import com.salesforce.android.chat.ui.ChatUIClient;
import com.salesforce.android.knowledge.ui.KnowledgeScene;
import com.salesforce.android.knowledge.ui.KnowledgeViewAddition;
import com.salesforce.android.service.common.utilities.control.Async;
import com.salesforce.android.sos.api.Sos;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils;
import com.salesforce.snapinssdkexample.utils.Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

import static com.salesforce.snapinssdkexample.utils.Utils.getBooleanPref;

/**
 * An addition to display Knowledge. Cases, Chat and SOS are launched from here as an example of
 * how these snap-ins can be displayed within an existing application.
 */

public class SupportHomeViewAddition implements KnowledgeViewAddition{
    private Context context;

    /**
     * Creates the view addition.
     */
    @NonNull
    @Override
    public View createView(ViewGroup viewGroup, Context context) {
        return LayoutInflater.from(context).inflate(R.layout.content_support_home, viewGroup, false);
    }

    /**
     * Initializes the view addition and listens for menu activity.
     */
    @Override
    public void initView(View view, boolean visible) {
        final FabSpeedDial fab = view.findViewById(R.id.support_home_fab);

        fab.setMenuListener(new SimpleMenuListenerAdapter(){
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_case:
                        launchCases();
                        break;
                    case R.id.action_chat:
                        launchChat();
                        break;
                    case R.id.action_sos:
                        launchSos();
                        break;
                }
                return true;
            }
        });

        if (!visible) {
            fab.setScaleX(0);
            fab.setScaleY(0);
        }

        context = fab.getContext();

    }


    /**
     * Determines which scenes should have the view addition.
     */
    @Override
    public boolean visibleFor(KnowledgeScene knowledgeScene) {
        return (knowledgeScene == KnowledgeScene.SCENE_HOME);
    }

    /**
     * Returns the entrance animator object.
     */
    @NonNull
    @Override
    public Animator getEnterAnimator(View view) {
        return getAnimatorSet(view, 1f);
    }

    /**
     * Returns the exit animator object.
     */
    @NonNull
    @Override
    public Animator getExitAnimator(View view) {
        return getAnimatorSet(view, 0f);
    }

    private Animator getAnimatorSet(View view, Float value){
        View fab = view.findViewById(R.id.support_home_fab);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(fab, View.SCALE_X, value),
                ObjectAnimator.ofFloat(fab, View.SCALE_Y, value)
        );
        return set;
    }

    /**
     * Configures and launches Live Agent Chat
     */
    private void launchChat() {
        ChatConfiguration chatConfiguration;

        // Create a UI configuration instance from a core config object
        // Show an alert if any argument is invalid
        try {
            chatConfiguration = ServiceSDKUtils.getChatConfigurationBuilder(context)
                    .preChatFields(buildPreChatFields())
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
     * Configures pre chat fields if prechat is enabled in settings
     */
    private List<PreChatField> buildPreChatFields(){
        // Create a pre-chat field for the user's name if it's enabled
        PreChatField preChatField1 = new PreChatField.Builder()
                    .build(context.getString(R.string.prechat_agent_info_label),
                            context.getString(R.string.prechat_enter_name_label),
                            PreChatField.STRING);

        // Create a pre-chat picklist field that has selecting pre-defined values
        PreChatField preChatField2 = new PreChatField.Builder()
                .required(true)
                .addPickListOption(new PreChatField.PickListOption(
                        context.getString(R.string.prechat_example_id) + '1',
                        context.getString(R.string.prechat_example_selection_one)))
                .addPickListOption(new PreChatField.PickListOption(
                        context.getString(R.string.prechat_example_id) + 2,
                        context.getString(R.string.prechat_example_selection_two)))
                .build(context.getString(R.string.prechat_agent_info_label),
                        context.getString(R.string.prechat_selection_label_title),
                        PreChatField.PICKLIST);

        return preChatEnabled()
                ? Utils.asMutableList(preChatField1, preChatField2)
                : Collections.<PreChatField>emptyList();
    }

    /**
     * Helper method to determine if pre chat is enabled
     */
    private boolean preChatEnabled() {
        return getBooleanPref(
                context, ChatSettingsActivity.KEY_PRECHAT_ENABLED);
    }

    /**
     * Configures and launches SOS
     */
    private boolean launchSos() {
        // Try to launch an SOS session, show an alert if any argument is invalid
        try {
            Sos.session(ServiceSDKUtils.getSosOptions(context))
                    .configuration(ServiceSDKUtils.getSosConfiguration(context))
                    .start((Activity) context);
        } catch (IllegalArgumentException e) {
            showConfigurationErrorAlertDialog(e.getMessage());
        }

        return true;
    }

    /**
     * Configures and launches Cases
     */
    private boolean launchCases() {
        // Create configuration callback function
        CaseClientCallbacks caseClientCallbacks = new CaseClientCallbacks() {
            // Populate hidden fields
            @Override
            public Map<String, String> getHiddenFields() {
                Map<String, String> hiddenFields = new HashMap<>();
                hiddenFields.put("Name__c", "Jimmy Jester");
                return hiddenFields;
            }
        };

        // Create a UI configuration instance from a core instance
        CaseUI.with(context).configure(
                CaseUIConfiguration.create(
                        ServiceSDKUtils.getCaseConfiguration(
                                context,
                                caseClientCallbacks,
                                ServiceSDKUtils.authenticatedUser()
                        )
                )
        );

        // Create a UI client UI asynchronously
        CaseUI.with(context).uiClient().onResult(new Async.ResultHandler<CaseUIClient>() {
            @Override
            public void handleResult(Async<?> async, @NonNull CaseUIClient caseUIClient) {
                caseUIClient.launch(context);
            }
        });
        return true;
    }

    private void showConfigurationErrorAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(context.getString(R.string.ok), null)
                .setMessage(context.getString(R.string.config_error_prefix, message))
                .create();

        dialog.show();
    }
}
