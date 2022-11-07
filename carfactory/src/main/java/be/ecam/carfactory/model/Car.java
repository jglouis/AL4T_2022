package be.ecam.carfactory.model;

public class Car {

    private final Wheel[] wheels;
    private final Engine engine;


    public Car(Engine engine, Wheel[] wheels) {
        this.wheels = wheels;
        this.engine = engine;

    }
}
