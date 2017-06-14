package com.example.salesforce.snapinssdkexample;

import android.app.Application;

import com.salesforce.androidsdk.analytics.security.Encryptor;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.smartstore.app.SmartStoreSDKManager;

/**
 * Application class. Initializes the Snap-ins SDK and the Knowledge interface.
 *
 * Be sure to update TODO item regarding encryption.
 */
public class SnapinsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize the Salesforce SDK
    SmartStoreSDKManager.initNative(this,
            new SalesforceSDKManager.KeyInterface() {
              @Override
              public String getKey(String name) {

                // TODO: Generate and return a key for SmartStore here.
                // WARNING: WE HIGHLY RECOMMEND NOT HARDCODING THIS
                // IN YOUR APP!!! THIS IS USED ONLY AS AN EXAMPLE!!!
                // Refer to the Salesforce Mobile SDK for more info.
                // https://developer.salesforce.com/docs/atlas.en-us.mobile_sdk.meta/mobile_sdk/offline_intro.htm
                return Encryptor.hash(name +
                        "12s9adpahk;n12-97sdainkasd=012", name +
                        "12kl0dsakj4-cxh1qewkjasdol8");
              }
            }, SnapinsActivity.class);

    // Initialize Knowledge
    SnapinsLauncher.getInstance().initKnowledge();
  }

}
