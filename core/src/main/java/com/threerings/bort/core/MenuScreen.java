package com.threerings.bort.core;

import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

public class MenuScreen extends UIScreen
{
    public MenuScreen (ScreenStack stack)
    {
        _stack = stack;
        _screens = new BortScreen[] {
            new ParticleScreen(),
            new LoginScreen(),
            new TintScreen(),
            new IntelBugScreen(),
        };
    }

    @Override
    public void wasShown ()
    {
        super.wasShown();
        _root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
        _root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).
                                            inset(5, 10)));
        _root.setSize(width(), height());

        for (final BortScreen screen : _screens) {
            _root.add(new Button(screen.name()).onClick(new UnitSlot() {
                @Override
                public void onEmit () {
                    _stack.push(screen);
                    screen.back.onClick(new UnitSlot() {
                        @Override
                        public void onEmit () {
                            _stack.remove(screen);
                        }
                    });
                }
            }));
        }
    }

    @Override public void wasHidden () {
        super.wasHidden();
        iface.destroyRoot(_root);
    }

    protected final ScreenStack _stack;
    protected final BortScreen[] _screens;
    protected Root _root;
}
