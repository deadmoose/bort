package com.threerings.bort.core;

import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.gl.IndexedTrisShader;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

/**
 * Demonstrates a test case that fails on Intel graphics + IndexedTrisShader:
 * A CanvasImage followed by two other ImageLayers from the same underlying Image results in the
 * latter two layers receiving geometry from the CanvasImage.
 */
public class IntelBugScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Intel Bug";
    }

    @Override
    protected Group createIface ()
    {
        Group group =
            new Group(AxisLayout.vertical(), Style.BACKGROUND.is(Background.solid(Colors.BLACK)));
        group.layer.setShader(new IndexedTrisShader(PlayN.graphics().ctx()));

        CanvasImage canvasImg = PlayN.graphics().createImage(50, 50);
        canvasImg.canvas().setFillColor(Colors.RED).fillRect(0, 0, 50, 50);
        Image img = PlayN.assets().getImageSync("flump/atlas0.png");

        group.layer.add(PlayN.graphics().createImageLayer(canvasImg));
        group.layer.add(PlayN.graphics().createImageLayer(img).setTranslation(50, 50));
        group.layer.add(PlayN.graphics().createImageLayer(img).setTranslation(100, 100));

        return group;
    }
}
