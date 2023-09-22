package model.brick;

import model.GameObject;
import model.prize.Prize;
import manager.GameEngine;

import java.awt.image.BufferedImage;

public abstract class Brick extends GameObject{

    private final boolean breakable;
    private final boolean empty;

    public Brick(double x, double y, BufferedImage style, boolean breakable, boolean empty){
        super(x, y, style);
        setDimension(48, 48);
        this.breakable = breakable;
        this.empty = empty;
    }

    public abstract Prize reveal(GameEngine engine);
}
