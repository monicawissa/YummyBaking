// Generated code from Butter Knife. Do not modify!
package com.example.moka.yummybaking.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.moka.yummybaking.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepDetailsFragment_ViewBinding implements Unbinder {
  private StepDetailsFragment target;

  @UiThread
  public StepDetailsFragment_ViewBinding(StepDetailsFragment target, View source) {
    this.target = target;

    target.layout = Utils.findRequiredViewAsType(source, R.id.root, "field 'layout'", LinearLayout.class);
    target.f1 = Utils.findRequiredViewAsType(source, R.id.f1, "field 'f1'", FrameLayout.class);
    target.f2 = Utils.findRequiredViewAsType(source, R.id.f2, "field 'f2'", FrameLayout.class);
    target.empty = Utils.findRequiredViewAsType(source, R.id.empty, "field 'empty'", TextView.class);
    target.playerView = Utils.findRequiredViewAsType(source, R.id.playerView, "field 'playerView'", SimpleExoPlayerView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.image, "field 'imageView'", ImageView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description, "field 'description'", TextView.class);
    target.next = Utils.findRequiredViewAsType(source, R.id.next_button, "field 'next'", FloatingActionButton.class);
    target.current = Utils.findRequiredViewAsType(source, R.id.currentStep, "field 'current'", TextView.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back_button, "field 'back'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StepDetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layout = null;
    target.f1 = null;
    target.f2 = null;
    target.empty = null;
    target.playerView = null;
    target.imageView = null;
    target.description = null;
    target.next = null;
    target.current = null;
    target.back = null;
  }
}
