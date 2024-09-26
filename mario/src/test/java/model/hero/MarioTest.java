package model.hero;

import org.junit.jupiter.api.Test;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class MarioTest {

    @Test
    void acquireCoin() {
        Mario hero = new Mario(0,0);
        for(int i=0; i<5;i++) {
            hero.acquireCoin();
        }
        assertEquals(hero.getCoins(), 5);
    }

    @Test
    void acquirePoints(){//acquirePoints(int point){
        Mario hero = new Mario(0,0);
        hero.acquirePoints(10);
        assertEquals(hero.getPoints(),10);
        hero.acquirePoints(5);
        assertEquals(hero.getPoints(),15);
    }

    @Test
    void setRemainingLives(){
        Mario hero = new Mario(0,0);
        hero.setRemainingLives(4);
        assertEquals(hero.getRemainingLives(),4);
    }

    @Test
    void setMarioForm(){
        Mario hero = new Mario(0,0);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        MarioForm marioForm = new MarioForm(animation, false, false);
        MarioForm marioFormFire = new MarioForm(animation, false, true);


        hero.setMarioForm(marioFormFire);
        assertEquals(hero.getMarioForm(),marioFormFire);

        hero.setMarioForm(marioForm);
        assertEquals(hero.getMarioForm(),marioForm);
    }
    @Test
    void isSuper(){
        Mario hero = new Mario(0,0);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        MarioForm marioForm = new MarioForm(animation, false, false);
        MarioForm marioFormSuper = new MarioForm(animation, true, false);
        MarioForm marioFormFire = new MarioForm(animation, false, true);

        hero.setMarioForm(marioFormSuper);
        assertEquals(hero.isSuper(),true);

        hero.setMarioForm(marioForm);
        assertEquals(hero.isSuper(),false);
    }
}
