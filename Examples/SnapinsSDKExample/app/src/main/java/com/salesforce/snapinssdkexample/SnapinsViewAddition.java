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