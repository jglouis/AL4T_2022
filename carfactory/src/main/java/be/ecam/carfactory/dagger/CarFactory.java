package be.ecam.carfactory.dagger;

import be.ecam.carfactory.CarFlavor;
import be.ecam.carfactory.model.Car;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {WheelModule.class, EngineModule.class})
public interface CarFactory {
    Car car();

    @Component.Builder
    interface Builder {
        CarFactory build();
        @BindsInstance Builder carFlavor(CarFlavor carFlavor);
    }
}
