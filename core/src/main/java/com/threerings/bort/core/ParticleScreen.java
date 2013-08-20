package com.threerings.bort.core;

import java.util.Random;

import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.particle.Emitter;
import tripleplay.particle.Generator;
import tripleplay.particle.Particles;
import tripleplay.particle.effect.Alpha;
import tripleplay.particle.effect.Gravity;
import tripleplay.particle.effect.Move;
import tripleplay.particle.init.Color;
import tripleplay.particle.init.Lifespan;
import tripleplay.particle.init.Transform;
import tripleplay.particle.init.Velocity;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Interpolator;
import tripleplay.util.Randoms;

/**
 * Particles on a layer that gets moved around.
 */
public class ParticleScreen extends BortScreen
{
    @Override
    protected String name ()
    {
        return "Particles";
    }

    @Override
    protected Group createIface ()
    {
        return new Group(AxisLayout.vertical(), Style.BACKGROUND.is(Background.solid(0xFF000000)));
    }

    @Override
    public void showTransitionCompleted ()
    {
        super.showTransitionCompleted();

        _withCircle = PlayN.graphics().createGroupLayer();

        CanvasImage image = PlayN.graphics().createImage(30, 30);
        image.canvas().setFillColor(0xFFFFFF00);
        image.canvas().fillCircle(15, 15, 15);
        _withCircle.add(PlayN.graphics().createImageLayer(image));


        GroupLayer base = PlayN.graphics().createGroupLayer();
        base.add(_withCircle);

        anim.addAt(base, _withCircle, 50, 0).then().repeat(_withCircle).
            tweenX(_withCircle).to(width() - 150).in(2500).easeInOut().then().
            tweenX(_withCircle).to(50).in(2500).easeInOut();

        anim.addAt(_root.layer, base, 0, 0).then().repeat(base).
            tweenY(base).to(height()).in(2500).easeInOut().then().
            tweenY(base).to(0).in(2500).easeInOut();

        createParticles(_particles, _rando);
    }

    protected void createParticles (Particles parts, Randoms rando)
    {
        CanvasImage image = PlayN.graphics().createImage(7, 7);
        image.canvas().setFillColor(0xFFFFFFFF);
        image.canvas().fillCircle(3, 3, 3);

        GroupLayer foo = PlayN.graphics().createGroupLayer();
        foo.add(PlayN.graphics().createImageLayer(image));
        foo.setTranslation(50, 50);

        anim.addAt(_root.layer, foo, 50, 50).then().repeat(foo).
            tweenX(foo).to(150).in(1000).easeInOut().then().
            tweenX(foo).to(50).in(1000).easeInOut();

        Emitter emitter = parts.createEmitter(1000, image, foo);
        emitter.generator = Generator.constant(100);
        emitter.initters.add(Lifespan.constant(5));
        emitter.initters.add(Transform.relativeLayer(_withCircle, foo));
        emitter.initters.add(Color.constant(0xFF99CCFF));
        emitter.initters.add(Velocity.randomSquare(rando, -20, 20, -100, 0));
        emitter.effectors.add(new Gravity(30));
        emitter.effectors.add(new Move());
        emitter.effectors.add(Alpha.byAge(Interpolator.EASE_OUT, 1, 0));
    }

    @Override
    public void paint (Clock clock)
    {
        super.paint(clock);
        _particles.paint(clock);
    }

    protected GroupLayer _withCircle;
    protected final Particles _particles = new Particles();
    protected final Randoms _rando = Randoms.with(new Random());
}
