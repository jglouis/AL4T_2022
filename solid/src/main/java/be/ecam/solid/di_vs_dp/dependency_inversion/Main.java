package be.ecam.solid.di_vs_dp.dependency_inversion;

class Main {
    public static void main(String[] args) {
        Engine myEngine = new V8Engine();
        Wheel[] myWheels = new Wheel[4];
        for (int i = 0; i < 4; i++) {
            myWheels[i] = new SportWheel();
        }
        Car myCar = new Car(myEngine, myWheels);
        myCar.start();
    }
}
