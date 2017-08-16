package com.example.salesforce.SnapinsChatSurvey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Activity that displays a web view to the survey.
 * This class isn't necessary if you plan to display a local UI survey.
 */
public class WebViewSurveyActivity extends AppCompatActivity {

  Bundle mExtras;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_web_view);

    // Create the toolbar
    Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
    setSupportActionBar(myToolbar);
    getSupportActionBar().setTitle(R.string.feedback_label);

    // Show the 'exit_done' button on toolbar
    getSupportActionBar().setDisplayHomeAsUpEnabled(true) ;
    myToolbar.setNavigationIcon(R.drawable.ic_exit_done);

    // When the user triggers the navigation icon, quit the activity
    myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    WebView webView = (WebView) findViewById(R.id.web_view);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient());

    // configuration information, potentially to connect a chat session and a survey session later
    mExtras = getIntent().getExtras();

    // TODO: Replace this URL with your real survey URL...
    // URL to the dummy survey
    webView.loadUrl("https://qtrial2017q3az1.az1.qualtrics.com/jfe/form/SV_6mwOzsCqPhHH2eN");

    // TODO: Do something with all this configuration information!
    mExtras.get("visitor_name");
    mExtras.get("button_id");
    mExtras.get("live_agent_pod");
    Toast.makeText(WebViewSurveyActivity.this, "This is where you do something with the configuration information...",
        Toast.LENGTH_LONG).show();
  }

}

