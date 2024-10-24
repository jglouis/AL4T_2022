package trafficsim;

import org.jetbrains.annotations.NotNull;
import trafficsim.Vehicle.VehicleDirection;
import trafficsim.Vehicle.VehicleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Simulation extends JPanel implements ActionListener {
    private Image car1;
    private Image mTerrain;
    private final Timer tm = new Timer(1, this);
    private int x = 0, velX = 2;
    private float mAngle = 0;

    private final Random random = new Random();
    //Arrays of vehicles in each direction
    private final ArrayList<Vehicle> vehiclesRight = new ArrayList<>();
    private final ArrayList<Vehicle> vehiclesDown = new ArrayList<>();
    private final ArrayList<Vehicle> vehiclesLeft = new ArrayList<>();
    private final ArrayList<Vehicle> vehiclesUp = new ArrayList<>();

    private final String[] carImages = {"/car1.png", "/car2.png", "/car3.png", "/car4.png",
            "/ambulance.png", "/police.png", "/truck1.png", "/truck2.png"};

    private final ArrayList<TrafficLight> trafficLights;
    //timer regulating the rate new cars are created
    private int carSpawnTimer = 0;

    private enum Direction {
        LEFT, UP, RIGHT, DOWN
    }

    float move = 0;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(mTerrain, 0, 0, this);

        //displays all cars going in the right direction
        ArrayList<Vehicle>[] allVehicles = new ArrayList[]{vehiclesRight, vehiclesLeft, vehiclesDown, vehiclesUp};
        for (ArrayList<Vehicle> list : allVehicles)
            for (int i = 0; i < list.size(); i++) {
                Vehicle v = list.get(i);
                if (v.isInView())
                    g2D.drawImage(v.getImage(), v.getTrans(), this);
                else {
                    list.remove(v);
                }
            }

        AffineTransform identity = g2D.getTransform();

        identity.setToTranslation(300, 200 + move);
        identity.rotate(Math.toRadians(mAngle), car1.getWidth(this), car1.getHeight(this));


        //Draw trafficLights
        for (TrafficLight t : trafficLights) {
            //Draw the lights
            Color colors[] = t.getCurrentLightColor();
            if (t.getOrientation() == 0) {
                g2D.setColor(colors[1]);
                g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 21, 29);
                g2D.setColor(colors[0]);
                g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 20, 29);
                g2D.setColor(colors[2]);
                g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 20, 29);
            } else {
                g2D.setColor(colors[1]);
                g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 29, 22);
                g2D.setColor(colors[0]);
                g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 29, 22);
                g2D.setColor(colors[2]);
                g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 29, 22);
            }
            g2D.drawImage(t.getLayoutImg(), t.getTrans(), this);

        }


        if (!tm.isRunning())
            tm.start();
    }

    public void actionPerformed(ActionEvent e) {
        carSpawnTimer++;
        if (x < 0 || x > 550)
            velX = -velX;
        x = x + velX;
        if (mAngle < 450)
            move += 0.2;
        else
            move += 2;
        steerTowards(450, 120);

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
        ArrayList<Vehicle> list;
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
            int line = (list.size()) / 3;
            int vAheadID = 1000;
            if (line > 0) {
                vAheadID = list.size() - 3;
            }
            int carImageId = random.nextInt(carImages.length);
            //int spd = 7- random.nextInt(2);
            Vehicle vehicleAhead = null;
            if (vAheadID < list.size()) {
                vehicleAhead = list.get(vAheadID);
            }
            list.add(new Vehicle(
                    getClass().getResourceAsStream(carImages[carImageId]),
                    6,
                    vehicleState,
                    vehicleDirection,
                    trafficLight,
                    this,
                    vehicleAhead,
                    list.size()));
        }
    }

    //simple function to test our steering

    /**
     * @param angle the final angle you want the car to positioned at relative to the normal
     * @param time  the time in seconds you want the car to take to position itself at "angle"
     */
    private void steerTowards(float angle, float time) {
        //first we calculate the angular velocity required to get the vehicle to angle in time t
        float angularVel = angle / time;

        if (Math.abs(mAngle) < Math.abs(angle))
            mAngle += angularVel;

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
            car1 = ImageIO.read(getClass().getResourceAsStream("/car1.jpg"));
            mTerrain = ImageIO.read(getClass().getResourceAsStream("/road1.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addTrafficLight(int x, int y, int rotation, int signalType, int id,
                                 Vector2 leftPos, Vector2 forwardPos, Vector2 rightPos) {
        TrafficLight t = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), x, y, rotation, signalType, id, this);
        t.setLeft_light_pos(leftPos);
        t.setForward_pos(forwardPos);
        t.setRight_light_pos(rightPos);
        trafficLights.add(t);
    }
}
