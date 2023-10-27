package model.prize;

import model.hero.Mario;
import manager.SoundManager;

import java.awt.*;

public interface Prize {
    int getPoint();
    void reveal();
    Rectangle getBounds();
    void onTouch(Mario mario, SoundManager sound);
}