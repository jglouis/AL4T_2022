package model.prize;

import manager.SoundManager;
import model.hero.Mario;

import java.awt.image.BufferedImage;

public class OneUpMushroom extends BoostItem {
    private static final int POINTS = 200;

    public OneUpMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(POINTS);
    }

    @Override
    public void onTouch(Mario mario, SoundManager sound) {
        mario.acquirePoints(getPoint());
        mario.setRemainingLives(mario.getRemainingLives() + 1);
        sound.playSound("oneUp");
    }
}
