package com.threerings.bort.core;

import java.util.Random;

import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.particle.Effector;
import tripleplay.particle.Emitter;
import tripleplay.particle.Generator;
import tripleplay.particle.Initializer;
import tripleplay.particle.ParticleBuffer;
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
    protected String title ()
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

        createParticles(_particles, _rando);

        GroupLayer base = PlayN.graphics().createGroupLayer();

        anim.addAt(base, _withCircle, 50, 0).then().repeat(_withCircle).
            tweenX(_withCircle).to(width() - 150).in(1000).easeInOut().then().
            tweenX(_withCircle).to(50).in(1000).easeInOut();

        anim.addAt(_root.layer, base, 0, 0).then().repeat(base).
            tweenY(base).to(height()).in(1000).easeInOut().then().
            tweenY(base).to(0).in(1000).easeInOut();
    }

    protected void createParticles (Particles parts, Randoms rando)
    {
        CanvasImage image = PlayN.graphics().createImage(7, 7);
        image.canvas().setFillColor(0xFFFFFFFF);
        image.canvas().fillCircle(3, 3, 3);

        Emitter emitter = parts.createEmitter(5000, image, _withCircle);
        final LayerTracker tracker = new LayerTracker(emitter.layer);
        emitter.generator = Generator.constant(100);
        emitter.initters.add(Lifespan.constant(5));
        emitter.initters.add(Color.constant(0xFF99CCFF));
        emitter.initters.add(tracker.getInitializer());
        emitter.initters.add(Velocity.randomSquare(rando, -20, 20, -100, 0));
        emitter.effectors.add(new Gravity(30));
        emitter.effectors.add(new Move());
        emitter.effectors.add(tracker.getEffector());
        emitter.effectors.add(Alpha.byAge(Interpolator.EASE_OUT, 1, 0));
    }

    @Override
    public void paint (Clock clock)
    {
        super.paint(clock);
        _particles.paint(clock);
    }

    protected class LayerTracker
    {
        LayerTracker (Layer layer) {
            _layer = layer;
        }

        public Initializer getInitializer () {
            final Initializer init = Transform.layer(_layer);
            return new Initializer () {
                @Override
                public void willInit (int count) {
                    init.willInit(count);
                }

                @Override
                public void init (int index, float[] data, int start) {
                    if (_initted) {
                        init.init(index, data, start);

                    } else {
                        init.init(0, _initialMatrix, -ParticleBuffer.M00);
                        System.arraycopy(_initialMatrix, 0, data, start + ParticleBuffer.M00, 6);
                        System.arraycopy(_currentMatrix, 0, data, start + ParticleBuffer.M00, 6);
                        _initted = true;
                    }
                }
                protected boolean _initted;
            };
        }

        public Effector getEffector () {
            return new Effector() {
                @Override
                public void apply (int index, float[] data, int start, float now, float dt) {
                    if (index == 0) {
                        System.arraycopy(_currentMatrix, 0, _initialMatrix, 0, 6);
                        Initializer init = Transform.layer(_layer);
                        init.willInit(0);
                        init.init(0, _currentMatrix, -ParticleBuffer.M00);
                    }

                    for (int ii = 0; ii < 6; ii++) {
                        data[start + ParticleBuffer.M00 + ii] +=
                            _currentMatrix[ii] - _initialMatrix[ii];
                    }
                }
            };
        }

        final protected Layer _layer;
        final protected float[] _initialMatrix = new float[6];
        final protected float[] _currentMatrix = new float[6];
    }

    protected GroupLayer _withCircle;
    protected final Particles _particles = new Particles();
    protected final Randoms _rando = Randoms.with(new Random());
}
