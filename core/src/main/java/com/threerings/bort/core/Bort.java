package com.threerings.bort.core;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

public class Bort extends Game.Default {
    /** Args from the Java bootstrap class. */
    public static String[] mainArgs = {};

    public static final int UPDATE_RATE = 50;

    public Bort () {
        super(UPDATE_RATE);
    }

    @Override public void init () {
        _screens.push(new UIScreen() {

        });
    }

    @Override public void update (int delta) {
        _clock.update(delta);
        _screens.update(delta);
    }

    @Override public void paint (float alpha) {
        _clock.paint(alpha);
        _screens.paint(_clock);
    }

    protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);

    protected final ScreenStack _screens = new ScreenStack() {
        @Override protected void handleError (RuntimeException error) {
            PlayN.log().warn("Screen failure", error);
        }
        @Override protected Transition defaultPushTransition () {
            return slide();
        }
        @Override protected Transition defaultPopTransition () {
            return slide().right();
        }
    };
}
