package be.ecam.carfactory.model;

import javax.inject.Inject;

public class Wheel {
    private TireCompound tireCompound;

    @Inject
    public Wheel(TireCompound tireCompound) {
        this.tireCompound = tireCompound;
    }
}
