package model.prize;

import static org.junit.Assert.*;

import org.junit.Test;

public class Unit_test {
    @Test
    public void coinValue() {
    		// Arrange
    		int value = 10;
    		Coin coin = new Coin(10, 15, null, value);
    		// Act
    		int points = coin.getPoint();
    		// Assert
    		assertEquals(value, coin.getPoint());
    }
}