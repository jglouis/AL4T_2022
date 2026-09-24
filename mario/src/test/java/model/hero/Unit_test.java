package model.hero;
import static org.junit.Assert.*;

import org.junit.Test;

import view.Animation;
import view.ImageLoader;
public class Unit_test {
    /*Arrange*/
    int point = 10;
    Animation animation;
    MarioForm marioFormSuper = new MarioForm(animation,true,false);
    MarioForm marioFormFire = new MarioForm(animation,false,true);
    @Test
    public void acquireCoin() {
        Mario test = new Mario(1,2, new ImageLoader());
        /*Act*/
        test.acquireCoin();
        int value = test.getCoins();
        /*Assert*/
        assertEquals(1,value);
    }


    @Test
    public void acquirePoints() {
        Mario test = new Mario(1,2, new ImageLoader());
        test.acquirePoints(point);
        int value = test.getPoints();
        assertEquals(10,value);
    }

    @Test
    public void GetMarioFormissuper() {
        Mario test = new Mario(1,2, new ImageLoader());
        test.setMarioForm(marioFormSuper);
        boolean value = marioFormSuper.isSuper();
        assertTrue(value);
    }
    @Test
    public void GetMarioFormisfire() {
        Mario test = new Mario(1,2, new ImageLoader());
        test.setMarioForm(marioFormFire);
        boolean value = marioFormFire.isFire();
        assertTrue(value);
    }
    @Test
    public void isSuper(){
        Mario test = new Mario(1,2, new ImageLoader());
        boolean value = test.isSuper();
        assertFalse(value);
    }




}

