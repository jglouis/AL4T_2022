package model.brick;

import manager.GameEngine;
import model.prize.Prize;

import java.awt.image.BufferedImage;

public class GroundBrick extends Brick {

    public GroundBrick(double x, double y, BufferedImage style) {
        super(x, y, style, false, true);
    }

    @Override
    public Prize reveal(GameEngine engine) {
        return null;
    }
}
