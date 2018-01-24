/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.example.salesforce.SnapinsChatSurvey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity that thanks the user after they complete the local survey.
 * This class isn't necessary if you plan to display a web-based survey.
 */
public class LocalThankYouActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_thank_you);

    // Create a toolbar
    Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
    setSupportActionBar(myToolbar);
    getSupportActionBar().setTitle(R.string.feedback_label);

    // Show the 'exit_done' button on toolbar
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    myToolbar.setNavigationIcon(R.drawable.ic_exit_done);

    // When the user triggers the navigation icon, quit the activity
    myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}