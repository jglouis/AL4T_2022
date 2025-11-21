package be.ecam.carfactory.model;


import javax.inject.Inject;

public class Car {

    private final Wheel[] wheels;
    private final Engine engine;

    @Inject
    public Car(Wheel[] wheels, Engine engine) {
        this.wheels = wheels;
        this.engine = engine;
    }
}
