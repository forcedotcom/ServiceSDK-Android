package com.salesforce.snapinssdkexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.ChatCore;
import com.salesforce.android.chat.core.model.AvailabilityState;
import com.salesforce.android.knowledge.ui.KnowledgeUI;
import com.salesforce.android.knowledge.ui.KnowledgeUIClient;
import com.salesforce.android.service.common.analytics.ServiceAnalytics;
import com.salesforce.android.service.common.analytics.ServiceAnalyticsListener;
import com.salesforce.android.service.common.utilities.control.Async;
import com.salesforce.android.sos.api.SosAvailability;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.snapinssdkexample.R;
import com.salesforce.snapinssdkexample.SupportHomeViewAddition;
import com.salesforce.snapinssdkexample.activities.settings.CaseSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.KnowledgeSettingsActivity;
import com.salesforce.snapinssdkexample.activities.settings.SosSettingsActivity;
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils;

import java.util.Map;

/**
 * Main test activity supporting basic functionality:
 * <ul>
 * <li>Launching Knowledge UI {@link SupportHomeViewAddition}</li>
 * <li>Authentication (Logging in or out)</li>
 * </ul>
 */
public class MainActivity extends AppCompatActivity implements SosAvailability.Listener {
    private KnowledgeUI mKnowledgeUI;
    private KnowledgeUIClient mKnowledgeUIClient;
    private TextView knowledgeLaunchButton;
    private Button loginButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        knowledgeLaunchButton = findViewById(R.id.knowledge_launch_button);
        loginButton = findViewById(R.id.login_button);
        logoutButton = findViewById(R.id.logout_button);

        setSupportActionBar(toolbar);

        initializeServiceSDK();

        setupButtons();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clean up SOS
        if (SosAvailability.isPolling()) {
            SosAvailability.stopPolling();
            SosAvailability.removeListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sos_settings:
                return startActivityFor(SosSettingsActivity.class);
            case R.id.action_kb_settings:
                return startActivityFor(KnowledgeSettingsActivity.class);
            case R.id.action_chat_settings:
                return startActivityFor(ChatSettingsActivity.class);
            case R.id.action_case_settings:
                return startActivityFor(CaseSettingsActivity.class);
            case R.id.action_version_page:
                return startActivityFor(VersionActivity.class);
            case R.id.action_check_chat_agent_availability:
                return showLiveAgentChatAvailability();
            case R.id.action_check_sos_agent_availability:
                return showSosAgentAvailability();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when the SOS availability status changes
     */
    @Override
    public void onSosAvailabilityChange(SosAvailability.Status status) {
        Toast.makeText(this,
                getString(R.string.sos_agent_availability_change_message) + status.name(),
                Toast.LENGTH_SHORT).show();
    }

    public boolean startActivityFor(Class<? extends AppCompatActivity> activityClass) {
        startActivity(new Intent(getApplicationContext(), activityClass));
        return true;
    }

    private Boolean showLiveAgentChatAvailability() {

        ChatConfiguration chatConfig = ServiceSDKUtils.getChatConfigurationBuilder(getApplicationContext()).build();

        // Create an agent availability client
        ChatCore.configureAgentAvailability(chatConfig).check()
                .onResult(new Async.ResultHandler<AvailabilityState>() {
                    @Override
                    public void handleResult(Async<?> async, @NonNull AvailabilityState availabilityState) {
                        // Display a toast when any agent availability state is changed
                        Toast.makeText(MainActivity.this,
                                String.format(getString(R.string.chat_availability_change_message), availabilityState.getStatus().toString()),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        return true;
    }

    private Boolean showSosAgentAvailability() {
        Toast.makeText(this,
                String.format(getString(R.string.sos_agent_availability_change_message),
                        SosAvailability.getStatus().name()),
                Toast.LENGTH_SHORT).show();

        return true;
    }

    /**
     * Initializes configurations and listeners which should happen at startup.
     */
    private void initializeServiceSDK() {
        setupServiceSDKListeners();

        initKnowledge();
        initSosAvailabilityListener();
    }

    /**
     * Adds listeners for SOS.
     */
    private void initSosAvailabilityListener() {
        SosAvailability.addListener(this);
        ServiceSDKUtils.startSosPolling(getApplicationContext());
    }

    /**
     * An example of how an application can listen to the Service SDK for analytic type events.
     */
    private void setupServiceSDKListeners() {
        // You can listen to user-driven events from the Snap-ins SDK using the ServiceAnalytics system.
        // Implement ServiceAnalyticsListener and add your listener to ServiceAnalytics to start receiving events.
        ServiceAnalytics.addListener(new ServiceAnalyticsListener() {
            @Override
            public void onServiceAnalyticsEvent(String behaviourId, Map<String, Object> eventData) {
                // Left blank intentionally
            }
        });
    }

    /**
     * Adds click listeners to the main activity buttons.
     */
    private void setupButtons() {
        knowledgeLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchKnowledge();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * Initializes Knowledge and adds the view addition.
     */
    private void initKnowledge() {
        if (mKnowledgeUI == null) {
            // Create the KnowledgeUI Configuration
            mKnowledgeUI = ServiceSDKUtils.getKnowledgeUI(
                    getApplicationContext(),
                    ServiceSDKUtils.authenticatedUser());
            // Add the view addition
            mKnowledgeUI.viewAddition(new SupportHomeViewAddition());
        }
    }

    /**
     * Launches the Knowledge View Addition.
     */
    private void launchKnowledge() {
        if (mKnowledgeUI != null) {
            // Create a client asynchronously
            Async<KnowledgeUIClient> client = mKnowledgeUI.createClient(this);

            client.onResult(new Async.ResultHandler<KnowledgeUIClient>() {
                @Override
                public void handleResult(Async<?> async, @NonNull KnowledgeUIClient uiClient) {
                    // Store reference to the Knowledge UI client
                    mKnowledgeUIClient = uiClient;

                    // Handle the close action
                    uiClient.addOnCloseListener(new KnowledgeUIClient.OnCloseListener() {
                        @Override
                        public void onClose() {
                            // Clear the reference to the Knowledge UI client
                            mKnowledgeUIClient = null;
                        }
                    });

                    // Launch the UI
                    uiClient.launchHome(MainActivity.this);
                }
            });
        }
    }

    /**
     * Initiates user login process.
     */
    private void login() {
        SalesforceSDKManager.getInstance()
                .getClientManager()
                .getRestClient(this, new ClientManager.RestClientCallback() {
                    @Override
                    public void authenticatedRestClient(RestClient client) {
                        // left blank intentionally
                    }
                });
    }

    /**
     * Logs out authenticated users.
     */
    private void logout() {
        SalesforceSDKManager.getInstance().logout(this, true);
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
    }

}
