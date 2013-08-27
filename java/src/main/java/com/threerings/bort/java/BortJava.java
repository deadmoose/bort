package com.threerings.bort.java;

import playn.core.Image;
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
    JavaTPPlatform jtpp = JavaTPPlatform.register(jp, config);

    Image icon = PlayN.assets().getImageSync("flump/atlas0.png");
    jtpp.setTitle("Bort!");
    jtpp.setIcon(icon);

    PlayN.run(new Bort());
  }
}
