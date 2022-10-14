package be.ecam.carfactory.dagger;

import be.ecam.carfactory.model.TireCompound;
import be.ecam.carfactory.model.Wheel;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class WheelModule {
    @Provides
    static Wheel[] wheels() {
        Wheel[] sportsWheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            sportsWheels[i] = new Wheel(TireCompound.RACE);
        }
        return sportsWheels;
    }
}
