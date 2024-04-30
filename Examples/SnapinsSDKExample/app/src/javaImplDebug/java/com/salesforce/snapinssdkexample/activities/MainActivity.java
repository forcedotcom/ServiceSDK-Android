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
import com.salesforce.android.service.common.analytics.ServiceAnalytics;
import com.salesforce.android.service.common.analytics.ServiceAnalyticsListener;
import com.salesforce.android.service.common.utilities.control.Async;
import com.salesforce.snapinssdkexample.ChatLauncher;
import com.salesforce.snapinssdkexample.R;
import com.salesforce.snapinssdkexample.activities.settings.ChatSettingsActivity;
import com.salesforce.snapinssdkexample.utils.ServiceSDKUtils;

import java.util.Map;

/**
 * Main test activity supporting basic functionality:
 * <ul>
 * <li>Authentication (Logging in or out)</li>
 * </ul>
 */
public class MainActivity extends AppCompatActivity {
    private ChatLauncher mChatLauncher;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        chatButton = findViewById(R.id.chat_launch_button);

        setSupportActionBar(toolbar);

        initializeServiceSDK();

        setupButtons();
    }

    @Override
    protected void onDestroy() {
        if (mChatLauncher != null) {
            mChatLauncher.endChatSession();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chat_settings:
                return startActivityFor(ChatSettingsActivity.class);
            case R.id.action_version_page:
                return startActivityFor(VersionActivity.class);
            case R.id.action_check_chat_agent_availability:
                return showLiveAgentChatAvailability();
            default:
                return super.onOptionsItemSelected(item);
        }
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

    /**
     * Initializes configurations and listeners which should happen at startup.
     */
    private void initializeServiceSDK() {
        setupServiceSDKListeners();
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
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChat();
            }
        });
    }

    /**
     * Launches Chat.
     */
    private void launchChat() {
        mChatLauncher = new ChatLauncher();
        mChatLauncher.launchChat(this);
    }
}
