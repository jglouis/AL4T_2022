package be.ecam.carfactory;

import be.ecam.carfactory.dagger.CarFactory;
import be.ecam.carfactory.dagger.DaggerCarFactory;
import be.ecam.carfactory.model.*;

public class Main {
    public static void main(String[] args) {
        // Create some cars
//        Engine sportEngine = new V8Engine();
//        Wheel[] sportsWheels = new Wheel[4];
//        for (int i = 0; i < 4; i++) {
//            sportsWheels[i] = new Wheel(TireCompound.RACE);
//        }
//
//
//        Car sportCar = new Car(sportsWheels, sportEngine);

        // Sport Car factory
        CarFactory sportCarFactory = DaggerCarFactory.builder()
                .carFlavor(CarFlavor.SPORT_CAR)
                .build();

        Car sportCar = sportCarFactory.car();

        // Electric car factory
        CarFactory electricCaFactory = DaggerCarFactory.builder()
                .carFlavor(CarFlavor.ELECTRIC_CAR)
                .build();
        Car electricCar = electricCaFactory.car();
    }
}