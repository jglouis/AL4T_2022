package be.ecam.carfactory.dagger;

import be.ecam.carfactory.CarFlavor;
import be.ecam.carfactory.model.DieselEngine;
import be.ecam.carfactory.model.ElectricEngine;
import be.ecam.carfactory.model.Engine;
import be.ecam.carfactory.model.V8Engine;
import dagger.Module;
import dagger.Provides;

import javax.inject.Provider;

@Module
public abstract class EngineModule {
    @Provides
    static Engine engine(CarFlavor flavor, Provider<ElectricEngine> electricEngineProvider, Provider<V8Engine> v8EngineProvider, Provider<DieselEngine> dieselEngineProvider) {
        Engine engine;
        switch (flavor) {
            case ELECTRIC_CAR -> {
                engine = electricEngineProvider.get();
            }
            case SPORT_CAR -> {
                engine = v8EngineProvider.get();
            }
            case DIESEL_CAR -> {
                engine = dieselEngineProvider.get();
            }
            default -> throw new RuntimeException("Should not happen");
        }
        return engine;
    }
}
