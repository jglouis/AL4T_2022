package be.ecam.carfactory;

import be.ecam.carfactory.dagger.CarFactory;
import be.ecam.carfactory.dagger.DaggerCarFactory;
import be.ecam.carfactory.model.*;

public class Main {
    public static void main(String[] args) {
        // Sport Car factory
        CarFactory sportCarFactory = DaggerCarFactory.builder()
                .carFlavor(CarFlavor.SPORT_CAR)
                .build();

        Car sportCar = sportCarFactory.car();

        // Electric car factory
        CarFactory electricCraFactory = DaggerCarFactory.builder()
                .carFlavor(CarFlavor.ELECTRIC_CAR)
                .build();
        Car electricCar = electricCraFactory.car();
    }
}