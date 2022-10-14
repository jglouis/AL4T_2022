package be.ecam.carfactory;

import be.ecam.carfactory.dagger.CarFactory;
import be.ecam.carfactory.dagger.DaggerCarFactory;
import be.ecam.carfactory.model.*;

public class Main {
    public static void main(String[] args) {
        // Create some cars
        Engine sportEngine = new V8Engine();
        Wheel[] sportsWheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            sportsWheels[i] = new Wheel(TireCompound.RACE);
        }


        Car sportCar = new Car(sportsWheels, sportEngine);

        // Car factory
        CarFactory carFactory = DaggerCarFactory.create();
        carFactory.car();
        carFactory.car();
        carFactory.car();
        carFactory.car();
    }
}