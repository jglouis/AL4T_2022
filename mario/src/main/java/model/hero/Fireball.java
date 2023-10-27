package model.hero;

import model.GameObject;
import java.awt.image.BufferedImage;

public class Fireball extends GameObject {
    private boolean toRight;
    private FireballImpl fireballImpl;

    public Fireball(double x, double y, BufferedImage style, boolean toRight) {
        super(x, y, style);
        setDimension(24, 24);
        setFalling(false);
        setJumping(false);
        this.toRight = toRight;
    }

    public void moveFireball() {
        if (fireballImpl != null) {
            fireballImpl.moveFireball(this);
        }
    }

    public void setFireballImpl(FireballImpl fireballImpl) {
        this.fireballImpl = fireballImpl;
    }

    public boolean isToRight() {
        return toRight;
    }
}
