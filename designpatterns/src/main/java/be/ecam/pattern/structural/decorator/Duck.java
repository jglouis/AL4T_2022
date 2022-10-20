package be.ecam.pattern.structural.decorator;

public class Duck implements Bird{
    @Override
    public void poop() {
        System.out.println("duck is pooping");
    }
}
