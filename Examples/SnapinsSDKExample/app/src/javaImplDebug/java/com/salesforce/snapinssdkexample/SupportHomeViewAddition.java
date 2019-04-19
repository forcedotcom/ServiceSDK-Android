package com.salesforce.snapinssdkexample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.salesforce.android.cases.core.CaseClientCallbacks;
import com.salesforce.android.cases.ui.CaseUI;
import com.salesforce.android.cases.ui.CaseUIClient;
import com.salesforce.android.cases.ui.CaseUIConfiguration;
import com.salesforce.android.knowledge.ui.KnowledgeScene;
import com.salesforce.android.knowledge.ui.KnowledgeViewAddition;
import com.salesforce.android.service.common.utilities.control.Async;
import com.salesforce.android.sos.api.Sos;
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * An addition to display Knowledge. Cases, Chat and SOS are launched from here as an example of
 * how these snap-ins can be displayed within an existing application.
 */

public class SupportHomeViewAddition implements KnowledgeViewAddition {
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

        fab.setMenuListener(new SimpleMenuListenerAdapter() {
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

    private Animator getAnimatorSet(View view, Float value) {
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
        ChatLauncher chat = new ChatLauncher();
        chat.launchChat(this.context);
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
