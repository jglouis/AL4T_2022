package model.prize;

import manager.SoundManager;
import model.GameObject;
import model.hero.MarioForm;
import model.hero.Mario;
import view.ImageLoader;
import view.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BoostItem extends GameObject implements Prize {
    private static final int DIMENSION_X = 48;
    private static final int DIMENSION_Y = 48;

    private boolean revealed = false;
    private int point;

    public BoostItem(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(DIMENSION_X, DIMENSION_Y);
    }

    protected void transformMario(Mario mario, MarioForm form, String soundName, SoundManager sound) {
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getFrames(form, true);
        BufferedImage[] rightFrames = imageLoader.getFrames(form, false);

        Animation animation = new Animation(leftFrames, rightFrames);
        MarioForm newForm = new MarioForm(animation, form.isSuper(), form.isFire());
        mario.setMarioForm(newForm);
        mario.setDimension(48, 96);

        sound.playSound(soundName);
    }

    public abstract void onTouch(Mario mario, SoundManager sound);

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void reveal() {
        setY(getY() - DIMENSION_Y);
        revealed = true;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public void draw(Graphics g){
        if(revealed){
            g.drawImage(getStyle(), (int)getX(), (int)getY(), null);
        }
    }
}