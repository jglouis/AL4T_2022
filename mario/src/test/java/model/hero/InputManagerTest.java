package model.hero;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InputManagerTest {

    @Test
    public void testMarioMultipleCoins() {
        Mario mario = new Mario(100, 100);

        mario.acquireCoin();
        mario.acquireCoin();
        mario.acquireCoin();

        assertEquals(3, mario.getCoins());
    }
}