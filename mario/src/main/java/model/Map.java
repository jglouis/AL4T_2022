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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Map implements IMap {

    private double remainingTime;
    private Mario mario;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Brick> groundBricks = new ArrayList<>();
    private ArrayList<Prize> revealedPrizes = new ArrayList<>();
    private ArrayList<Brick> revealedBricks = new ArrayList<>();
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private EndFlag endPoint;
    private BufferedImage backgroundImage;
    private double bottomBorder = 720 - 96;
    private String path;


    public Map(double remainingTime, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.remainingTime = remainingTime;
    }


    @Override
    public Mario getMario() {
        return mario;
    }

    @Override
    public void setMario(Mario mario) {
        this.mario = mario;
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    @Override
    public ArrayList<Prize> getRevealedPrizes() {
        return revealedPrizes;
    }

    @Override
    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();

        allBricks.addAll(bricks);
        allBricks.addAll(groundBricks);

        return allBricks;
    }

    @Override
    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    @Override
    public void addGroundBrick(Brick brick) {
        this.groundBricks.add(brick);
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    @Override
    public void drawMap(Graphics2D g2){
        drawBackground(g2);
        drawPrizes(g2);
        drawBricks(g2);
        drawEnemies(g2);
        drawFireballs(g2);
        drawMario(g2);
        endPoint.draw(g2);
    }

    private void drawFireballs(Graphics2D g2) {
        for(Fireball fireball : fireballs){
            fireball.draw(g2);
        }
    }

    private void drawPrizes(Graphics2D g2) {
        for(Prize prize : revealedPrizes){
            if(prize instanceof Coin){
                ((Coin) prize).draw(g2);
            }
            else if(prize instanceof  BoostItem){
                ((BoostItem) prize).draw(g2);
            }
        }
    }

    private void drawBackground(Graphics2D g2){
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawBricks(Graphics2D g2) {
        for(Brick brick : bricks){
            if(brick != null)
                brick.draw(g2);
        }

        for(Brick brick : groundBricks){
            brick.draw(g2);
        }
    }

    private void drawEnemies(Graphics2D g2) {
        for(Enemy enemy : enemies){
            if(enemy != null)
                enemy.draw(g2);
        }
    }

    private void drawMario(Graphics2D g2) {
        mario.draw(g2);
    }

    @Override
    public void updateLocations() {
        mario.updateLocation();
        for(Enemy enemy : enemies){
            enemy.updateLocation();
        }

        for(Iterator<Prize> prizeIterator = revealedPrizes.iterator(); prizeIterator.hasNext();){
            Prize prize = prizeIterator.next();
            if(prize instanceof Coin){
                ((Coin) prize).updateLocation();
                if(((Coin) prize).getRevealBoundary() > ((Coin) prize).getY()){
                    prizeIterator.remove();
                }
            }
            else if(prize instanceof BoostItem){
                ((BoostItem) prize).updateLocation();
            }
        }

        for (Fireball fireball: fireballs) {
            fireball.updateLocation();
        }

        for(Iterator<Brick> brickIterator = revealedBricks.iterator(); brickIterator.hasNext();){
            OrdinaryBrick brick = (OrdinaryBrick)brickIterator.next();
            brick.animate();
            if(brick.getFrames() < 0){
                bricks.remove(brick);
                brickIterator.remove();
            }
        }

        endPoint.updateLocation();
    }

    @Override
    public double getBottomBorder() {
        return bottomBorder;
    }

    @Override
    public void addRevealedPrize(Prize prize) {
        revealedPrizes.add(prize);
    }

    @Override
    public void addFireball(Fireball fireball) {
        fireballs.add(fireball);
    }

    @Override
    public void setEndPoint(EndFlag endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public EndFlag getEndPoint() {
        return endPoint;
    }

    @Override
    public void addRevealedBrick(OrdinaryBrick ordinaryBrick) {
        revealedBricks.add(ordinaryBrick);
    }

    @Override
    public void removeFireball(Fireball object) {
        fireballs.remove(object);
    }

    @Override
    public void removeEnemy(Enemy object) {
        enemies.remove(object);
    }

    @Override
    public void removePrize(Prize object) {
        revealedPrizes.remove(object);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void updateTime(double passed){
        remainingTime = remainingTime - passed;
    }

    @Override
    public boolean isTimeOver(){
        return remainingTime <= 0;
    }

    @Override
    public double getRemainingTime() {
        return remainingTime;
    }
}
