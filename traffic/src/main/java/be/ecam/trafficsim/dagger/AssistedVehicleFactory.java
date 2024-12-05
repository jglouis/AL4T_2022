package be.ecam.trafficsim.dagger;

import be.ecam.trafficsim.IForwardGo;
import be.ecam.trafficsim.Vehicle;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface AssistedVehicleFactory {
    Vehicle create(
            @Assisted("velocity") int vel,
            Vehicle.VehicleState vehState,
            Vehicle.VehicleDirection vDir,
            IForwardGo tl,
            @Assisted("vAhead") Vehicle vAhead,
            @Assisted("index") int index);
}
