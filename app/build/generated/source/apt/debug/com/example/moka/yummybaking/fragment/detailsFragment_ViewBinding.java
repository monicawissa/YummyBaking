// Generated code from Butter Knife. Do not modify!
package com.example.moka.yummybaking.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.moka.yummybaking.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class detailsFragment_ViewBinding implements Unbinder {
  private detailsFragment target;

  @UiThread
  public detailsFragment_ViewBinding(detailsFragment target, View source) {
    this.target = target;

    target.steprecycle = Utils.findRequiredViewAsType(source, R.id.stepsList, "field 'steprecycle'", RecyclerView.class);
    target.ingredientrecycle = Utils.findRequiredViewAsType(source, R.id.ingredientList, "field 'ingredientrecycle'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    detailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.steprecycle = null;
    target.ingredientrecycle = null;
  }
}
