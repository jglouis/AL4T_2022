package be.ecam.solid.di_vs_dp.naive;

class Car {
    private final Engine engine;
    private final Wheel[] wheels = new Wheel[4];

    Car() {
        this.engine = new Engine();
        for (int i = 0; i < 4; i++) {
            wheels[i] = new Wheel();
        }
    }

    void start() {
        engine.start();
    }
}