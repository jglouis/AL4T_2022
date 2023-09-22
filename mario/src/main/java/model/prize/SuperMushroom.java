package model.prize;

import manager.SoundManager;
import model.hero.Mario;
import model.hero.MarioForm;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class SuperMushroom extends BoostItem {

    private static final int POINTS = 125;
    private final ImageLoader imageLoader;

    public SuperMushroom(double x, double y, BufferedImage style, ImageLoader imageLoader) {
        super(x, y, style);
        this.imageLoader = imageLoader;
        setPoint(POINTS);
    }

    @Override
    public void onTouch(Mario mario, SoundManager sound) {
        mario.acquirePoints(getPoint());
        if (!mario.getMarioForm().isSuper()) {
            transformMarioToSuper(mario, sound);
        }
    }

    private void transformMarioToSuper(Mario mario, SoundManager sound) {
        MarioForm superMarioForm = new MarioForm(null, false, false);
        transformMario(mario, superMarioForm, "superMushroom", sound);

        BufferedImage[] leftFrames = imageLoader.getFrames(superMarioForm, true);
        BufferedImage[] rightFrames = imageLoader.getFrames(superMarioForm, false);

        Animation animation = new Animation(leftFrames, rightFrames);
        superMarioForm.setAnimation(animation);
    }
}
