/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample;

import android.app.Application;

/**
 * Application class. Initializes the Snap-ins SDK and the Knowledge interface.
 */
public class SnapinsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Knowledge
    SnapinsLauncher.getInstance().initKnowledge();
  }
}
