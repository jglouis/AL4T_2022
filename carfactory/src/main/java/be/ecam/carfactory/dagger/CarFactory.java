package be.ecam.carfactory.dagger;

import be.ecam.carfactory.model.Car;
import dagger.Component;

@Component(modules = {WheelModule.class, EngineModule.class})
public interface CarFactory {
    Car car();
}
