package be.ecam.carfactory.dagger;

import be.ecam.carfactory.CarFlavor;
import be.ecam.carfactory.model.TireCompound;
import be.ecam.carfactory.model.Wheel;
import dagger.Module;
import dagger.Provides;

import javax.inject.Provider;

@Module
public abstract class WheelModule {
    @Provides
    static Wheel[] wheels(Provider<Wheel> wheelProvider) {
        Wheel[] sportsWheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            sportsWheels[i] = wheelProvider.get();
        }
        return sportsWheels;
    }

    @Provides
    static TireCompound tireCompound(CarFlavor flavor) {
        if (flavor == CarFlavor.SPORT_CAR) {
            return TireCompound.RACE;
        }
        return TireCompound.STANDARD;
    }
}
