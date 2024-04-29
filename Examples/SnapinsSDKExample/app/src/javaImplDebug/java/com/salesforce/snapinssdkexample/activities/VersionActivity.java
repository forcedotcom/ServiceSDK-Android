package com.salesforce.snapinssdkexample.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.salesforce.android.chat.core.BuildConfig;
import com.salesforce.snapinssdkexample.R;

/**
 * Activity to display the versions of the snap-ins SDKs
 */
public class VersionActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_version);

        populateVersionText();
    }

    private void populateVersionText() {
        ((TextView) findViewById(R.id.chat_version)).setText(BuildConfig.VERSION_NAME);
        ((TextView) findViewById(R.id.common_version)).setText(com.salesforce.android.service.common.ui.BuildConfig.VERSION_NAME);
    }
}
