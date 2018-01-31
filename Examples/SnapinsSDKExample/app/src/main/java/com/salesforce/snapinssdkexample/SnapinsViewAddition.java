/*
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.snapinssdkexample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.salesforce.snapinssdkexample.R;
import com.salesforce.android.knowledge.ui.KnowledgeScene;
import com.salesforce.android.knowledge.ui.KnowledgeViewAddition;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

import static com.salesforce.android.knowledge.ui.KnowledgeScene.SCENE_HOME;

/**
 * KnowledgeViewAddition implementation that uses the FabSpeedDial library
 * to create actionable menu items for Cases, Chat, and SOS.
 */
public class SnapinsViewAddition implements KnowledgeViewAddition {

  /**
   * Creates the view addition.
   */
  @NonNull
  @Override
  public View createView(ViewGroup viewGroup, Context context) {
    return LayoutInflater.from(context).inflate(R.layout.content_support_home, viewGroup, false);
  }

  /**
   * Initializes the view addition and listens for menu activity.
   */
  @Override
  public void initView(final View view, boolean visible) {

    FabSpeedDial fabView = (FabSpeedDial) view.findViewById(R.id.support_home_fab);
    fabView.setMenuListener(new SimpleMenuListenerAdapter() {

      @Override
      public boolean onPrepareMenu(NavigationMenu navigationMenu) {
        // TO DO: Do something with yout menu items, or return false if you don't want to show them
        return true;
      }

      @Override
      public boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
          case (R.id.action_case):
            // Start Cases
            SnapinsLauncher.getInstance().startCases(view.getContext());
            break;
          case (R.id.action_chat):
            // Start Chat
            SnapinsLauncher.getInstance().startChat(view.getContext());
            break;
          case (R.id.action_sos):
            // Start SOS
            SnapinsLauncher.getInstance().startSOS(view.getContext());
            break;
          default:
            Log.w("Snapins Example", "Unknown item selected...");
            break;
        }

        return true;
      }
    });

    if (!visible) {
      view.setScaleX(0);
      view.setScaleY(0);
    }
  }

  /**
   * Determines which scenes should have the view addition.
   */
  @Override
  public boolean visibleFor(KnowledgeScene knowledgeScene) {
    // Only interested in adding this FAB to the Knowledge HOME page...
    return (knowledgeScene == SCENE_HOME);
  }

  /**
   * Returns the entrance animator object.
   */
  @NonNull
  @Override
  public Animator getEnterAnimator(View view) {
    AnimatorSet set = new AnimatorSet();
    set.playTogether(
            ObjectAnimator.ofFloat(view, View.SCALE_X, 1),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1)
    );
    return set;
  }

  /**
   * Returns the exit animator object.
   */
  @NonNull
  @Override
  public Animator getExitAnimator(View view) {
    AnimatorSet set = new AnimatorSet();
    set.playTogether(
            ObjectAnimator.ofFloat(view, View.SCALE_X, 0),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 0)
    );
    return set;
  }
}