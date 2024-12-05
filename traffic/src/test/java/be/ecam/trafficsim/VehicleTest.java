package be.ecam.trafficsim;

import static org.junit.jupiter.api.Assertions.*;
import be.ecam.trafficsim.Vehicle;
import be.ecam.trafficsim.Vehicle.VehicleState;
import be.ecam.trafficsim.Vehicle.VehicleDirection;
import be.ecam.trafficsim.TrafficLight;
import org.junit.jupiter.api.Test;

import java.awt.image.ImageObserver;

class VehicleTest {

    TrafficLight createTrafficLight() {
        int x = 349;
        int y = 147;
        int rotation = 180;
        int signalType = 0;
        int id = 1;
        Vector2 leftPos = new Vector2(349, 147);
        Vector2 forwardPos = new Vector2(349 + 23, 147);
        Vector2 rightPos = new Vector2(349 + 47, 147);
        ImageObserver iObs = null;
        TrafficLight t = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), x, y, rotation, signalType, id, iObs);

        t.setLeftLightPosition(leftPos);
        t.setForwardPosition(forwardPos);
        t.setRightLightPosition(rightPos);

        return t;
    }

    @Test
    void testMove() {
        TrafficLight t = createTrafficLight();
        int baseVelocity = 6;

        Vehicle vehicle = new Vehicle(baseVelocity, VehicleState.MOVE_Y, VehicleDirection.UP, t, null, 1);

        Vector2 oldVehiclePosition = vehicle.getVehiclePosition();

        vehicle.move();

        // Assert the speeds correspond to the right directions
        assertTrue(oldVehiclePosition.y != vehicle.getVehiclePosition().y);
    }

    @Test
    void testTurning(){
        TrafficLight t = createTrafficLight();

        Vehicle vehicle = new Vehicle(6, VehicleState.MOVE_Y, VehicleDirection.LEFT_UP, t, null, 1);

        vehicle.changeCommand(Vehicle.Command.TURN_LEFT);
        vehicle.changeMCurAngle(270);
        vehicle.turning();

        // Assert the turning correspond to the right directions
        assertEquals(VehicleDirection.LEFT, vehicle.getVDirection());
    }

    @Test
    void testBrake() {
        TrafficLight t = createTrafficLight();
        int baseVelocity = 6;

        Vehicle vehicle = new Vehicle(baseVelocity, VehicleState.MOVE_X, VehicleDirection.RIGHT, t, null, 1);

        vehicle.brake();

        // Assert braking reduces speed to 0
        assertTrue(vehicle.getSpeed() < baseVelocity);
    }
}