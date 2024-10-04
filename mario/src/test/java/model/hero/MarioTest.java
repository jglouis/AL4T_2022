package model.hero;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarioTest {

    @Test
    public void lifeMethodTest() {
        Mario test1 = new Mario(1,2);
        test1.setRemainingLives(2);
        int value = test1.getRemainingLives();
        assertEquals(2,value);
    }

    @Test
    public void acquireCoinTest() {
        Mario test2 = new Mario(1,2);
        test2.acquireCoin();
        int value = test2.getCoins();
        assertEquals(1,value);
    }

    @Test
    public void acquirePointTest() {
        Mario test3 = new Mario(1,2);
        test3.acquirePoints(10);
        int value = test3.getPoints();
        assertEquals(10,value);
    }

}