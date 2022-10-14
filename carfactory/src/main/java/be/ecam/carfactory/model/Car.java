package be.ecam.carfactory.model;

import be.ecam.carfactory.data.DataStore;

import javax.inject.Inject;

public class Car {

    private final Wheel[] wheels;
    private final Engine engine;
    private final DataStore dataStore;

    @Inject
    public Car(Wheel[] wheels, Engine engine, DataStore dataStore) {
        this.wheels = wheels;
        this.engine = engine;
        this.dataStore = dataStore;
    }
}
