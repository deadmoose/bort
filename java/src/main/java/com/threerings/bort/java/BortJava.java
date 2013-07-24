package com.threerings.bort.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import tripleplay.platform.JavaTPPlatform;

import com.threerings.bort.core.Bort;

public class BortJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    config.width = 1024;
    config.height = 768;

    // use config to customize the Java platform, if needed
    JavaPlatform jp = JavaPlatform.register(config);
    JavaTPPlatform.register(jp, config);
    PlayN.run(new Bort());
  }
}
