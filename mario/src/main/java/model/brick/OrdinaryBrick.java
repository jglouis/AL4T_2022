package model.brick;

import model.prize.Prize;
import view.Animation;
import manager.MapManager;
import manager.GameEngine;

import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Brick {

    //private final Animation animation;
    private boolean breaking;
    private int frames;

    public OrdinaryBrick(double x, double y, BufferedImage style) {
        super(x, y, style, true, true);
        //this.animation = animation;
        this.breaking = false;
        //this.frames = animation.getLeftFrames().length;
    }

    @Override
    public Prize reveal(GameEngine engine) {
        MapManager manager = engine.getMapManager();
        if(!manager.getMario().isSuper())
            return null;

        breaking = true;
        manager.addRevealedBrick(this);

        double newX = getX() - 27, newY = getY() - 27;
        setLocation(newX, newY);

        return null;
    }

    public void animate() {
        if (breaking) {
            //setStyle(animation.animate(3, true));
            frames--;
        }
    }

    public int getFrames() {
        return frames;
    }
}
