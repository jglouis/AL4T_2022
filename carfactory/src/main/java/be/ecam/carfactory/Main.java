package be.ecam.carfactory;

import be.ecam.carfactory.model.*;

public class Main {
    public static void main(String[] args) {
        // Step 1: Creating the dependencies (in order)
        Wheel[] wheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            wheels[i] = new Wheel(TireCompound.STANDARD);
        }
        Engine engine = new DieselEngine();

        // Step 2: Injecting the dependencies into the consumer
        Car car = new Car(engine, wheels);
    }
}