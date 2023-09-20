package model.prize;

import manager.GameEngine;
import manager.SoundManager;
import model.hero.Mario;

import java.awt.*;

public interface Prize {

    int getPoint();

    void reveal();

    Rectangle getBounds();

    void onTouch(Mario mario, SoundManager sound);

}
