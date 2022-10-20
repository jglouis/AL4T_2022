package be.ecam.pattern.structural.decorator;

public class Main {
    public static void main(String[] args) {
        // All birds don't fly
        Penguin pingu = new Penguin();
        Duck donald = new Duck();

        // let's teach donald how to fly
        Bird superDonald = new FlyingBirdDecorator(donald);

        // Now he can fly along the plane
        Plane boeing747 = new Plane();
        Flyer[] flyers = new Flyer[] {(Flyer) superDonald, boeing747};
        for (Flyer flyer : flyers) {
            flyer.fly();
        }
    }
}
