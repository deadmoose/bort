package com.threerings.bort.core;

import playn.core.Image;
import playn.core.PlayN;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Label;
import tripleplay.ui.Scroller;
import tripleplay.ui.layout.AxisLayout;

public class ScrollerScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Scrollers";
    }

    @Override
    protected Group createIface ()
    {
        Group group = new Group(AxisLayout.horizontal().stretchByDefault());

        Image image = PlayN.assets().getImageSync("flump/atlas0.png");

        group.add(new Scroller(new Label(Icons.image(image))));
        group.add(new Scroller(new Label(Icons.image(image))));
        group.add(new Scroller(new Label(Icons.image(image))));

        return group;
    }

}
