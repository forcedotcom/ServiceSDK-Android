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

package com.salesforce.snapinssdkexample;

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
