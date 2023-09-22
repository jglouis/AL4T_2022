package model.prize;

import manager.SoundManager;
import model.hero.Mario;
import model.hero.MarioForm;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class FireFlower extends BoostItem {
    private static final int POINTS = 150;
    private final ImageLoader imageLoader;

    public FireFlower(double x, double y, BufferedImage style, ImageLoader imageLoader) {
        super(x, y, style);
        this.imageLoader = imageLoader;
        setPoint(POINTS);
    }

    @Override
    public void onTouch(Mario mario, SoundManager sound) {
        mario.acquirePoints(getPoint());
        if (!mario.getMarioForm().isSuper()) {
            transformMarioToFire(mario, sound);
        }
    }

    private void transformMarioToFire(Mario mario, SoundManager sound) {
        MarioForm fireMarioForm = new MarioForm(null, false, false);
        transformMario(mario, fireMarioForm, "superMushroom", sound);

        BufferedImage[] leftFrames = imageLoader.getFrames(fireMarioForm, true);
        BufferedImage[] rightFrames = imageLoader.getFrames(fireMarioForm, false);

        Animation animation = new Animation(leftFrames, rightFrames);
        fireMarioForm.setAnimation(animation);
    }
}
