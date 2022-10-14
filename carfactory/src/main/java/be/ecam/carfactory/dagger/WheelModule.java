package be.ecam.carfactory.dagger;

import be.ecam.carfactory.CarFlavor;
import be.ecam.carfactory.model.TireCompound;
import be.ecam.carfactory.model.Wheel;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class WheelModule {
    @Provides
    static Wheel[] wheels(CarFlavor carFlavor) {
        TireCompound tireCompound;
        switch (carFlavor) {
            case SPORT_CAR ->
                tireCompound = TireCompound.RACE;

            default -> tireCompound= TireCompound.STANDARD;
        }

        Wheel[] sportsWheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            sportsWheels[i] = new Wheel(tireCompound);
        }
        return sportsWheels;
    }
}
