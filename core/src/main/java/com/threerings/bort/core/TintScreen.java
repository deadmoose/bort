package com.threerings.bort.core;

import playn.core.Color;
import playn.core.Image;
import playn.core.PlayN;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

public class TintScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Tint";
    }

    @Override
    protected Group createIface ()
    {
        Group group =
            new Group(AxisLayout.vertical(), Style.BACKGROUND.is(Background.solid(Colors.BLACK)));

        Image img = PlayN.assets().getImageSync("flump/atlas0.png");

        group.layer.add(PlayN.graphics().createImageLayer(img));
        group.layer.add(PlayN.graphics().createImageLayer(img).
            setTint(Colors.RED).setTranslation(50, 50));
        group.layer.add(PlayN.graphics().createImageLayer(img).
            setTint(Color.argb(128, 0, 255, 0)).setTranslation(100, 100));

        return group;
    }
}
