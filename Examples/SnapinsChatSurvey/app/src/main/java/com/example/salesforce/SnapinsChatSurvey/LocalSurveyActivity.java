/*
 * Copyright (c) 2018, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. Redistributions in
 * binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Salesforce.com nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.salesforce.SnapinsChatSurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Activity that displays a local survey.
 * This class isn't necessary if you plan to display a web-based survey.
 */
public class LocalSurveyActivity extends AppCompatActivity {
  private Button mSubmitSurveyButton;
  private RatingBar mRatingBar;
  private EditText mAnswerText;
  Bundle mExtras;


  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.survey_view);

    // Create a toolbar
    Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
    setSupportActionBar(myToolbar);
    getSupportActionBar().setTitle(R.string.feedback_label);

    // Show the 'exit_done' button on toolbar
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    myToolbar.setNavigationIcon(R.drawable.ic_exit_done);

    // If the user triggers the navigation icon, quit the activity
    myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    mRatingBar = (RatingBar) findViewById(R.id.rating_bar_q1);
    mAnswerText = (EditText) findViewById(R.id.answer);

    // Make keyboard disappear when rating bar is focused-on
    mRatingBar.setFocusable(true);
    mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        InputMethodManager minimizeKeyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        minimizeKeyboard.hideSoftInputFromWindow(mAnswerText.getWindowToken(), 0);
      }
    });

    mSubmitSurveyButton = (Button) findViewById(R.id.ok_button);
    mExtras = getIntent().getExtras();

    mSubmitSurveyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        // TODO: Do something with all this survey information!
        Toast.makeText(LocalSurveyActivity.this, "This is where you do something with survey information...",
            Toast.LENGTH_LONG).show();

        mRatingBar.getNumStars();
        mAnswerText.getText();
        mExtras.get("visitor_name");
        mExtras.get("button_id");
        mExtras.get("live_agent_pod");

        // Launch a Thank-You activity
        Intent intent = new Intent(LocalSurveyActivity.this, LocalThankYouActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LocalSurveyActivity.this.startActivity(intent);

        // finish the Survey Activity - so the user may not resubmit
        finish();
      }
    });
  }
}

