package be.ecam.carfactory.model;

public class Car {

    private final Wheel[] wheels;
    private final Engine engine;


    public Car() {
        this.wheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            this.wheels[i] = new Wheel(TireCompound.STANDARD);
        }
        this.engine = new DieselEngine();

    }
}
