package com.salesforce.snapinssdkexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.salesforce.snapinssdkexample.R;

public class SnapinsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Get the floating action button
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    // Handle button tap
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // Start the Snap-ins experience
        SnapinsLauncher.getInstance().startSnapins(SnapinsActivity.this);
      }
    });
  }

}
