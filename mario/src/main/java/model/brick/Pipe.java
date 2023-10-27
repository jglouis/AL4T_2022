package model.brick;

import manager.GameEngine;
import model.prize.Prize;

import java.awt.image.BufferedImage;

public class Pipe extends Brick {

    public Pipe(double x, double y, BufferedImage style) {
        super(x, y, style, false, true);
        setDimension(96, 96);
    }

    @Override
    public Prize reveal(GameEngine engine) {
        return null;
    }
}
