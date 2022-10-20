package be.ecam.pattern.structural.decorator;

import org.jetbrains.annotations.NotNull;

public class FlyingBirdDecorator implements Flyer, Bird {
    private final Bird bird;


    public FlyingBirdDecorator(@NotNull Bird bird) {
        this.bird = bird;
    }

    public void fly() {
        System.out.println("fly, silly bird!");
        bird.poop(); // absolutely inevitable
    }

    @Override
    public void poop() {
        bird.poop();
    }
}
