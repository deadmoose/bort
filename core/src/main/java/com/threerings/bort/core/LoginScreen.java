package com.threerings.bort.core;

import playn.core.Font;
import playn.core.PlayN;
import playn.core.util.Callback;
import tripleplay.flump.Library;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Label;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Style.TextEffect;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.Tabs;
import tripleplay.ui.Widget;
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

        _root.setStylesheet(getBaseStylesheet());

        Group group = new Group(AxisLayout.vertical());

        group.add(new Label(Icons.image(_library.createTexture("Gut").layer().image())));
        group.add(new Label("Username:"));

        return group;
    }

    protected Stylesheet getBaseStylesheet ()
    {
        Font font = PlayN.graphics().createFont("Helvetica", Font.Style.BOLD, 20);

        return SimpleStyles.newSheetBuilder().
            add(Label.class, Style.HALIGN.left, Style.VALIGN.top).
            add(Label.class, Style.COLOR.is(0xFFFFBE00)).
            add(Widget.class, Style.FONT.is(font)).
            add(Label.class, Style.TEXT_EFFECT.is(TextEffect.PIXEL_OUTLINE)).
            add(Label.class, Style.HIGHLIGHT.is(0xFF000000)).
            add(Tabs.class, Tabs.HIGHLIGHTER.is(Tabs.NOOP_HIGHLIGHTER)).
            add(Field.class, Field.FULLTIME_NATIVE_FIELD.is(false)).
            create();
    }

    protected Library _library;
}
