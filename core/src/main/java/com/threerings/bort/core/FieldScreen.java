package com.threerings.bort.core;

import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.layout.AxisLayout;

public class FieldScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Fields";
    }

    @Override
    protected Group createIface ()
    {
        Group group = new Group(AxisLayout.vertical().offStretch());

        //group.add(new Field());
        group.add(new Field("Foo"));
        //group.add(new Field("Bar"));

        return group;
    }

}
