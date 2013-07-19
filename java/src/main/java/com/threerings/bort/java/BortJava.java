package com.threerings.bort.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.threerings.bort.core.Bort;

public class BortJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new Bort());
  }
}
