package be.ecam.solid.di_vs_dp.dependency_inversion;

class Car {
    private final Engine engine;
    private final Wheel[] wheels;

    public Car(Engine engine, Wheel[] wheels) {
        this.engine = engine;
        this.wheels = wheels;
    }

    void start() {
        engine.start();
    }
}