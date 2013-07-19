package com.threerings.bort.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.threerings.bort.core.Bort;

public class BortActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Bort());
  }
}
