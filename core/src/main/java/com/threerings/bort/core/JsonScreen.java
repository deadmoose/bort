package com.threerings.bort.core;

import playn.core.PlayN;

import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

public class JsonScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Json";
    }

    @Override
    protected Group createIface ()
    {
        Group group = new Group(AxisLayout.vertical());

        try {
            String str = PlayN.assets().getTextSync("flump/library.json");
            for (int ii = 0; ii < 10; ii++) {
                long start = System.currentTimeMillis();
                PlayN.json().parse(str);
                long end = System.currentTimeMillis();
                group.add(new Label("Parsed in: " + (end - start)/1000f));
            }
        } catch (Exception e) {
            group.add(new Label("Oh crud: " + e.getMessage()));
        }

        return group;
    }
}
