package model.prize;

import manager.GameEngine;
import model.hero.Mario;
import model.hero.MarioForm;
import view.Animation;
import view.IImageLoader;

import java.awt.image.BufferedImage;

public class SuperMushroom extends BoostItem{

    public SuperMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(125);
    }

    @Override
    public void onTouch(Mario mario, GameEngine engine) {
        mario.acquirePoints(getPoint());

        IImageLoader imageLoader = engine.getImageLoader();

        if(!mario.getMarioForm().isSuper()){
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SUPER);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SUPER);

            Animation animation = new Animation(leftFrames, rightFrames);
            MarioForm newForm = new MarioForm(animation, true, false, imageLoader);
            mario.setMarioForm(newForm);
            mario.setDimension(48, 96);

            engine.playSuperMushroom();
        }
    }
}
