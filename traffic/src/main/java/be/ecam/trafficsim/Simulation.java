package be.ecam.trafficsim;

import be.ecam.trafficsim.ui.VehicleDrawable;
import org.jetbrains.annotations.NotNull;
import be.ecam.trafficsim.Vehicle.VehicleDirection;
import be.ecam.trafficsim.Vehicle.VehicleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


public class Simulation extends JPanel implements ActionListener {
    private Image mTerrain;
    private final Timer tm = new Timer(1, this);

    private final Random random = new Random();
    //Arrays of vehicles in each direction
    private final ArrayList<VehicleDrawable> vehiclesDrawableRight = new ArrayList<>();
    private final ArrayList<VehicleDrawable> vehiclesDrawableDown = new ArrayList<>();
    private final ArrayList<VehicleDrawable> vehiclesDrawableLeft = new ArrayList<>();
    private final ArrayList<VehicleDrawable> vehiclesDrawableUp = new ArrayList<>();

    private final String[] carImages = {"/car1.png", "/car2.png", "/car3.png", "/car4.png",
            "/ambulance.png", "/police.png", "/truck1.png", "/truck2.png"};

    private final ArrayList<TrafficLight> trafficLights;
    //timer regulating the rate new cars are created
    private int carSpawnTimer = 0;

    private enum Direction {
        LEFT, UP, RIGHT, DOWN
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(mTerrain, 0, 0, this);

        for (ArrayList<VehicleDrawable> list : Arrays.asList(
                vehiclesDrawableRight, vehiclesDrawableLeft, vehiclesDrawableDown, vehiclesDrawableUp))
            for (int i = 0; i < list.size(); i++) {
                VehicleDrawable v = list.get(i);
                if (v.isInView())
                    g2D.drawImage(v.getImage(), v.getTrans(), this);
                else {
                    list.remove(v);
                }
            }

        //Draw trafficLights
        for (TrafficLight t : trafficLights) {
            //Draw the lights
            Color[] colors = t.getCurrentLightColor();
            if (t.getOrientation() == 0) {
                g2D.setColor(colors[1]);
                g2D.fillRect(t.getForwardPosition().x, t.getForwardPosition().y, 21, 29);
                g2D.setColor(colors[0]);
                g2D.fillRect(t.getLeftLightPosition().x, t.getLeftLightPosition().y, 20, 29);
                g2D.setColor(colors[2]);
                g2D.fillRect(t.getRightLightPosition().x, t.getRightLightPosition().y, 20, 29);
            } else {
                g2D.setColor(colors[1]);
                g2D.fillRect(t.getForwardPosition().x, t.getForwardPosition().y, 29, 22);
                g2D.setColor(colors[0]);
                g2D.fillRect(t.getLeftLightPosition().x, t.getLeftLightPosition().y, 29, 22);
                g2D.setColor(colors[2]);
                g2D.fillRect(t.getRightLightPosition().x, t.getRightLightPosition().y, 29, 22);
            }
            g2D.drawImage(t.getLayoutImg(), t.getTrans(), this);

        }
        if (!tm.isRunning())
            tm.start();
    }

    public void actionPerformed(ActionEvent e) {
        carSpawnTimer++;

        //This section is where cars are created, every 800s
        if (carSpawnTimer % 500 == 0) {
            //create new car objects over here
            for (int i = 0; i < 20; i++) {
                for (Direction direction : Direction.values()) {
                    spawnCar(direction);
                }
            }
            carSpawnTimer = 0;
        }
        repaint();
    }

    private void spawnCar(@NotNull Direction direction) {
        ArrayList<VehicleDrawable> listDrawable;
        VehicleDirection vehicleDirection;
        VehicleState vehicleState;
        TrafficLight trafficLight;
        switch (direction) {
            case LEFT -> {
                listDrawable = vehiclesDrawableLeft;
                vehicleDirection = VehicleDirection.LEFT;
                vehicleState = VehicleState.MOVE_X;
                trafficLight = trafficLights.get(3);
            }
            case UP -> {
                listDrawable = vehiclesDrawableUp;
                vehicleDirection = VehicleDirection.UP;
                vehicleState = VehicleState.MOVE_Y;
                trafficLight = trafficLights.get(2);
            }
            case RIGHT -> {
                listDrawable = vehiclesDrawableRight;
                vehicleDirection = VehicleDirection.RIGHT;
                vehicleState = VehicleState.MOVE_X;
                trafficLight = trafficLights.get(1);
            }
            case DOWN -> {
                listDrawable = vehiclesDrawableDown;
                vehicleDirection = VehicleDirection.DOWN;
                vehicleState = VehicleState.MOVE_Y;
                trafficLight = trafficLights.get(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        if (listDrawable.size() < 30) {
            int line = (listDrawable.size()) / 3;
            int vAheadID = 1000;
            if (line > 0) {
                vAheadID = listDrawable.size() - 3;
            }
            int carImageId = random.nextInt(carImages.length);
            //int spd = 7- random.nextInt(2);
            Vehicle vehicleAhead = null;
            if (vAheadID < listDrawable.size()) {
                vehicleAhead = listDrawable.get(vAheadID).getVehicle();
            }
            try {
                Vehicle vehicle = new Vehicle(6, vehicleState, vehicleDirection, trafficLight, vehicleAhead, listDrawable.size());
                VehicleDrawable vehicleDrawable = new VehicleDrawable(
                        getClass().getResourceAsStream(carImages[carImageId]),
                        vehicle,
                        this
                );
                listDrawable.add(vehicleDrawable);
            } catch (IOException e) {
                //TODO
            }
        }
    }

    public Simulation() {
        trafficLights = new ArrayList<>();
        addTrafficLight(349, 147, 180, 0, 1,
                new Vector2(349, 147),
                new Vector2(349 + 23, 147),
                new Vector2(349 + 47, 147));

        addTrafficLight(302, 530, 90, 1, 2,
                new Vector2(322, 511),
                new Vector2(322, 533),
                new Vector2(322, 558));

        addTrafficLight(1077, 585, 0, 0, 3,
                new Vector2(1079, 585),
                new Vector2(1077 + 25, 585),
                new Vector2(1077 + 48, 585));

        addTrafficLight(1125, 195, -90, 1, 4,
                new Vector2(1145, 175),
                new Vector2(1145, 175 + 25),
                new Vector2(1145, 175 + 47));

        try {
            mTerrain = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/road1.jpg")));
        } catch (IOException | NullPointerException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void addTrafficLight(int x, int y, int rotation, int signalType, int id,
                                 Vector2 leftPos, Vector2 forwardPos, Vector2 rightPos) {
        TrafficLight t = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), x, y, rotation, signalType, id, this);
        t.setLeftLightPosition(leftPos);
        t.setForwardPosition(forwardPos);
        t.setRightLightPosition(rightPos);
        trafficLights.add(t);
    }
}
