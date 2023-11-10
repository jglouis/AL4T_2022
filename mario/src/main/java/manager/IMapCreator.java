package manager;

import model.EndFlag;
import model.IMap;
import model.brick.*;
import model.enemy.Enemy;
import model.enemy.Goomba;
import model.enemy.KoopaTroopa;
import model.hero.Mario;
import model.prize.*;
import view.IImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IMapCreator {
    void setMapCreator(IImageLoader imageLoader);
    IMap createMap(String mapPath, double timeLimit) ;

    }
