package be.ecam.trafficsim;

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
    // Arrays of vehicles in each direction
    private final ArrayList<UIVehicle> vehiclesRight = new ArrayList<>();
    private final ArrayList<UIVehicle> vehiclesDown = new ArrayList<>();
    private final ArrayList<UIVehicle> vehiclesLeft = new ArrayList<>();
    private final ArrayList<UIVehicle> vehiclesUp = new ArrayList<>();

    private final String[] carImages = {"/car1.png", "/car2.png", "/car3.png", "/car4.png",
            "/ambulance.png", "/police.png", "/truck1.png", "/truck2.png"};

    private final ArrayList<TrafficLight> trafficLights;
    // Timer regulating the rate new cars are created
    private int carSpawnTimer = 0;

    private enum Direction {
        LEFT, UP, RIGHT, DOWN
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
        addTestVehicle();
    }

    private void addTestVehicle() {
        try {
            // Create a traffic light for reference
            TrafficLight testTrafficLight = trafficLights.get(0);

            // Create a test vehicle with visible coordinates
            Vehicle testVehicle = new Vehicle(6, Vehicle.VehicleState.MOVE_X, Vehicle.VehicleDirection.RIGHT, testTrafficLight, null);
            testVehicle.getVehiclePosition().x = 300; // Set an X coordinate inside the map
            testVehicle.getVehiclePosition().y = 400; // Set a Y coordinate inside the map

            // Create the UIVehicle
            UIVehicle testUIVehicle = new UIVehicle(getClass().getResourceAsStream("/car1.png"), this, testVehicle);

            // Add it to the appropriate list
            vehiclesRight.add(testUIVehicle);
        } catch (Exception e) {
            System.err.println("Failed to add test vehicle: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(mTerrain, 0, 0, this);

        // Display all cars in their respective directions
        for (ArrayList<UIVehicle> list : Arrays.asList(vehiclesRight, vehiclesLeft, vehiclesDown, vehiclesUp)) {
            list.removeIf(uiVehicle -> !uiVehicle.getVehicle().isInView());
            for (UIVehicle uiVehicle : list) {
                uiVehicle.render(g2D);
            }
        }

        // Draw traffic lights
        for (TrafficLight t : trafficLights) {
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

        if (!tm.isRunning()) {
            tm.start();
        }
    }

    private void updateVehicles() {
        for (ArrayList<UIVehicle> list : Arrays.asList(vehiclesRight, vehiclesLeft, vehiclesDown, vehiclesUp)) {
            for (UIVehicle uiVehicle : list) {
                uiVehicle.getVehicle().move(); // Update the vehicle's position
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        carSpawnTimer++;

        // Create new cars every 500ms
        if (carSpawnTimer % 250 == 0) {
            for (Direction direction : Direction.values()) {
                spawnCar(direction);
            }
            carSpawnTimer = 0;
        }

        updateVehicles();
        repaint();
    }

    private void spawnCar(@NotNull Direction direction) {
        ArrayList<UIVehicle> list;
        VehicleDirection vehicleDirection;
        VehicleState vehicleState;
        TrafficLight trafficLight;
        switch (direction) {
            case LEFT -> {
                list = vehiclesLeft;
                vehicleDirection = VehicleDirection.LEFT;
                vehicleState = VehicleState.MOVE_X;
                trafficLight = trafficLights.get(3);
            }
            case UP -> {
                list = vehiclesUp;
                vehicleDirection = VehicleDirection.UP;
                vehicleState = VehicleState.MOVE_Y;
                trafficLight = trafficLights.get(2);
            }
            case RIGHT -> {
                list = vehiclesRight;
                vehicleDirection = VehicleDirection.RIGHT;
                vehicleState = VehicleState.MOVE_X;
                trafficLight = trafficLights.get(1);
            }
            case DOWN -> {
                list = vehiclesDown;
                vehicleDirection = VehicleDirection.DOWN;
                vehicleState = VehicleState.MOVE_Y;
                trafficLight = trafficLights.get(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        if (list.size() < 30) {
            int carImageId = random.nextInt(carImages.length);
            Vehicle vehicle = new Vehicle(6, vehicleState, vehicleDirection, trafficLight, null);
            try {
                UIVehicle uiVehicle = new UIVehicle(getClass().getResourceAsStream(carImages[carImageId]), this, vehicle);
                list.add(uiVehicle);
            } catch (Exception ex) {
                System.err.println("Failed to load vehicle image: " + ex.getMessage());
            }
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
