package model;

import model.brick.Brick;
import model.brick.OrdinaryBrick;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Coin;
import model.prize.Prize;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public interface IMap {
    public Mario getMario();

    public void setMario(Mario mario);

    public ArrayList<Enemy> getEnemies();

    public ArrayList<Fireball> getFireballs();

    public ArrayList<Prize> getRevealedPrizes();

    public ArrayList<Brick> getAllBricks();

    public void addBrick(Brick brick);

    public void addGroundBrick(Brick brick);

    public void addEnemy(Enemy enemy);

    public void drawMap(Graphics2D g2);



    public void updateLocations();

    public double getBottomBorder();

    public void addRevealedPrize(Prize prize);

    public void addFireball(Fireball fireball);

    public void setEndPoint(EndFlag endPoint);

    public EndFlag getEndPoint();

    public void addRevealedBrick(OrdinaryBrick ordinaryBrick);

    public void removeFireball(Fireball object);

    public void removeEnemy(Enemy object);

    public void removePrize(Prize object);

    public String getPath();

    public void setPath(String path);

    public void updateTime(double passed);

    public boolean isTimeOver();

    public double getRemainingTime();
}
