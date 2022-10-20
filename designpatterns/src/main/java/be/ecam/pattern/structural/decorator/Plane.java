package be.ecam.pattern.structural.decorator;

public class Plane implements Flyer{
    @Override
    public void fly() {
        System.out.println("flying like a plane");
    }
}
