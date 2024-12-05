package be.ecam.trafficsim;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTest {

    private final IForwardGo MockGreenTrafficLight = () -> true;
    private final IForwardGo MockRedTrafficLight = () -> false;

    private final ISoundManager mockSoundManager = new ISoundManager() {
        @Override
        public void playContinuousLoop(@NotNull String clipName) {
            // do nothing
        }

        @Override
        public void playOnce(@NotNull String clipName) {
            // do nothing
        }

        @Override
        public void close() {
            // do nothing
        }
    };

    @Test
    public void moveCenterLane() {
        Vehicle.VehicleState vehicleState = Vehicle.VehicleState.MOVE_X;
        Vehicle.VehicleDirection vehicleDirection = Vehicle.VehicleDirection.RIGHT;
        Vehicle vehicle = new Vehicle(6, vehicleState, vehicleDirection, MockGreenTrafficLight, null, 1, mockSoundManager);
        vehicle.move();

        int x = vehicle.getVehiclePosition().x;
        int y = vehicle.getVehiclePosition().y;

        assertEquals(-294, x);
        assertEquals(463, y);
        assertEquals(Vehicle.VehicleState.MOVE_X, vehicle.getState());
    }

    @Test
    public void moveCenterLane_ensureBrakeAtTraffic() {
        Vehicle.VehicleState vehicleState = Vehicle.VehicleState.MOVE_X;
        Vehicle.VehicleDirection vehicleDirection = Vehicle.VehicleDirection.RIGHT;
        Vehicle vehicle = new Vehicle(6, vehicleState, vehicleDirection, MockRedTrafficLight, null, 1, mockSoundManager);
        vehicle.move();

        int x = vehicle.getVehiclePosition().x;
        int y = vehicle.getVehiclePosition().y;

        assertEquals(-294, x);
        assertEquals(463, y);
        assertEquals(Vehicle.VehicleState.BRAKE, vehicle.getState());
    }

}
