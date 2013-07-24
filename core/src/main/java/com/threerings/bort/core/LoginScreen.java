package com.threerings.bort.core;

import playn.core.PlayN;
import playn.core.util.Callback;
import tripleplay.flump.Library;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

/**
 * Stripped down bits from a login panel that exhibits severely broken behavior on some graphics
 * cards.
 */
public class LoginScreen extends BortScreen
{
    public LoginScreen ()
    {
        // We're going to play fast & loose and just load the flump and assume it's done by
        // the time anyone cares about it. Hooray fragile test bed!

        Library.fromAssets("flump", new Callback<Library>() {
            @Override
            public void onSuccess (Library result) {
                _library = result;
            }

            @Override
            public void onFailure (Throwable cause) {
                PlayN.log().error("Couldn't load flump", cause);
            }

        });
    }

    @Override
    protected String name ()
    {
        return "Login";
    }

    @Override
    protected Group createIface ()
    {
        if (_library == null) {
            PlayN.log().error("Tried to use LoginScreen before flump finished loading");
            return null;
        }

        Group group = new Group(AxisLayout.vertical());

        group.add(new Label(Icons.image(_library.createTexture("Gut").layer().image())));
        group.add(new Label("Username:"));


        return group;
    }

    protected Library _library;
}
