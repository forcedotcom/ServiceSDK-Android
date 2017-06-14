package com.example.salesforce.snapinssdkexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SnapinsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Pass along this instance to our Snap-ins launcher instance
    SnapinsLauncher.getInstance().setActivity(this);

    // Get the floating action button
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    // Handle button tap
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // Start the Knowledge UI
        Log.i("Snapins Example","Starting Knowledge...");
        SnapinsLauncher.getInstance().startKnowledge();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
