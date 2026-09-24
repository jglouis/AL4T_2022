package model;

import org.junit.Test;
import static org.junit.Assert.*;

import model.brick.OrdinaryBrick;

public class Unit_test {
	@Test
	public void objectMove() {
		// Arrange
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setVelX(2);
		// Act
		obj.updateLocation();
		double x = obj.getX();
		// Assert
		assertEquals(2, x, 0.1);
	}

	@Test
	public void objectFall() {
		// Arrange
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setGravityAcc(1);
		obj.setFalling(true);
		// Act
		obj.updateLocation();
		obj.updateLocation();
		double y = obj.getY();
		// Assert
		assertEquals(1, y, 0.1);
	}
}